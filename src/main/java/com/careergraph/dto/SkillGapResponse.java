package com.careergraph.dto;

import java.util.List;

public record SkillGapResponse(
        String person,
        String job,
        List<String> matchingSkills,
        List<String> missingSkills
) {
}