package com.mindspace.service;
import com.mindspace.model.*;
import com.mindspace.model.ChatMessage.IntentCategory;
import com.mindspace.model.ChatMessage.MessageRole;
import com.mindspace.repository.ChatSessionRepository;
import com.mindspace.repository.MentalHealthResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class ChatService {
    private final ChatSessionRepository sessionRepository;
    private final MentalHealthResourceRepository resourceRepository;
    private final IntentClassifierService classifierService;
    private final ResponseGeneratorService responseGeneratorService;
    public ChatService(ChatSessionRepository sessionRepository, MentalHealthResourceRepository resourceRepository, IntentClassifierService classifierService, ResponseGeneratorService responseGeneratorService) {
        this.sessionRepository = sessionRepository; this.resourceRepository = resourceRepository;
        this.classifierService = classifierService; this.responseGeneratorService = responseGeneratorService;
    }
    public record ChatResult(Long sessionId, String reply, List<String> mindfulnessSteps, List<MentalHealthResource> resources, String detectedIntent, LocalDateTime timestamp) {}
    @Transactional
    public ChatResult processMessage(User user, String messageText, Long existingSessionId) {
        ChatSession session = resolveSession(user, existingSessionId, messageText);
        if (classifierService.isCrisisMessage(messageText)) return buildCrisisResponse(session, messageText);
        IntentCategory intent = classifierService.classify(messageText);
        saveMessage(session, MessageRole.USER, messageText, intent);
        ResponseGeneratorService.BotResponse botResponse = responseGeneratorService.generateResponse(intent);
        List<MentalHealthResource> resources = resourceRepository.findByApplicableIntentsContaining(intent.name());
        saveMessage(session, MessageRole.BOT, botResponse.text(), intent);
        return new ChatResult(session.getId(), botResponse.text(), botResponse.steps(), resources, intent.name(), LocalDateTime.now());
    }
    private ChatSession resolveSession(User user, Long existingSessionId, String firstMessage) {
        if (existingSessionId != null) return sessionRepository.findByIdAndUserId(existingSessionId, user.getId()).orElseGet(() -> createNewSession(user, firstMessage));
        return createNewSession(user, firstMessage);
    }
    private ChatSession createNewSession(User user, String firstMessage) {
        IntentCategory mood = classifierService.classify(firstMessage);
        ChatSession session = ChatSession.builder().user(user).moodAtStart(mood.name()).build();
        return sessionRepository.save(session);
    }
    private void saveMessage(ChatSession session, MessageRole role, String content, IntentCategory intent) {
        ChatMessage msg = ChatMessage.builder().session(session).role(role).content(content).detectedIntent(intent).build();
        session.getMessages().add(msg);
        sessionRepository.save(session);
    }
    private ChatResult buildCrisisResponse(ChatSession session, String messageText) {
        saveMessage(session, MessageRole.USER, messageText, IntentCategory.COUNSELOR);
        ResponseGeneratorService.BotResponse crisis = responseGeneratorService.getCrisisResponse();
        List<MentalHealthResource> crisisResources = resourceRepository.findByAvailable24x7True();
        saveMessage(session, MessageRole.BOT, crisis.text(), IntentCategory.COUNSELOR);
        return new ChatResult(session.getId(), crisis.text(), crisis.steps(), crisisResources, "CRISIS", LocalDateTime.now());
    }
    @Transactional(readOnly = true)
    public List<ChatSession> getUserSessions(Long userId) { return sessionRepository.findByUserIdOrderByStartedAtDesc(userId); }
    @Transactional(readOnly = true)
    public Optional<ChatSession> getSession(Long sessionId, Long userId) { return sessionRepository.findByIdAndUserId(sessionId, userId); }
}
