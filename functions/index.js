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

// Example function to get all user FCM tokens
async function getAllUserFCMTokens() {
    let tokens = [];
    try {
        const usersSnapshot = await admin.firestore().collection('Users').get();
        usersSnapshot.forEach(doc => {
            const fcmToken = doc.data().fcmToken;
            if (fcmToken) {
                tokens.push(fcmToken);
            }
        });
    } catch (error) {
        console.error('Error fetching user FCM tokens:', error);
    }
    return tokens;
}


const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp();

exports.sendAdoptionUpdateNotification = functions.firestore
    .document('AdoptionApplications/{applicationId}')
    .onUpdate(async (change, context) => {
        const newValue = change.after.data();
        const previousValue = change.before.data();

        // Check if the status has indeed changed
        if (newValue.status === previousValue.status) {
            console.log('Status has not changed');
            return null; // Exit if there's no status change
        }

        // Assuming there's a userId field in the AdoptionApplications document
        const userId = newValue.userId;
        if (!userId) {
            console.error('No userId found for application:', context.params.applicationId);
            return;
        }

        // Retrieve the user document to get the FCM token
        const userRef = admin.firestore().collection('Users').doc(userId);
        const userDoc = await userRef.get();
        if (!userDoc.exists) {
            console.error('User not found:', userId);
            return;
        }

        const userFCMToken = userDoc.data().fcmToken;
        if (!userFCMToken) {
            console.error('No FCM token found for user:', userId);
            return;
        }

        // Notification message
        const message = {
            notification: {
                title: 'Adoption Application Update',
                body: `Your application status for ${newValue.petName || "the pet"} is now ${newValue.status}.`
            },
            token: userFCMToken,
        };

        // Sending the notification
        try {
            await admin.messaging().send(message);
            console.log('Notification sent successfully to:', userId);
        } catch (error) {
            console.error('Error sending notification to:', userId, error);
        }
    });


// Sends a notification when a new message is added to a chat
exports.sendNewMessageNotification = functions.firestore
    .document('Chats/{chatId}/Messages/{messageId}')
    .onCreate(async (snapshot, context) => {
        const messageData = snapshot.data();
        const chatId = context.params.chatId;
        // Assuming 'receiverId' is available in the message document
        const receiverId = messageData.receiverId;

        // Retrieve the receiver's FCM token
        const receiverRef = admin.firestore().collection('Users').doc(receiverId);
        const receiverDoc = await receiverRef.get();
        if (!receiverDoc.exists) {
            console.error('Receiver user not found:', receiverId);
            return;
        }

        const receiverFCMToken = receiverDoc.data().fcmToken;
        if (!receiverFCMToken) {
            console.error('No FCM token found for receiver:', receiverId);
            return;
        }

        // Notification message for new chat message
        const newMessageNotification = {
            notification: {
                title: 'New Message',
                body: `You have a new message from ${messageData.senderName}.`
            },
            token: receiverFCMToken,
        };

        try {
            await admin.messaging().send(newMessageNotification);
            console.log('New message notification sent successfully to:', receiverId);
        } catch (error) {
            console.error('Error sending new message notification:', error);
        }
    });


// Sends a notification when new educational content is added
exports.sendNewLessonNotification = functions.firestore
    .document('AdoptionLessons/{lessonId}')
    .onCreate(async (snapshot, context) => {
        const lessonData = snapshot.data();
        const title = lessonData.title;

        // For the sake of simplicity, assuming a function that retrieves all user FCM tokens
        const tokens = await getAllUserFCMTokens(); // Implement this function based on your app's logic

        // Notification message for new educational content
        const lessonNotification = {
            notification: {
                title: 'New Educational Content',
                body: `Check out the new lesson: ${title}.`
            },
            tokens: tokens, // 'tokens' should be an array of FCM tokens
        };

        try {
            await admin.messaging().sendMulticast(lessonNotification);
            console.log('New lesson notification sent successfully.');
        } catch (error) {
            console.error('Error sending new lesson notification:', error);
        }
    });
