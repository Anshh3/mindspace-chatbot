package com.mindspace.dto;
import jakarta.validation.constraints.NotBlank;
public class ChatRequest {
    @NotBlank private String message;
    private Long sessionId;
    public ChatRequest() {}
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
}
