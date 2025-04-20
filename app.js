require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const jwt = require('jsonwebtoken');
const cors = require('cors');
const app = express();
const port = 3000;


const employeRoutes = require('./routes/employe');

// Middleware
app.use(cors({
    origin: '*',  // URL de ton frontend
    //credentials: true, // Allow cookies and authentication headers
    //methods: "GET, POST, PUT, DELETE, OPTIONS",
    allowedHeaders: 'Content-Type,Authorization',
})); // Enable CORS for frontend requests
app.use(express.json());


app.use('/api/employe', employeRoutes);  // /api/users pour gérer les utilisateurs



// Lancement du serveur
app.listen(port, () => {
    console.log(` Serveur démarré sur http://localhost:${port}`);
});


// Connect to the MongoDB database
mongoose.connect('mongodb+srv://kenza:kenza2020@cluster0.65hm7.mongodb.net/micro?retryWrites=true&w=majority&appName=Cluster0', {
    useNewUrlParser: true,
    useUnifiedTopology: true
  })
  .then(() => {
    console.log('\x1b[33m%s\x1b[0m', `Connected to MongoDB`)
    ;})
  .catch((err) => {
    console.log('\x1b[31m%s\x1b[0m','error mongodb connection',err)
  });

