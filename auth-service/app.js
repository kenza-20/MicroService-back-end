require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const { Eureka } = require('eureka-js-client');

const app = express();
const port = 3000;

// Routes
const employeRoutes = require('./routes/employe');

// Middleware
app.use(cors({
    origin: '*',
    allowedHeaders: 'Content-Type,Authorization',
}));
app.use(express.json());

app.use('/api/employe', employeRoutes);

app.get('/api/auth/ping', (req, res) => {
    res.send('pong from auth-service!');
});

// Lancement du serveur
app.listen(port, '0.0.0.0', () => {
    console.log(`✅ Serveur démarré sur http://0.0.0.0:${port}`);
});

// Connexion MongoDB (sans options dépréciées)
mongoose.connect(process.env.MONGO_URI || 'mongodb+srv://kenza:kenza2020@cluster0.65hm7.mongodb.net/micro?retryWrites=true&w=majority&appName=Cluster0')
    .then(() => {
        console.log('\x1b[33m%s\x1b[0m', `✅ Connected to MongoDB`);

        const eureka = new Eureka({
            instance: {
                app: 'AUTH-SERVICE',
                instanceId: 'auth-service:3000',
                hostName: 'auth-service',
                ipAddr: 'auth-service',
                port: {
                    '$': 3000,
                    '@enabled': true
                },
                statusPageUrl: 'http://auth-service:3000/api/auth/ping',
                healthCheckUrl: 'http://auth-service:3000/api/auth/ping',
                vipAddress: 'auth-service',
                dataCenterInfo: {
                    '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
                    name: 'MyOwn'
                }
            },
            eureka: {
                host: 'eureka-server',
                port: 8761,
                servicePath: '/eureka/apps/'
            }
        });

        // Retry logique
        function startEurekaWithRetry(client, retries = 5) {
            client.start((error) => {
                if (error) {
                    console.log(`❌ Tentative échouée de connexion à Eureka (retries left: ${retries})`);
                    console.error(error);

                    if (retries > 0) {
                        setTimeout(() => startEurekaWithRetry(client, retries - 1), 5000); // 5s entre chaque tentative
                    } else {
                        console.log('❌ Échec final : impossible de joindre Eureka après plusieurs essais.');
                    }
                } else {
                    console.log('✅ Auth-Service enregistré dans Eureka !');
                }
            });
        }

        // Attente avant première tentative (15s)
        setTimeout(() => {
            console.log('⏳ Tentative d\'enregistrement Eureka...');
            startEurekaWithRetry(eureka);
        }, 15000);
    })
    .catch((err) => {
        console.log('\x1b[31m%s\x1b[0m', '❌ Erreur de connexion MongoDB :', err);
    });
