package com.mindspace.controller;
import com.mindspace.model.MentalHealthResource;
import com.mindspace.repository.MentalHealthResourceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private final MentalHealthResourceRepository resourceRepository;
    public ResourceController(MentalHealthResourceRepository resourceRepository) { this.resourceRepository = resourceRepository; }
    @GetMapping("/public")
    public ResponseEntity<List<MentalHealthResource>> getAllResources() { return ResponseEntity.ok(resourceRepository.findAll()); }
    @GetMapping("/crisis")
    public ResponseEntity<List<MentalHealthResource>> getCrisisResources() { return ResponseEntity.ok(resourceRepository.findByAvailable24x7True()); }
    @GetMapping("/type/{type}")
    public ResponseEntity<List<MentalHealthResource>> getByType(@PathVariable String type) { return ResponseEntity.ok(resourceRepository.findByResourceType(type.toUpperCase())); }
}
