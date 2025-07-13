package com.example.moviemania.classes;

public class ChatRequest {
    private String message;
    private String session_id;

    public ChatRequest(String message, String sessionId) {
        this.message = message;
        this.session_id = sessionId;
    }

    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getSession_id() { return session_id; }
    public void setSession_id(String session_id) { this.session_id = session_id; }
}