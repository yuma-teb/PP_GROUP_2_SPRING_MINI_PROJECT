package com.practice.javagroupiiminiproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {
    private UUID achievementId ;
    private String title ;
    private String description;
    private String badge ;
    private Integer exRequired;
}
