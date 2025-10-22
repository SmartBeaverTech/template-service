package com.smartbeaver.insightly.dao.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    private String name;
    private String description;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
