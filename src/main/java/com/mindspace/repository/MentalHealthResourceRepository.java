package com.mindspace.repository;
import com.mindspace.model.MentalHealthResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MentalHealthResourceRepository extends JpaRepository<MentalHealthResource, Long> {
    @Query("SELECT r FROM MentalHealthResource r WHERE r.applicableIntents LIKE %:intent%")
    List<MentalHealthResource> findByApplicableIntentsContaining(@Param("intent") String intent);
    List<MentalHealthResource> findByResourceType(String resourceType);
    List<MentalHealthResource> findByAvailable24x7True();
}
