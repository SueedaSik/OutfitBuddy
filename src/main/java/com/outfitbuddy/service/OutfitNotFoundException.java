package com.outfitbuddy.service;

public class OutfitNotFoundException extends RuntimeException {

    public OutfitNotFoundException(Long id) {
        super("Outfit mit ID " + id + " wurde nicht gefunden");
    }
}
