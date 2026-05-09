package com.mindspace.model;
import jakarta.persistence.*;
@Entity
@Table(name = "mental_health_resources")
public class MentalHealthResource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String title;
    @Column(columnDefinition = "TEXT") private String description;
    private String contactInfo;
    private String url;
    @Column(name = "resource_type") private String resourceType;
    @Column(name = "is_available_24_7") private boolean available24x7;
    @Column(name = "is_free") private boolean free;
    @Column(name = "applicable_intents") private String applicableIntents;
    public MentalHealthResource() {}
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String t) { this.title = t; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String c) { this.contactInfo = c; }
    public String getUrl() { return url; }
    public void setUrl(String u) { this.url = u; }
    public String getResourceType() { return resourceType; }
    public void setResourceType(String r) { this.resourceType = r; }
    public boolean isAvailable24x7() { return available24x7; }
    public void setAvailable24x7(boolean a) { this.available24x7 = a; }
    public boolean isFree() { return free; }
    public void setFree(boolean f) { this.free = f; }
    public String getApplicableIntents() { return applicableIntents; }
    public void setApplicableIntents(String a) { this.applicableIntents = a; }
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private String title, description, contactInfo, url, resourceType, applicableIntents;
        private boolean available24x7, free;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String t) { this.title = t; return this; }
        public Builder description(String d) { this.description = d; return this; }
        public Builder contactInfo(String c) { this.contactInfo = c; return this; }
        public Builder url(String u) { this.url = u; return this; }
        public Builder resourceType(String r) { this.resourceType = r; return this; }
        public Builder available24x7(boolean a) { this.available24x7 = a; return this; }
        public Builder free(boolean f) { this.free = f; return this; }
        public Builder applicableIntents(String a) { this.applicableIntents = a; return this; }
        public MentalHealthResource build() {
            MentalHealthResource r = new MentalHealthResource();
            r.id = id; r.title = title; r.description = description; r.contactInfo = contactInfo;
            r.url = url; r.resourceType = resourceType; r.available24x7 = available24x7;
            r.free = free; r.applicableIntents = applicableIntents; return r;
        }
    }
}
