package com.mindspace.service;
import com.mindspace.model.ChatMessage.IntentCategory;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class IntentClassifierService {
    private static final Map<IntentCategory, List<String>> INTENT_KEYWORDS = new LinkedHashMap<>();
    static {
        INTENT_KEYWORDS.put(IntentCategory.ANXIETY, Arrays.asList("anxious","anxiety","exam","nervous","test","stress","stressed","worried","worry","panic","scared","fear","deadline"));
        INTENT_KEYWORDS.put(IntentCategory.BREATHING, Arrays.asList("breath","breathing","calm","relax","relaxation","meditat","mindful","inhale","exhale","breathe"));
        INTENT_KEYWORDS.put(IntentCategory.OVERWHELMED, Arrays.asList("overwhelm","burned out","burnout","too much","lonely","alone","cannot cope","breaking down","exhausted","too hard","give up","pressure","burden"));
        INTENT_KEYWORDS.put(IntentCategory.SLEEP, Arrays.asList("sleep","insomnia","tired","cannot sleep","can't sleep","awake","restless","night","fatigue","sleepy"));
        INTENT_KEYWORDS.put(IntentCategory.COUNSELOR, Arrays.asList("counselor","therapist","counseling","therapy","professional","talk to someone","help","support","appointment","crisis","emergency"));
        INTENT_KEYWORDS.put(IntentCategory.SAD, Arrays.asList("sad","low","depress","cry","hopeless","grief","lost","empty","numb","miserable","unhappy","down","blue","crying"));
        INTENT_KEYWORDS.put(IntentCategory.GREAT, Arrays.asList("great","good","happy","wonderful","fine","amazing","fantastic","excellent","feeling well","much better","okay"));
    }
    public IntentCategory classify(String message) {
        if (message == null || message.isBlank()) return IntentCategory.GENERAL;
        String lower = message.toLowerCase();
        Map<IntentCategory, Integer> scores = new EnumMap<>(IntentCategory.class);
        for (Map.Entry<IntentCategory, List<String>> entry : INTENT_KEYWORDS.entrySet()) {
            int score = 0;
            for (String keyword : entry.getValue()) {
                if (lower.contains(keyword)) score += keyword.contains(" ") ? 2 : 1;
            }
            if (score > 0) scores.put(entry.getKey(), score);
        }
        return scores.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(IntentCategory.GENERAL);
    }
    public boolean isCrisisMessage(String message) {
        if (message == null) return false;
        String lower = message.toLowerCase();
        return Arrays.asList("suicide","suicidal","end my life","kill myself","hurt myself","self harm","want to die","don't want to live").stream().anyMatch(lower::contains);
    }
}
