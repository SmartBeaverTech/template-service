package com.smartbeaver.insightly.service;

import com.smartbeaver.insightly.dao.entities.Template;
import com.smartbeaver.insightly.dao.repositories.TemplateRepository;
import com.smartbeaver.insightly.dto.TemplateMetadataDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultTemplateService implements TemplateService{

    @Autowired
    private TemplateRepository repository;


    @Override
    public Template createTemplate(Template template) {
        LocalDateTime now = LocalDateTime.now();

        // Set timestamps automatically
        if (template.getMetadata().getCreatedAt() == null) {
            template.getMetadata().setCreatedAt(now);
        }
        template.getMetadata().setUpdatedAt(now);
        return repository.save(template);
    }

    @Override
    public Template getTemplate(UUID templateId) {
        return repository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Template not found with ID: " + templateId
                ));
    }

    @Override
    public Template getTemplateByVersion(UUID templateId, int version) {
        return repository.findByTemplateIdAndVersion(templateId, version)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Template not found with ID: " + templateId + " and version: " + version
                ));
    }

    @Override
    public List<Template> getTemplateByType(String type) {
        return repository.findByType(type);
    }

    @Override
    public Template updateTemplate(UUID templateId, Template updated) {
        return repository.findById(templateId).map(existing -> {
            // Keep the same templateId
            updated.setTemplateId(templateId);

            // Preserve the original createdAt in metadata
            OffsetDateTime originalCreatedAt = OffsetDateTime.from(existing.getMetadata().getCreatedAt());
            updated.getMetadata().setCreatedAt(LocalDateTime.from(originalCreatedAt));

            // Update updatedAt in metadata
            updated.getMetadata().setUpdatedAt(LocalDateTime.from(OffsetDateTime.now()));

            return repository.save(updated);
        }).orElse(null);
    }

    @Override
    public void deleteTemplate(UUID templateId) {
        repository.deleteById(templateId);
    }

    // Get metadata only for all templates
    @Override
    public List<TemplateMetadataDTO> getAllTemplatesMetadata() {
        return repository.findAll().stream()
                .map(template -> new TemplateMetadataDTO(
                        template.getTemplateId(),
                        template.getType(),
                        template.getVersion(),
                        template.getMetadata().getName(),
                        template.getMetadata().getDescription(),
                        template.getMetadata().getCategory(),
                        template.getMetadata().getCreatedAt(),
                        template.getMetadata().getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }
}
