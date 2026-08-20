package com.careergraph.dto;

import java.util.List;

public record RecommendedJobsResponse(
        String person,
        List<String> matchingSkills,
        List<String> recommendedJobs
) {
}