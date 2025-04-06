package com.practice.javagroupiiminiproject.model.request;

import java.time.LocalDateTime;

import com.practice.javagroupiiminiproject.model.entity.AppUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitRequest {
	private String title;
	private String description;
	private String frequency;
	private Boolean isActived;
  private AppUser appUser;
	private LocalDateTime createAt;
}
