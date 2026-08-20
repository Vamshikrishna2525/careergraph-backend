package com.careergraph.service;

import com.careergraph.exception.ResourceNotFoundException;
import com.careergraph.repository.CareerRepository;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Record;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CareerServiceTest {

    private final CareerRepository careerRepository =
            mock(CareerRepository.class);

    private final CareerService careerService =
            new CareerService(careerRepository);

    // ===============================
    // RECOMMENDED JOBS
    // ===============================

    @Test
    void getRecommendedJobs_shouldReturnRecord() {

        Record record = mock(Record.class);

        when(careerRepository.findRecommendedJobs("P001"))
                .thenReturn(record);

        Record result =
                careerService.getRecommendedJobs("P001");

        assertNotNull(result);

        verify(careerRepository)
                .findRecommendedJobs("P001");
    }

    // ===============================
    // RECOMMENDED JOBS - NOT FOUND
    // ===============================

    @Test
    void getRecommendedJobs_shouldThrowExceptionWhenPersonNotFound() {

        when(careerRepository.findRecommendedJobs("P999"))
                .thenReturn(null);

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> careerService.getRecommendedJobs("P999")
                );

        assertEquals(
                "Person not found: P999",
                exception.getMessage()
        );
    }

    // ===============================
    // CANDIDATES
    // ===============================

    @Test
    void getCandidates_shouldReturnRecord() {

        Record record = mock(Record.class);

        when(careerRepository.findCandidates("J001"))
                .thenReturn(record);

        Record result =
                careerService.getCandidates("J001");

        assertNotNull(result);

        verify(careerRepository)
                .findCandidates("J001");
    }

    // ===============================
    // CANDIDATES - NOT FOUND
    // ===============================

    @Test
    void getCandidates_shouldThrowExceptionWhenJobNotFound() {

        when(careerRepository.findCandidates("J999"))
                .thenReturn(null);

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> careerService.getCandidates("J999")
                );

        assertEquals(
                "Job not found: J999",
                exception.getMessage()
        );
    }

    // ===============================
    // SKILL GAP
    // ===============================

    @Test
    void getSkillGap_shouldReturnRecord() {

        Record record = mock(Record.class);

        when(careerRepository.findSkillGap("P001", "J002"))
                .thenReturn(record);

        Record result =
                careerService.getSkillGap("P001", "J002");

        assertNotNull(result);

        verify(careerRepository)
                .findSkillGap("P001", "J002");
    }

    // ===============================
    // SKILL GAP - NOT FOUND
    // ===============================

    @Test
    void getSkillGap_shouldThrowExceptionWhenPersonOrJobNotFound() {

        when(careerRepository.findSkillGap("P999", "J999"))
                .thenReturn(null);

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> careerService.getSkillGap("P999", "J999")
                );

        assertEquals(
                "Person or job not found",
                exception.getMessage()
        );
    }

    // ===============================
    // SIMILAR PROFESSIONALS
    // ===============================

    @Test
    void getSimilarProfessionals_shouldReturnRecord() {

        Record record = mock(Record.class);

        when(careerRepository.findSimilarProfessionals("P001"))
                .thenReturn(record);

        Record result =
                careerService.getSimilarProfessionals("P001");

        assertNotNull(result);

        verify(careerRepository)
                .findSimilarProfessionals("P001");
    }

    // ===============================
    // SIMILAR PROFESSIONALS - NOT FOUND
    // ===============================

    @Test
    void getSimilarProfessionals_shouldThrowExceptionWhenPersonNotFound() {

        when(careerRepository.findSimilarProfessionals("P999"))
                .thenReturn(null);

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> careerService.getSimilarProfessionals("P999")
                );

        assertEquals(
                "Person not found: P999",
                exception.getMessage()
        );
    }
}