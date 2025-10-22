package com.smartbeaver.insightly.controllers;


import com.smartbeaver.insightly.dao.entities.Template;
import com.smartbeaver.insightly.dto.TemplateMetadataDTO;
import com.smartbeaver.insightly.service.TemplateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/templates")
@RequiredArgsConstructor
@Tag(name = "Template Management", description = "Manage Templates")
public class TemplateController {

    private final TemplateService service;

    @PostMapping
    public ResponseEntity<?> createTemplate(@Valid @RequestBody Template template, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Return first validation error message
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
        }

        Template savedTemplate = service.createTemplate(template);
        return ResponseEntity.ok(savedTemplate);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<Template> getTemplate(@PathVariable UUID templateId) {
        return ResponseEntity.ok(service.getTemplate(templateId));
    }

    @GetMapping("/{templateId}/version/{version}")
    public ResponseEntity<Template> getTemplateVersion(@PathVariable UUID templateId,
                                                       @PathVariable int version) {
        return ResponseEntity.ok(service.getTemplateByVersion(templateId, version));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Template>> getTemplatesByType(@PathVariable String type) {
        return ResponseEntity.ok(service.getTemplateByType(type));
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<Template> updateTemplate(@PathVariable UUID templateId,
                                                   @RequestBody Template template) {
        return ResponseEntity.ok(service.updateTemplate(templateId, template));
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID templateId) {
        service.deleteTemplate(templateId);
        return ResponseEntity.noContent().build();
    }

    // Get all templates metadata (lightweight)
    @GetMapping
    public ResponseEntity<List<TemplateMetadataDTO>> getAllTemplatesMetadata() {
        List<TemplateMetadataDTO> metadataList = service.getAllTemplatesMetadata();
        return ResponseEntity.ok(metadataList);
    }
}