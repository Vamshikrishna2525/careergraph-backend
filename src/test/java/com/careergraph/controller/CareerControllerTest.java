package com.careergraph.controller;

import com.careergraph.exception.ResourceNotFoundException;
import com.careergraph.service.CareerService;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.neo4j.driver.Values.value;

@WebMvcTest(CareerController.class)
class CareerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CareerService careerService;


    // ===============================
    // PERSON -> RECOMMENDED JOBS
    // ===============================

    @Test
    void getRecommendedJobs_shouldReturn200() throws Exception {

        Record record = org.mockito.Mockito.mock(Record.class);

        when(record.get("person"))
                .thenReturn(value("Rahul Sharma"));

        when(record.get("matchingSkills"))
                .thenReturn(value(
                        List.of(
                                "Java",
                                "Spring Boot",
                                "SQL",
                                "Git"
                        )
                ));

        when(record.get("recommendedJobs"))
                .thenReturn(value(
                        List.of(
                                "Java Developer",
                                "Spring Boot Developer",
                                "Full Stack Developer"
                        )
                ));

        when(careerService.getRecommendedJobs("P001"))
                .thenReturn(record);

        mockMvc.perform(
                get("/api/persons/P001/jobs")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.person")
                .value("Rahul Sharma"))
        .andExpect(jsonPath("$.matchingSkills[0]")
                .value("Java"))
        .andExpect(jsonPath("$.recommendedJobs[0]")
                .value("Java Developer"));
    }


    // ===============================
    // PERSON NOT FOUND
    // ===============================

    @Test
    void getRecommendedJobs_shouldReturn404() throws Exception {

        when(careerService.getRecommendedJobs("P999"))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Person not found: P999"
                        )
                );

        mockMvc.perform(
                get("/api/persons/P999/jobs")
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status")
                .value(404))
        .andExpect(jsonPath("$.error")
                .value("Not Found"))
        .andExpect(jsonPath("$.message")
                .value("Person not found: P999"));
    }


    // ===============================
    // JOB -> RANKED CANDIDATES
    // ===============================

    @Test
    void getCandidates_shouldReturn200() throws Exception {

        Record record = org.mockito.Mockito.mock(Record.class);

        when(record.get("job"))
                .thenReturn(value("Java Developer"));

        when(record.get("requiredSkills"))
                .thenReturn(value(
                        List.of(
                                "Java",
                                "SQL",
                                "Git"
                        )
                ));

        when(record.get("candidates"))
                .thenReturn(value(
                        List.of(
                                Map.of(
                                        "name",
                                        "Rahul Sharma",

                                        "matchingSkills",
                                        List.of(
                                                "Java",
                                                "SQL",
                                                "Git"
                                        ),

                                        "matchPercentage",
                                        100
                                )
                        )
                ));

        when(careerService.getCandidates("J001"))
                .thenReturn(record);

        mockMvc.perform(
                get("/api/jobs/J001/candidates")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.job")
                .value("Java Developer"))
        .andExpect(jsonPath("$.requiredSkills[0]")
                .value("Java"))
        .andExpect(jsonPath("$.candidates[0].name")
                .value("Rahul Sharma"))
        .andExpect(jsonPath("$.candidates[0].matchPercentage")
                .value(100));
    }


    // ===============================
    // JOB NOT FOUND
    // ===============================

    @Test
    void getCandidates_shouldReturn404() throws Exception {

        when(careerService.getCandidates("J999"))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Job not found: J999"
                        )
                );

        mockMvc.perform(
                get("/api/jobs/J999/candidates")
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status")
                .value(404))
        .andExpect(jsonPath("$.error")
                .value("Not Found"))
        .andExpect(jsonPath("$.message")
                .value("Job not found: J999"));
    }
    // ===============================
    // PERSON -> SIMILAR PROFESSIONALS
    // ===============================

    @Test
    void getSimilarProfessionals_shouldReturn200() throws Exception {

        Record record = org.mockito.Mockito.mock(Record.class);

        when(record.get("person"))
                .thenReturn(value("Rahul Sharma"));

        when(record.get("similarProfessionals"))
                .thenReturn(value(
                        List.of(
                                Map.of(
                                        "name", "Kiran Rao",
                                        "sharedSkills",
                                        List.of(
                                                "Java",
                                                "Spring Boot",
                                                "SQL"
                                        ),
                                        "similarityPercentage", 75
                                ),
                                Map.of(
                                        "name", "Sneha Patel",
                                        "sharedSkills",
                                        List.of(
                                                "Java",
                                                "Spring Boot"
                                        ),
                                        "similarityPercentage", 50
                                )
                        )
                ));

        when(careerService.getSimilarProfessionals("P001"))
                .thenReturn(record);

        mockMvc.perform(
                get("/api/persons/P001/similar-professionals")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.person")
                .value("Rahul Sharma"))
        .andExpect(jsonPath("$.similarProfessionals[0].name")
                .value("Kiran Rao"))
        .andExpect(jsonPath(
                "$.similarProfessionals[0].similarityPercentage"
        ).value(75))
        .andExpect(jsonPath(
                "$.similarProfessionals[0].sharedSkills[0]"
        ).value("Java"))
        .andExpect(jsonPath("$.similarProfessionals[1].name")
                .value("Sneha Patel"));
    }


    // ===============================
    // SIMILAR PROFESSIONALS - NOT FOUND
    // ===============================

    @Test
    void getSimilarProfessionals_shouldReturn404() throws Exception {

        when(careerService.getSimilarProfessionals("P999"))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Person not found: P999"
                        )
                );

        mockMvc.perform(
                get("/api/persons/P999/similar-professionals")
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status")
                .value(404))
        .andExpect(jsonPath("$.error")
                .value("Not Found"))
        .andExpect(jsonPath("$.message")
                .value("Person not found: P999"));
    }

    // ===============================
    // PERSON -> SKILL GAP
    // ===============================

    @Test
    void getSkillGap_shouldReturn200() throws Exception {

        Record record = org.mockito.Mockito.mock(Record.class);

        when(record.get("person"))
                .thenReturn(value("Rahul Sharma"));

        when(record.get("job"))
                .thenReturn(value("Spring Boot Developer"));

        when(record.get("matchingSkills"))
                .thenReturn(value(
                        List.of(
                                "Java",
                                "Spring Boot",
                                "SQL"
                        )
                ));

        when(record.get("missingSkills"))
                .thenReturn(value(
                        List.of(
                                "REST API"
                        )
                ));

        when(careerService.getSkillGap("P001", "J002"))
                .thenReturn(record);

        mockMvc.perform(
                get("/api/persons/P001/skill-gap/J002")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.person")
                .value("Rahul Sharma"))
        .andExpect(jsonPath("$.job")
                .value("Spring Boot Developer"))
        .andExpect(jsonPath("$.matchingSkills[0]")
                .value("Java"))
        .andExpect(jsonPath("$.missingSkills[0]")
                .value("REST API"));
    }


    // ===============================
    // SKILL GAP NOT FOUND
    // ===============================

    @Test
    void getSkillGap_shouldReturn404() throws Exception {

        when(careerService.getSkillGap("P999", "J999"))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Person or job not found"
                        )
                );

        mockMvc.perform(
                get("/api/persons/P999/skill-gap/J999")
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status")
                .value(404))
        .andExpect(jsonPath("$.error")
                .value("Not Found"))
        .andExpect(jsonPath("$.message")
                .value("Person or job not found"));
    }
}