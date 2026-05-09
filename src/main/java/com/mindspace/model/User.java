package com.mindspace.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false) private String name;
    @Email @NotBlank @Column(unique = true, nullable = false) private String email;
    @NotBlank @Column(nullable = false) private String password;
    @Column(name = "student_id", unique = true) private String studentId;
    @Column(name = "created_at") private LocalDateTime createdAt;
    @Column(name = "last_active") private LocalDateTime lastActive;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatSession> chatSessions;
    public User() {}
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); lastActive = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { lastActive = LocalDateTime.now(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String s) { this.studentId = s; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastActive() { return lastActive; }
    public List<ChatSession> getChatSessions() { return chatSessions; }
    public void setChatSessions(List<ChatSession> c) { this.chatSessions = c; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String name, email, password, studentId;
        public Builder name(String n) { this.name = n; return this; }
        public Builder email(String e) { this.email = e; return this; }
        public Builder password(String p) { this.password = p; return this; }
        public Builder studentId(String s) { this.studentId = s; return this; }
        public User build() {
            User u = new User(); u.name = name; u.email = email; u.password = password; u.studentId = studentId; return u;
        }
    }
}
