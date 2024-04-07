/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });


const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendAdoptionUpdateNotification = functions.firestore
    .document('AdoptionApplications/{applicationId}')
    .onUpdate(async (change, context) => {
        const newValue = change.after.data();
        const previousValue = change.before.data();

        // Check if the status has changed
        if (newValue.status !== previousValue.status) {
            // Define the notification message
            const message = {
                notification: {
                    title: 'Adoption Application Update',
                    body: `Your adoption application's status has been updated to: ${newValue.status}.`
                },
                token: newValue.userFCMToken // Assuming each application document includes the FCM token of the user
            };

            // Send the notification
            try {
                await admin.messaging().send(message);
                console.log('Notification sent successfully');
            } catch (error) {
                console.error('Error sending notification:', error);
            }
        }
    });
