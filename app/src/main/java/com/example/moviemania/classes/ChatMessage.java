package com.example.moviemania.classes;

public class ChatMessage {
    private String message;
    private boolean isUser;
    private boolean isLoading;
    private boolean isTyping; // Add typing state
    private String fullMessage; // Store complete message for typing effect

    public ChatMessage(String message, boolean isUser) {
        this.message = message;
        this.isUser = isUser;
        this.isLoading = false;
        this.isTyping = false;
        this.fullMessage = message;
    }

    // Constructor for loading message
    public ChatMessage(boolean isLoading) {
        this.message = "";
        this.isUser = false;
        this.isLoading = isLoading;
        this.isTyping = false;
        this.fullMessage = "";
    }

    // Constructor for typing message
    public ChatMessage(String fullMessage, boolean isTyping, boolean placeholder) {
        this.fullMessage = fullMessage;
        this.message = "";
        this.isUser = false;
        this.isLoading = false;
        this.isTyping = isTyping;
    }

    // Getters and setters
    public String getMessage() { return message; }
    public String getFullMessage() { return fullMessage; }
    public boolean isUser() { return isUser; }
    public boolean isLoading() { return isLoading; }
    public boolean isTyping() { return isTyping; }

    public void setMessage(String message) { this.message = message; }
    public void setLoading(boolean loading) { isLoading = loading; }
    public void setTyping(boolean typing) { isTyping = typing; }
    public void setFullMessage(String fullMessage) { this.fullMessage = fullMessage; }
}