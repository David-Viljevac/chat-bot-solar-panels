const express = require("express");
require('dotenv').config();
const dialogflow = require('dialogflow')

//DIALOGFLOW API 
const credentials = JSON.parse(process.env.CREDENTIALS);
const config = {
    credentials: {
        private_key: credentials.private_key,
        client_email: credentials.client_email
    }
}
const sessionClient = new dialogflow.SessionsClient(config);

const port = 3000;
const server = express();
server.use(express.json());
// CORS Middleware with next()
server.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
    if (req.method === 'OPTIONS') {
        return res.sendStatus(200);
    }
    next();
});

server.post('/api/chat', async (req, res) => {
    let { queryText, sessionId } = req.body;
    let result = await detectIntent(queryText, sessionId);
    res.status(200).send(result);
});

server.get('/health-check', (req, res) => {
    res.status(200).send('Successfully passed the health check for chat bot');
});

server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});

const detectIntent = async (queryText, sessionId) => {
    let sessionPath = sessionClient.sessionPath(credentials.project_id, sessionId)

    let request = {
        session: sessionPath,
        queryInput: {
            text: {
                text: queryText,
                languageCode: 'en-US'
            }
        }
    }
    const responses = await sessionClient.detectIntent(request);
    const result = responses[0].queryResult;
    return result.fulfillmentText;
}
