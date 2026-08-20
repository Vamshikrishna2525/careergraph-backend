package com.careergraph.controller;

import com.careergraph.dto.CandidateResponse;
import com.careergraph.dto.RecommendedJobsResponse;
import com.careergraph.dto.SkillGapResponse;
import com.careergraph.service.CareerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.constraints.NotBlank;

import org.neo4j.driver.Record;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.careergraph.dto.SimilarProfessionalsResponse;
import com.careergraph.dto.SimilarProfessional;
import com.careergraph.dto.SimilarProfessionalsResponse;

import java.util.List;

@RestController
@Validated
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    // ===============================
    // PERSON -> RECOMMENDED JOBS
    // ===============================

    @Operation(
            summary = "Get recommended jobs",
            description = "Returns jobs recommended for a person based on their skills."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Recommended jobs retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Person not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid person ID"
            )
    })
    @GetMapping("/api/persons/{personId}/jobs")
    public RecommendedJobsResponse getRecommendedJobs(

            @Parameter(
                    description = "Unique ID of the person",
                    example = "P001"
            )
            @PathVariable @NotBlank String personId) {

        Record record = careerService.getRecommendedJobs(personId);

        List<String> matchingSkills =
                record.get("matchingSkills")
                        .asList(value -> value.asString());

        List<String> recommendedJobs =
                record.get("recommendedJobs")
                        .asList(value -> value.asString());

        return new RecommendedJobsResponse(
                record.get("person").asString(),
                matchingSkills,
                recommendedJobs
        );
    }

    // ===============================
    // JOB -> RANKED CANDIDATES
    // ===============================

    @Operation(
            summary = "Get ranked candidates",
            description = "Returns candidates ranked according to how well their skills match the required skills for a job."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Candidates retrieved and ranked successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Job not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid job ID"
            )
    })
    @GetMapping("/api/jobs/{jobId}/candidates")
    public CandidateResponse getCandidates(

            @Parameter(
                    description = "Unique ID of the job",
                    example = "J001"
            )
            @PathVariable @NotBlank String jobId) {

        Record record = careerService.getCandidates(jobId);

        return new CandidateResponse(
                record.get("job").asString(),

                record.get("requiredSkills")
                        .asList(value -> value.asString()),

                record.get("candidates")
                        .asList(value -> value.asMap())
        );
    }
 // ===============================
 // PERSON -> SIMILAR PROFESSIONALS
 // ===============================

 @Operation(
         summary = "Find similar professionals",
         description = "Returns other professionals who share skills with the specified person, ranked by similarity."
 )
 @ApiResponses({
         @ApiResponse(
                 responseCode = "200",
                 description = "Similar professionals retrieved successfully"
         ),
         @ApiResponse(
                 responseCode = "404",
                 description = "Person not found"
         ),
         @ApiResponse(
                 responseCode = "400",
                 description = "Invalid person ID"
         )
 })
 @GetMapping("/api/persons/{personId}/similar-professionals")
 public SimilarProfessionalsResponse getSimilarProfessionals(

         @Parameter(
                 description = "Unique ID of the person",
                 example = "P001"
         )
         @PathVariable @NotBlank String personId) {

     Record record = careerService.getSimilarProfessionals(personId);

     List<SimilarProfessional> similarProfessionals =
             record.get("similarProfessionals")
                     .asList(value -> {

                         var map = value.asMap();

                         List<String> sharedSkills =
                                 ((List<?>) map.get("sharedSkills"))
                                         .stream()
                                         .map(Object::toString)
                                         .toList();

                         String name =
                                 map.get("name").toString();

                         int similarityPercentage =
                                 ((Number) map.get("similarityPercentage"))
                                         .intValue();

                         return new SimilarProfessional(
                                 name,
                                 sharedSkills,
                                 similarityPercentage
                         );
                     });

     return new SimilarProfessionalsResponse(
             record.get("person").asString(),
             similarProfessionals
     );
 }
    // ===============================
    // PERSON -> SKILL GAP
    // ===============================

    @Operation(
            summary = "Analyze skill gap",
            description = "Returns the skills a person has, the skills required by a job, and the skills missing for that job."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Skill gap analyzed successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Person or job not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid person ID or job ID"
            )
    })
    @GetMapping("/api/persons/{personId}/skill-gap/{jobId}")
    public SkillGapResponse getSkillGap(

            @Parameter(
                    description = "Unique ID of the person",
                    example = "P001"
            )
            @PathVariable @NotBlank String personId,

            @Parameter(
                    description = "Unique ID of the job",
                    example = "J002"
            )
            @PathVariable @NotBlank String jobId) {

        Record record = careerService.getSkillGap(
                personId,
                jobId
        );

        List<String> matchingSkills =
                record.get("matchingSkills")
                        .asList(value -> value.asString());

        List<String> missingSkills =
                record.get("missingSkills")
                        .asList(value -> value.asString());

        return new SkillGapResponse(
                record.get("person").asString(),
                record.get("job").asString(),
                matchingSkills,
                missingSkills
        );
    }
}