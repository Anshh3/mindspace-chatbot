package com.mindspace.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "chat_sessions")
public class ChatSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "started_at") private LocalDateTime startedAt;
    @Column(name = "ended_at") private LocalDateTime endedAt;
    @Column(name = "mood_at_start") private String moodAtStart;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> messages = new ArrayList<>();
    public ChatSession() {}
    @PrePersist protected void onCreate() { startedAt = LocalDateTime.now(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(LocalDateTime e) { this.endedAt = e; }
    public String getMoodAtStart() { return moodAtStart; }
    public void setMoodAtStart(String m) { this.moodAtStart = m; }
    public List<ChatMessage> getMessages() { return messages; }
    public void setMessages(List<ChatMessage> m) { this.messages = m; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private User user; private String moodAtStart;
        public Builder user(User u) { this.user = u; return this; }
        public Builder moodAtStart(String m) { this.moodAtStart = m; return this; }
        public ChatSession build() {
            ChatSession s = new ChatSession();
            s.user = user; s.moodAtStart = moodAtStart; return s;
        }
    }
}
