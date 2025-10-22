package com.smartbeaver.insightly.service;

import com.smartbeaver.insightly.dao.entities.Template;
import com.smartbeaver.insightly.dto.TemplateMetadataDTO;

import java.util.List;
import java.util.UUID;

public interface TemplateService {

    public Template createTemplate(Template template);
    public Template getTemplate(UUID templateId);
    public Template getTemplateByVersion(UUID templateId, int version);
    public List<Template> getTemplateByType(String type);
    public Template updateTemplate(UUID templateId, Template updated);
    public void deleteTemplate(UUID templateId);

    // Get metadata only for all templates
    List<TemplateMetadataDTO> getAllTemplatesMetadata();
}
