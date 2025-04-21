const Employe = require('../models/employeModel');
const crypto = require('crypto');
const jwt = require('jsonwebtoken');
require('dotenv').config();
const bcrypt = require('bcrypt')
const nodemailer = require('nodemailer');
const sendEmail = require('../emailService');




const signupEmploye = async (req, res) => {
  const { name, email, password } = req.body;

  try {
      // 🔍 Validation des champs
      if (!name  || !email || !password ) {
          throw new Error('All fields must be filled');
      }
      // 🔍 Vérification si l'email existe déjà
      const exists = await Employe.findOne({ email });
      if (exists) {
          throw new Error('Email already in use');
      }

      // 🔑 Hash du mot de passe
      const salt = await bcrypt.genSalt(10);
      const hash = await bcrypt.hash(password, salt);
      const role = 'employe'; 


      // ✅ Création de l'utilisateur

      const user = await Employe.create({ name, email, password: hash, role });

      // 🎟 Génération du Token
      const token = createToken(user._id, user.role);

      res.status(200).json({ name, email, role, token, employeId: user._id});

  } catch (error) {
      res.status(400).json({ error: error.message });
  }
};

// Generate Token
const createToken = (_id, role) => {
    if (!process.env.SECRET) {
        throw new Error("JWT Secret is missing! Make sure to define SECRET in your .env file.");
    }
    return jwt.sign({ _id, role }, process.env.SECRET, { expiresIn: '100d' });
};


const login_post = async (req, res) => {
    const { email, password } = req.body;

    try {
        // Récupérer l'utilisateur par email
        const user = await Employe.findOne({ email });
        if (!user) {
            return res.status(404).json({ message: 'Utilisateur non trouvé.' });
        }


        // Comparer le mot de passe envoyé avec celui stocké dans la DB
        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(401).json({ message: 'Mot de passe incorrect.' });
        }

        // Générer un token JWT
        const token = jwt.sign(
            { id: user._id, email: user.email, role: user.role },
            process.env.JWT_SECRET,
            { expiresIn: '1h' }
        );

        // Enregistrer le token dans la base de données
        user.token = token;
        await user.save();

        // Réponse avec le token
        res.json({
            message: 'Connexion réussie.',
            token,
            user: {
                id: user._id,
                email: user.email,
                role: user.role
            }
        });

    } catch (err) {
        console.error(err);
        res.status(500).json({ message: 'Erreur serveur.' });
    }
};

const blacklist = new Set();
const logout = (req, res) => {
  const token = req.headers.authorization?.split(" ")[1]; // Récupérer le token

  if (!token) {
    return res.status(400).json({ message: "Token manquant." });
  }

  blacklist.add(token); // Ajouter à la blacklist
  res.json({ message: "Déconnexion réussie." });
};


const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.EMAIL_USER,   
        pass: process.env.EMAIL_PASS   
    }
  });
  
  
  // Fonction pour demander la réinitialisation du mot de passe
  const forgotPassword = async (req, res) => {
    const { email } = req.body;
  
    try {
        // Vérifier si l'email existe dans la base de données
        const user = await Employe.findOne({ email });
        if (!user) {
            console.log('Employe not found');
            return res.status(400).json({ error: 'Email not found' });
        }
  
        // Générer un code de réinitialisation
        const resetCode = crypto.randomBytes(3).toString('hex'); // Code de 6 caractères
  
        // Enregistrer le code dans l'utilisateur (dans un champ temporaire)
        user.resetCode = resetCode;
        user.resetCodeExpiration = Date.now() + 3600000; // Le code expire dans 1 heure
        await user.save();
        console.log(user)
        // Envoyer un email avec le code de réinitialisation
        const mailOptions = {
            from: process.env.EMAIL_USER,
            to: email,
            subject: 'Password Reset Code',
            html: `
                <h3>Password Reset Request</h3>
                <p>We received a request to reset your password.</p>
                <p>Your reset code is: <strong>${resetCode}</strong></p>
                <p>If you didn't request a password reset, please ignore this email.</p>
            `
        };
  
        await transporter.sendMail(mailOptions);
  
        res.status(200).json({ message: 'Reset code sent to your email' });
    } catch (error) {
        console.error('Error during password reset process:', error);
        res.status(500).json({ error: 'Something went wrong' });
    }
  };
  
  // Fonction pour réinitialiser le mot de passe
  const resetPassword = async (req, res) => {
    const { resetCode, newPassword } = req.body;
  
    try {
        // Vérifier si le code est valide et non expiré
        const user = await Employe.findOne({ resetCode, resetCodeExpiration: { $gt: Date.now() } });
        if (!user) {
            return res.status(400).json({ error: 'Invalid or expired reset code' });
        }
  
        // Hacher le nouveau mot de passe
        const hashedPassword = await bcrypt.hash(newPassword, 10);  // Le "10" représente le nombre de "salts rounds"
  
        // Mettre à jour le mot de passe de l'utilisateur
        user.password = hashedPassword;
        user.resetCode = undefined; // Effacer le code de réinitialisation
        user.resetCodeExpiration = undefined; // Effacer l'expiration du code
        await user.save();
  
        res.status(200).json({ message: 'Password reset successfully' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Something went wrong' });
    }
  };
const getByToken = async (req, res) => {
    const token = req.headers.authorization?.split(" ")[1]; // Récupérer le token depuis l'en-tête Authorization

    console.log("token reçu :", token);

    if (!token) {
        return res.status(400).json({ message: "Token manquant." });
    }

    try {
        // Rechercher l'utilisateur ayant ce token
        const user = await User.findOne({ token });

        if (!user) {
            return res.status(404).json({ message: "Utilisateur non trouvé." });
        }

        // Retourner les infos utilisateur
        res.status(200).json({
            user: {
                id: user._id,
                image: user.image,
                name: user.name,
                surname: user.surname,
                email: user.email,
                role: user.role,
                tel: user.tel,
                confirmed: user.confirmed,
                password:user.password
            }
        });

    } catch (error) {
        console.error("Erreur dans getByToken:", error);
        res.status(500).json({ message: "Erreur serveur lors de la récupération de l'utilisateur." });
    }
};

module.exports = { signupEmploye,login_post,logout,forgotPassword,resetPassword,getByToken};