package com.mindspace.dto;
import jakarta.validation.constraints.NotBlank;
public class AuthDto {
    public static class RegisterRequest {
        @NotBlank private String name;
        @NotBlank private String email;
        @NotBlank private String password;
        private String studentId;
        public RegisterRequest() {}
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }
    }
    public static class LoginRequest {
        @NotBlank private String email;
        @NotBlank private String password;
        public LoginRequest() {}
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    public static class AuthResponse {
        private String token, name, email, studentId;
        public AuthResponse() {}
        public AuthResponse(String token, String name, String email, String studentId) {
            this.token = token; this.name = name; this.email = email; this.studentId = studentId;
        }
        public String getToken() { return token; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getStudentId() { return studentId; }
        public static Builder builder() { return new Builder(); }
        public static class Builder {
            private String token, name, email, studentId;
            public Builder token(String t) { this.token = t; return this; }
            public Builder name(String n) { this.name = n; return this; }
            public Builder email(String e) { this.email = e; return this; }
            public Builder studentId(String s) { this.studentId = s; return this; }
            public AuthResponse build() { return new AuthResponse(token, name, email, studentId); }
        }
    }
}
