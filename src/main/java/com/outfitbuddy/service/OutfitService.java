package com.outfitbuddy.service;

import com.outfitbuddy.model.Outfit;
import com.outfitbuddy.repository.OutfitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutfitService {

    private final OutfitRepository outfitRepository;

    public OutfitService(OutfitRepository outfitRepository) {
        this.outfitRepository = outfitRepository;
    }

    public List<Outfit> findAll() {
        return outfitRepository.findAll();
    }

    public Outfit findById(Long id) {
        return outfitRepository.findById(id)
                .orElseThrow(() -> new OutfitNotFoundException(id));
    }

    public Outfit create(Outfit outfit) {
        outfit.setId(null);
        return outfitRepository.save(outfit);
    }

    public Outfit update(Long id, Outfit outfitDetails) {
        Outfit outfit = findById(id);
        outfit.setName(outfitDetails.getName());
        outfit.setCategory(outfitDetails.getCategory());
        outfit.setLastWorn(outfitDetails.getLastWorn());
        return outfitRepository.save(outfit);
    }

    public void delete(Long id) {
        Outfit outfit = findById(id);
        outfitRepository.delete(outfit);
    }
}
