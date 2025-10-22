package com.smartbeaver.insightly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TemplateMetadataDTO {
    private UUID templateId;
    private String type;
    private int version;
    private String name;
    private String description;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

