package com.mindspace.service;
import com.mindspace.config.JwtUtil;
import com.mindspace.dto.AuthDto.*;
import com.mindspace.model.User;
import com.mindspace.repository.UserRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository; this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager; this.jwtUtil = jwtUtil;
    }
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) throw new IllegalArgumentException("Email already registered.");
        if (request.getStudentId() != null && userRepository.existsByStudentId(request.getStudentId())) throw new IllegalArgumentException("Student ID already registered.");
        User user = User.builder().name(request.getName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).studentId(request.getStudentId()).build();
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder().token(token).name(user.getName()).email(user.getEmail()).studentId(user.getStudentId()).build();
    }
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder().token(token).name(user.getName()).email(user.getEmail()).studentId(user.getStudentId()).build();
    }
}
