package com.mindspace.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private ChatSession session;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private MessageRole role;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private IntentCategory detectedIntent;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }
    public enum MessageRole { USER, BOT }
    public enum IntentCategory { ANXIETY, BREATHING, OVERWHELMED, SLEEP, COUNSELOR, SAD, GREAT, GENERAL }
    public ChatMessage() {}
    public Long getId() { return id; }
    public ChatSession getSession() { return session; }
    public void setSession(ChatSession s) { this.session = s; }
    public MessageRole getRole() { return role; }
    public void setRole(MessageRole r) { this.role = r; }
    public String getContent() { return content; }
    public void setContent(String c) { this.content = c; }
    public IntentCategory getDetectedIntent() { return detectedIntent; }
    public void setDetectedIntent(IntentCategory d) { this.detectedIntent = d; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private ChatSession session; private MessageRole role;
        private String content; private IntentCategory detectedIntent;
        public Builder session(ChatSession s) { this.session = s; return this; }
        public Builder role(MessageRole r) { this.role = r; return this; }
        public Builder content(String c) { this.content = c; return this; }
        public Builder detectedIntent(IntentCategory d) { this.detectedIntent = d; return this; }
        public ChatMessage build() {
            ChatMessage m = new ChatMessage();
            m.session = session; m.role = role; m.content = content; m.detectedIntent = detectedIntent;
            return m;
        }
    }
}
