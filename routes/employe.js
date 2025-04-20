const express = require('express');
const employeModel = require('../models/employeModel');
const employeController = require('../controlleurs/employeController');
const router = express.Router();


router.post('/signup', employeController.signupEmploye);
router.post('/login',employeController.login_post);
router.post('/logout',employeController.logout)
router.post('/forgot-password', employeController.forgotPassword);
// Route pour réinitialiser le mot de passe
router.post('/reset-password', employeController.resetPassword);
// ✅ Route pour confirmer l'email en utilisant l'ID utilisateur
router.get("/confirm/:id", async (req, res) => {
  try {
      const { id } = req.params;

      // Trouver l'utilisateur par ID
      const user = await userModel.findById(id);
      if (!user) {
          return res.status(400).json({ error: "Invalid confirmation link" });
      }

      // Vérifier si l'utilisateur est déjà confirmé
      if (user.confirmed) {
          return res.status(200).json({ message: "Your account is already confirmed" });
      }

      // ✅ Confirmer l'utilisateur
      user.confirmed = true;
      await user.save();

      res.status(200).send(`
<div style="background-color: #f5f5f5; padding: 40px 0;">
<div style="max-width: 600px; margin: auto; font-family: Arial, sans-serif; background: #ffffff; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.1); text-align: center; overflow: hidden;">
  <div style="background-color: #f4ce36; padding: 40px 0;">
    <img src="https://img.icons8.com/ios-filled/100/ffffff/ok.png" width="50" alt="Success" />
  </div>
  <div style="padding: 30px;">
    <h2 style="margin-top: 0;">Thank you for your registration</h2>
    <p style="max-width: 80%; margin: auto; color: #555;">
      Your email has been successfully verified. You can now complete your registration or log in to your account.
    </p>
  </div>
</div>
</div>

`);
  } catch (error) {
      res.status(500).json({ error: "Something went wrong" });
  }
});

module.exports = router;
