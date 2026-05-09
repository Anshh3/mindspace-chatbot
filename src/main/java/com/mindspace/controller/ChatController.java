package com.mindspace.controller;
import com.mindspace.model.*;
import com.mindspace.repository.UserRepository;
import com.mindspace.service.ChatService;
import com.mindspace.service.ChatService.ChatResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserRepository userRepository;
    public ChatController(ChatService chatService, UserRepository userRepository) { this.chatService = chatService; this.userRepository = userRepository; }
    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> sendMessage(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Map<String, Object> body) {
        User user = getUser(userDetails);
        String message = (String) body.get("message");
        Long sessionId = body.get("sessionId") != null ? Long.valueOf(body.get("sessionId").toString()) : null;
        ChatResult result = chatService.processMessage(user, message, sessionId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("sessionId", result.sessionId());
        response.put("reply", result.reply());
        response.put("mindfulnessSteps", result.mindfulnessSteps());
        response.put("detectedIntent", result.detectedIntent());
        response.put("timestamp", result.timestamp());
        response.put("resources", mapResources(result.resources()));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/sessions")
    public ResponseEntity<List<Map<String, Object>>> getSessions(@AuthenticationPrincipal UserDetails userDetails) {
        User user = getUser(userDetails);
        List<ChatSession> sessions = chatService.getUserSessions(user.getId());
        List<Map<String, Object>> result = sessions.stream().map(s -> { Map<String, Object> m = new LinkedHashMap<>(); m.put("sessionId", s.getId()); m.put("startedAt", s.getStartedAt()); m.put("moodAtStart", s.getMoodAtStart()); m.put("messageCount", s.getMessages().size()); return m; }).toList();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/sessions/{id}")
    public ResponseEntity<?> getSession(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        User user = getUser(userDetails);
        return chatService.getSession(id, user.getId()).map(session -> {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("sessionId", session.getId());
            result.put("startedAt", session.getStartedAt());
            result.put("messages", session.getMessages().stream().map(msg -> { Map<String, Object> m = new LinkedHashMap<>(); m.put("role", msg.getRole()); m.put("content", msg.getContent()); m.put("timestamp", msg.getCreatedAt()); return m; }).toList());
            return ResponseEntity.ok(result);
        }).orElse(ResponseEntity.notFound().build());
    }
    private User getUser(UserDetails userDetails) { return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found")); }
    private List<Map<String, Object>> mapResources(List<MentalHealthResource> resources) {
        return resources.stream().map(r -> { Map<String, Object> m = new LinkedHashMap<>(); m.put("title", r.getTitle()); m.put("description", r.getDescription()); m.put("contactInfo", r.getContactInfo()); m.put("url", r.getUrl()); m.put("resourceType", r.getResourceType()); m.put("available24x7", r.isAvailable24x7()); m.put("free", r.isFree()); return m; }).toList();
    }
}
