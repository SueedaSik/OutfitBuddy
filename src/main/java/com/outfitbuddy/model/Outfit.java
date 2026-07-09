package com.outfitbuddy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    private LocalDate lastWorn;

    public Outfit() {
    }

    public Outfit(String name, String category, LocalDate lastWorn) {
        this.name = name;
        this.category = category;
        this.lastWorn = lastWorn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getLastWorn() {
        return lastWorn;
    }

    public void setLastWorn(LocalDate lastWorn) {
        this.lastWorn = lastWorn;
    }
}
