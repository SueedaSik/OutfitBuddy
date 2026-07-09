package com.outfitbuddy.controller;

import com.outfitbuddy.model.Outfit;
import com.outfitbuddy.service.OutfitNotFoundException;
import com.outfitbuddy.service.OutfitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/outfits")
public class OutfitController {

    private final OutfitService outfitService;

    public OutfitController(OutfitService outfitService) {
        this.outfitService = outfitService;
    }

    @GetMapping
    public List<Outfit> getAllOutfits() {
        return outfitService.findAll();
    }

    @GetMapping("/{id}")
    public Outfit getOutfit(@PathVariable Long id) {
        return outfitService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Outfit createOutfit(@Valid @RequestBody Outfit outfit) {
        return outfitService.create(outfit);
    }

    @PutMapping("/{id}")
    public Outfit updateOutfit(@PathVariable Long id, @Valid @RequestBody Outfit outfit) {
        return outfitService.update(id, outfit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOutfit(@PathVariable Long id) {
        outfitService.delete(id);
    }

    @ExceptionHandler(OutfitNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(OutfitNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
