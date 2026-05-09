package com.mindspace.config;
import com.mindspace.model.MentalHealthResource;
import com.mindspace.repository.MentalHealthResourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class DataSeeder implements CommandLineRunner {
    private final MentalHealthResourceRepository resourceRepository;
    public DataSeeder(MentalHealthResourceRepository resourceRepository) { this.resourceRepository = resourceRepository; }
    @Override
    public void run(String... args) {
        if (resourceRepository.count() > 0) return;
        System.out.println("Seeding mental health resources...");
        resourceRepository.saveAll(List.of(
            MentalHealthResource.builder().title("Campus Counseling Center").description("Free confidential counseling for enrolled students.").contactInfo("counseling@campus.edu | (555) 100-2000").url("https://campus.edu/counseling").resourceType("COUNSELING").available24x7(false).free(true).applicableIntents("ANXIETY,OVERWHELMED,SAD,COUNSELOR,GENERAL").build(),
            MentalHealthResource.builder().title("24/7 Campus Crisis Line").description("Immediate support outside office hours. Trained crisis counselors available.").contactInfo("(555) 200-9999").url("https://campus.edu/crisis").resourceType("CRISIS").available24x7(true).free(true).applicableIntents("COUNSELOR,SAD,OVERWHELMED,ANXIETY").build(),
            MentalHealthResource.builder().title("Peer Support Network").description("Trained student peers for informal non-judgmental conversations.").contactInfo("peer.support@campus.edu").url("https://campus.edu/peer-support").resourceType("PEER").available24x7(false).free(true).applicableIntents("ANXIETY,OVERWHELMED,SAD,GENERAL").build(),
            MentalHealthResource.builder().title("Exam Stress Workshop").description("Weekly group sessions on managing performance anxiety and exam pressure.").contactInfo("workshops@campus.edu").url("https://campus.edu/workshops/anxiety").resourceType("WORKSHOP").available24x7(false).free(true).applicableIntents("ANXIETY").build(),
            MentalHealthResource.builder().title("Student Wellness Hub").description("Drop-in support, peer mentoring, and self-care resources. Open 7 days a week.").contactInfo("wellness@campus.edu").url("https://campus.edu/wellness").resourceType("COUNSELING").available24x7(false).free(true).applicableIntents("OVERWHELMED,GREAT,GENERAL").build(),
            MentalHealthResource.builder().title("Sleep Health Program").description("Free sleep assessment and coaching through the student health center.").contactInfo("health@campus.edu").url("https://campus.edu/sleep-health").resourceType("ONLINE").available24x7(false).free(true).applicableIntents("SLEEP").build(),
            MentalHealthResource.builder().title("Mindfulness Classes").description("Free weekly guided mindfulness sessions open to all students.").contactInfo("mindfulness@campus.edu").url("https://campus.edu/mindfulness").resourceType("WORKSHOP").available24x7(false).free(true).applicableIntents("BREATHING,ANXIETY,OVERWHELMED,SLEEP").build(),
            MentalHealthResource.builder().title("iCall Helpline India").description("Professional counseling helpline for students. Confidential support.").contactInfo("9152987821 | Mon-Sat 8am-10pm").url("https://icallhelpline.org").resourceType("CRISIS").available24x7(false).free(true).applicableIntents("COUNSELOR,SAD,ANXIETY,OVERWHELMED").build(),
            MentalHealthResource.builder().title("Vandrevala Foundation").description("Free 24/7 mental health helpline with trained counselors.").contactInfo("1860-2662-345 | 24/7").url("https://www.vandrevalafoundation.com").resourceType("CRISIS").available24x7(true).free(true).applicableIntents("COUNSELOR,SAD,ANXIETY,OVERWHELMED").build()
        ));
        System.out.println("Seeded 9 mental health resources.");
    }
}
