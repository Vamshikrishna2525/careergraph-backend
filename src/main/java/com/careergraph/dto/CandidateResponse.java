package com.careergraph.dto;

import java.util.List;
import java.util.Map;

public record CandidateResponse(
        String job,
        List<String> requiredSkills,
        List<Map<String, Object>> candidates
) {
}