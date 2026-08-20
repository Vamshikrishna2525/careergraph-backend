package com.careergraph.dto;

import java.util.List;

public record SimilarProfessionalsResponse(
        String person,
        List<SimilarProfessional> similarProfessionals
) {
}