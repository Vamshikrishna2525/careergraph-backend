package com.careergraph.service;

import com.careergraph.exception.ResourceNotFoundException;
import com.careergraph.repository.CareerRepository;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Service;

@Service
public class CareerService {

    private final CareerRepository careerRepository;

    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    public Record getRecommendedJobs(String personId) {

        Record record = careerRepository.findRecommendedJobs(personId);

        if (record == null) {
            throw new ResourceNotFoundException(
                    "Person not found: " + personId
            );
        }

        return record;
    }

    public Record getCandidates(String jobId) {

        Record record = careerRepository.findCandidates(jobId);

        if (record == null) {
            throw new ResourceNotFoundException(
                    "Job not found: " + jobId
            );
        }

        return record;
    }
 // ===============================
 // PERSON -> SIMILAR PROFESSIONALS
 // ===============================

 public Record getSimilarProfessionals(String personId) {

     Record record = careerRepository.findSimilarProfessionals(personId);

     if (record == null) {
         throw new ResourceNotFoundException(
                 "Person not found: " + personId
         );
     }

     return record;
 }

    public Record getSkillGap(String personId, String jobId) {

        Record record = careerRepository.findSkillGap(
                personId,
                jobId
        );

        if (record == null) {
            throw new ResourceNotFoundException(
                    "Person or job not found"
            );
        }

        return record;
    }
}