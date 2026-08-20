# CareerGraph


Graph-powered Career Intelligence application built using **Spring Boot** and **CognoDB**.


CareerGraph uses a graph database to connect people, skills, jobs, companies, and courses. It analyzes these relationships to provide career recommendations, candidate ranking, skill-gap analysis, and similar-professional recommendations.


---


## 1. Project Overview


CareerGraph is designed to demonstrate how graph relationships can be used for career intelligence.


The application currently supports:


- Person → Recommended Jobs
- Job → Ranked Candidates
- Person → Job Skill Gap
- Person → Similar Professionals
- Match percentages
- Shared skills analysis
- REST APIs
- Swagger/OpenAPI documentation
- Web-based dashboard


The backend is implemented using Spring Boot and the graph data is stored in CognoDB using Cypher queries.


---


## 2. Why a Graph Database?


Career data is highly relationship-oriented.


A traditional relational database would require several tables and joins to answer questions such as:


- Which jobs match a person's skills?
- Which candidates are suitable for a job?
- Which professionals have similar skills?
- Which skills is a person missing for a particular job?
- Which companies are connected to jobs and professionals?
- Which courses teach skills required for a particular job?


A graph database represents these relationships directly.


For example:


```text
(Person)
   |
   | HAS_SKILL
   ↓
(Skill)
   ↑
   | REQUIRES
   |
(Job)
   |
   | OFFERED_BY
   ↓
(Company)

This makes relationship-heavy career queries easier to express and understand.

CareerGraph therefore uses graph traversal and relationship matching instead of relying primarily on large numbers of relational joins.

3. Graph Data Model
Nodes

The current graph contains the following node types:

Person
Skill
Job
Company
Course
Relationships

The graph currently contains:

Person -[:HAS_SKILL]-> Skill


Job -[:REQUIRES]-> Skill


Job -[:OFFERED_BY]-> Company


Person -[:WORKED_AT]-> Company


Course -[:TEACHES]-> Skill


Person -[:COMPLETED]-> Course
Simplified Graph
                         ┌─────────────┐
                         │   Company   │
                         └──────▲──────┘
                                │
                           OFFERED_BY
                                │
┌──────────┐  HAS_SKILL   ┌─────┴─────┐   REQUIRES   ┌──────────┐
│  Person  │─────────────►│   Skill   │◄────────────│   Job    │
└────┬─────┘              └─────▲─────┘              └──────────┘
     │                          │
     │ COMPLETED                │ TEACHES
     ▼                          │
┌──────────┐              ┌────┴─────┐
│  Course  │─────────────►│  Skill   │
└──────────┘              └──────────┘


Person ──WORKED_AT──► Company
4. Seed Data

The project includes seed data in:

data/seed.cypher

The seed data contains:

Skills

Examples:

Java
Spring Boot
React
JavaScript
SQL
Python
HTML
CSS
Git
REST API
People

Examples:

Rahul Sharma
Priya Reddy
Arjun Kumar
Sneha Patel
Kiran Rao
Ananya Singh
Jobs

Examples:

Java Developer
Spring Boot Developer
Full Stack Developer
Backend Developer
Software Engineer
Companies

Examples:

TechNova Solutions
Infosys
Wipro
TCS
NextGen Technologies
Courses

Examples:

Java Programming
Spring Boot Masterclass
React Fundamentals
SQL Essentials
Python for Developers
5. Main Features
5.1 Recommended Jobs

Endpoint:

GET /api/persons/{personId}/jobs

Example:

GET /api/persons/P001/jobs

For Rahul Sharma, the application analyzes his skills and returns matching jobs.

Example response:

{
  "person": "Rahul Sharma",
  "matchingSkills": [
    "Java",
    "Spring Boot",
    "SQL",
    "Git"
  ],
  "recommendedJobs": [
    "Java Developer",
    "Spring Boot Developer",
    "Full Stack Developer",
    "Backend Developer",
    "Software Engineer"
  ]
}
5.2 Ranked Candidates

Endpoint:

GET /api/jobs/{jobId}/candidates

Example:

GET /api/jobs/J001/candidates

The application calculates the percentage of required job skills possessed by each candidate and ranks candidates accordingly.

Example:

{
  "job": "Java Developer",
  "requiredSkills": [
    "Java",
    "SQL",
    "Git"
  ],
  "candidates": [
    {
      "matchPercentage": 100,
      "name": "Rahul Sharma",
      "matchingSkills": [
        "Java",
        "SQL",
        "Git"
      ]
    }
  ]
}
5.3 Skill Gap Analysis

Endpoint:

GET /api/persons/{personId}/skill-gap/{jobId}

Example:

GET /api/persons/P001/skill-gap/J002

The application compares:

Person Skills
        VS
Job Required Skills

and returns:

Matching skills
Missing skills

Example:

{
  "person": "Rahul Sharma",
  "job": "Spring Boot Developer",
  "matchingSkills": [
    "Java",
    "Spring Boot",
    "SQL"
  ],
  "missingSkills": [
    "REST API"
  ]
}
5.4 Similar Professionals

Endpoint:

GET /api/persons/{personId}/similar-professionals

Example:

GET /api/persons/P001/similar-professionals

The application compares the skills of the selected person with other professionals.

Similarity is calculated using:

Similarity Percentage =
(Number of Shared Skills / Total Skills of Selected Person) × 100

Example:

{
  "person": "Rahul Sharma",
  "similarProfessionals": [
    {
      "name": "Kiran Rao",
      "sharedSkills": [
        "Java",
        "Spring Boot",
        "SQL"
      ],
      "similarityPercentage": 75
    }
  ]
}
6. Architecture
                 ┌─────────────────────┐
                 │     Web Browser     │
                 │   HTML / CSS / JS   │
                 └──────────┬──────────┘
                            │
                            │ HTTP
                            ▼
                 ┌─────────────────────┐
                 │   CareerController  │
                 │     REST APIs       │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │    CareerService    │
                 │ Business Logic      │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │  CareerRepository   │
                 │   Cypher Queries    │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │      CognoDB        │
                 │    Graph Database   │
                 └─────────────────────┘
7. Project Structure
careergraph-backend/
│
├── data/
│   └── seed.cypher
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/careergraph/
│   │   │       ├── CareergraphBackendApplication.java
│   │   │       │
│   │   │       ├── config/
│   │   │       │   └── CognoDBConfig.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── CareerController.java
│   │   │       │   └── HealthController.java
│   │   │       │
│   │   │       ├── dto/
│   │   │       │   ├── CandidateResponse.java
│   │   │       │   ├── RecommendedJobsResponse.java
│   │   │       │   ├── SimilarProfessional.java
│   │   │       │   ├── SimilarProfessionalsResponse.java
│   │   │       │   └── SkillGapResponse.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── ResourceNotFoundException.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   └── CareerRepository.java
│   │   │       │
│   │   │       └── service/
│   │   │           └── CareerService.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── app.js
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/careergraph/
│               ├── CareergraphBackendApplicationTests.java
│               ├── controller/
│               │   └── CareerControllerTest.java
│               └── service/
│                   └── CareerServiceTest.java
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
8. Technologies Used
Java 25 LTS
Spring Boot 4.1.0
Spring Web MVC
Spring Validation
CognoDB
Neo4j Java Driver
Cypher
Maven
HTML
CSS
JavaScript
Swagger / OpenAPI
JUnit
Mockito
9. Configuration

The application uses environment variables for database credentials.

application.properties:

spring.application.name=careergraph-backend


cognodb.uri=${COGNODB_URI}
cognodb.username=${COGNODB_USERNAME}
cognodb.password=${COGNODB_PASSWORD}


server.port=8080

Set the following environment variables:

COGNODB_URI
COGNODB_USERNAME
COGNODB_PASSWORD

Database credentials are intentionally not stored in the repository.

10. Running the Application
Step 1: Clone the repository
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd careergraph-backend
Step 2: Configure environment variables

Set:

COGNODB_URI
COGNODB_USERNAME
COGNODB_PASSWORD
Step 3: Load seed data

Run the contents of:

data/seed.cypher

against the CognoDB database.

Step 4: Run the application

Windows:

.\mvnw.cmd spring-boot:run

Or:

.\mvnw.cmd clean package
java -jar target/careergraph-backend-0.0.1-SNAPSHOT.jar

The application runs on:

http://localhost:8080
11. Swagger API Documentation

Once the application is running, Swagger UI is available at:

http://localhost:8080/swagger-ui/index.html

The Swagger interface can be used to test the REST APIs.

12. API Summary
Feature	Method	Endpoint
Recommended Jobs	GET	/api/persons/{personId}/jobs
Ranked Candidates	GET	/api/jobs/{jobId}/candidates
Skill Gap	GET	/api/persons/{personId}/skill-gap/{jobId}
Similar Professionals	GET	/api/persons/{personId}/similar-professionals
Health Check	GET	/health
13. Important Cypher Operations
Recommended Jobs

The query traverses:

Person → HAS_SKILL → Skill ← REQUIRES ← Job

This finds jobs that require skills possessed by the person.

Ranked Candidates

The query traverses:

Job → REQUIRES → Skill

and compares those required skills against:

Person → HAS_SKILL → Skill

The candidate score is calculated as:

Matching Required Skills
------------------------ × 100
Total Required Skills

Candidates are then ordered from highest match percentage to lowest.

Skill Gap

The query compares:

Required Job Skills
        -
Person Skills

The result produces:

matchingSkills
missingSkills
Similar Professionals

The query traverses:

Person → HAS_SKILL → Skill ← HAS_SKILL ← Person

The number of shared skills is compared against the selected person's total skills to calculate similarity.

14. Error Handling

The application provides centralized error handling.

For example, requesting an unknown person:

GET /api/persons/P999/jobs

returns:

{
  "status": 404,
  "error": "Not Found",
  "message": "Person not found: P999"
}

This prevents internal exceptions from being exposed directly to API users.

15. Testing

The project includes controller, service, and application tests.

Run:

.\mvnw.cmd clean test

Current test result:

Tests run: 17
Failures: 0
Errors: 0
Skipped: 0


BUILD SUCCESS
16. Web UI

The application provides a simple CareerGraph dashboard.

The dashboard includes:

Career Analysis
Person information
Matching skills
Recommended jobs
Ranked candidates
Skill gap analysis
Similar professionals

Example workflow:

Enter Person ID
      ↓
Analyze Career
      ↓
View Skills
      ↓
View Recommended Jobs
      ↓
Check Job Skill Gap
      ↓
View Similar Professionals
      ↓
Find Ranked Candidates
17. Demo

Hosted Demo:

TODO: Add deployed application URL

Swagger:

TODO: Add deployed Swagger URL
18. Screenshots

Add screenshots of the working application here before submission.

Career Analysis
TODO: Add screenshot
Recommended Jobs
TODO: Add screenshot
Ranked Candidates
TODO: Add screenshot
Skill Gap
TODO: Add screenshot
Similar Professionals
TODO: Add screenshot
19. Future Improvements

Possible future enhancements include:

Location-aware job recommendations
Experience-based job matching
Course recommendations based on skill gaps
Company recommendations
Career-path visualization
Authentication
Resume-based skill extraction
More advanced graph-based ranking
Interactive graph visualization
20. Conclusion

CareerGraph demonstrates how a graph database can be used to model and analyze interconnected career data.

By representing people, skills, jobs, companies, and courses as connected graph entities, the application can answer relationship-heavy career questions such as:

What jobs match a person's skills?
Which candidates best match a job?
What skills does a person need to learn?
Which professionals have similar skill profiles?

The project combines Spring Boot, Cypher, CognoDB, REST APIs, and a web dashboard to provide a practical graph-powered career intelligence system.