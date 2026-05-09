package com.mindspace;
import com.mindspace.model.ChatMessage.IntentCategory;
import com.mindspace.service.IntentClassifierService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class IntentClassifierServiceTest {
    private final IntentClassifierService classifier = new IntentClassifierService();
    @Test void testAnxiety() { assertEquals(IntentCategory.ANXIETY, classifier.classify("I feel anxious about my exams")); }
    @Test void testBreathing() { assertEquals(IntentCategory.BREATHING, classifier.classify("Can you guide me through a breathing exercise?")); }
    @Test void testCrisis() { assertTrue(classifier.isCrisisMessage("I want to hurt myself")); }
    @Test void testGeneral() { assertEquals(IntentCategory.GENERAL, classifier.classify("")); }
}
