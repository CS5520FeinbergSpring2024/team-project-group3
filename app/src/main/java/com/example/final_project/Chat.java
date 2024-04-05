package com.example.final_project;

public class Chat {
    private String chatId;
    private String petOwnerId;
    private String shelterId;
    private String lastMessage;
    private long lastMessageTimestamp;

    public Chat() {
        // Default constructor for Firebase
    }

    public Chat(String chatId, String petOwnerId, String shelterId, String lastMessage, long lastMessageTimestamp) {
        this.chatId = chatId;
        this.petOwnerId = petOwnerId;
        this.shelterId = shelterId;
        this.lastMessage = lastMessage;
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    // Getters and Setters
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(String petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    public String getShelterId() {
        return shelterId;
    }

    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(long lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }
}
