package com.careergraph.repository;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CareerRepository {

    private final Driver driver;

    public CareerRepository(Driver driver) {
        this.driver = driver;
    }

    // ===============================
    // PERSON -> RECOMMENDED JOBS
    // ===============================

    public Record findRecommendedJobs(String personId) {

        String query = """
                MATCH (p:Person {id: $personId})
                      -[:HAS_SKILL]->(s:Skill)
                      <-[:REQUIRES]-(j:Job)
                RETURN p.name AS person,
                       collect(DISTINCT s.name) AS matchingSkills,
                       collect(DISTINCT j.title) AS recommendedJobs
                """;

        try (var session = driver.session()) {

            var result = session.run(
                    query,
                    Map.of("personId", personId)
            );

            if (!result.hasNext()) {
                return null;
            }

            return result.single();
        }
    }

    // ===============================
    // JOB -> RANKED CANDIDATES
    // ===============================

    public Record findCandidates(String jobId) {

        String query = """
                MATCH (j:Job {id: $jobId})
                MATCH (j)-[:REQUIRES]->(required:Skill)
                WITH j, collect(required) AS requiredSkills

                MATCH (p:Person)-[:HAS_SKILL]->(skill:Skill)
                WITH j, requiredSkills, p,
                     collect(
                         CASE
                             WHEN skill IN requiredSkills
                             THEN skill.name
                         END
                     ) AS matchedSkills

                WITH j, requiredSkills, p,
                     [skill IN matchedSkills
                      WHERE skill IS NOT NULL]
                     AS actualMatchedSkills

                WITH j, requiredSkills, p, actualMatchedSkills,
                     size(requiredSkills) AS totalRequired

                WITH j, requiredSkills, p, actualMatchedSkills,
                     CASE
                         WHEN totalRequired = 0 THEN 0
                         ELSE round(
                             (toFloat(size(actualMatchedSkills))
                             / totalRequired) * 100
                         )
                     END AS matchPercentage

                WITH j, requiredSkills,
                     collect({
                         name: p.name,
                         matchingSkills: actualMatchedSkills,
                         matchPercentage: matchPercentage
                     }) AS allCandidates

                UNWIND allCandidates AS candidate

                WITH j, requiredSkills, candidate
                ORDER BY candidate.matchPercentage DESC

                WITH j, requiredSkills,
                     collect(candidate) AS candidates

                RETURN j.title AS job,
                       [skill IN requiredSkills | skill.name]
                       AS requiredSkills,
                       candidates
                """;

        try (var session = driver.session()) {

            var result = session.run(
                    query,
                    Map.of("jobId", jobId)
            );

            if (!result.hasNext()) {
                return null;
            }

            return result.single();
        }
    }
 // ===============================
 // PERSON -> SIMILAR PROFESSIONALS
 // ===============================

 public Record findSimilarProfessionals(String personId) {

     String query = """
             MATCH (p:Person {id: $personId})
                   -[:HAS_SKILL]->(personSkill:Skill)

             WITH p,
                  collect(DISTINCT personSkill) AS personSkills

             MATCH (p)-[:HAS_SKILL]->(shared:Skill)
                   <-[:HAS_SKILL]-(similar:Person)

             WHERE p <> similar

             WITH p,
                  personSkills,
                  similar,
                  collect(DISTINCT shared.name) AS sharedSkills

             WITH p,
                  personSkills,
                  similar,
                  sharedSkills,
                  size(personSkills) AS totalPersonSkills,
                  size(sharedSkills) AS sharedSkillCount

             WITH p,
                  similar,
                  sharedSkills,
                  CASE
                      WHEN totalPersonSkills = 0 THEN 0
                      ELSE round(
                          (toFloat(sharedSkillCount)
                          / totalPersonSkills) * 100
                      )
                  END AS similarityPercentage

             WITH p,
                  collect({
                      name: similar.name,
                      sharedSkills: sharedSkills,
                      similarityPercentage: similarityPercentage
                  }) AS allProfessionals

             UNWIND allProfessionals AS professional

             WITH p, professional

             ORDER BY professional.similarityPercentage DESC

             WITH p,
                  collect(professional) AS similarProfessionals

             RETURN p.name AS person,
                    similarProfessionals
             """;

     try (var session = driver.session()) {

         var result = session.run(
                 query,
                 Map.of("personId", personId)
         );

         if (!result.hasNext()) {
             return null;
         }

         return result.single();
     }
 }
    // ===============================
    // PERSON -> SKILL GAP
    // ===============================

    public Record findSkillGap(String personId, String jobId) {

        String query = """
                MATCH (p:Person {id: $personId})
                MATCH (j:Job {id: $jobId})

                OPTIONAL MATCH (j)-[:REQUIRES]->(required:Skill)
                OPTIONAL MATCH (p)-[:HAS_SKILL]->(owned:Skill)

                WITH p, j,
                     collect(DISTINCT required.name)
                     AS requiredSkills,
                     collect(DISTINCT owned.name)
                     AS ownedSkills

                WITH p, j, requiredSkills, ownedSkills,
                     [skill IN requiredSkills
                      WHERE NOT skill IN ownedSkills]
                     AS missingSkills,
                     [skill IN requiredSkills
                      WHERE skill IN ownedSkills]
                     AS matchingSkills

                RETURN p.name AS person,
                       j.title AS job,
                       matchingSkills,
                       missingSkills
                """;

        try (var session = driver.session()) {

            var result = session.run(
                    query,
                    Map.of(
                            "personId", personId,
                            "jobId", jobId
                    )
            );

            if (!result.hasNext()) {
                return null;
            }

            return result.single();
        }
    }
}