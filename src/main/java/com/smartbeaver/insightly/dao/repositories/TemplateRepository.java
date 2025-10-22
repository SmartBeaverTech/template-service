package com.smartbeaver.insightly.dao.repositories;


import com.smartbeaver.insightly.dao.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository extends JpaRepository<Template, UUID> {
    List<Template> findByType(String type);
    Optional<Template> findByTemplateIdAndVersion(UUID templateId, int version);
    List<Template> findByMetadataName(String name);
    List<Template> findByMetadataCategory(String category);
}