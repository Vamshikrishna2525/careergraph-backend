package com.careergraph.dto;

import java.util.List;

public record SimilarProfessional(
        String name,
        List<String> sharedSkills,
        int similarityPercentage
) {
}