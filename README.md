CareerGraph

Graph-Powered Career Intelligence

CareerGraph is a graph-based career intelligence application that uses CognoDB to model relationships between people, skills, jobs, companies, and courses.

The application provides career analysis and relationship-driven recommendations such as suitable jobs, ranked candidates, skill gaps, and similar professionals.

Overview

Traditional career matching systems often depend on large numbers of relational joins and manually maintained matching logic.

CareerGraph models career information as a connected graph, making relationship-based queries easier to express and understand.

The application connects a Spring Boot backend with CognoDB and provides a simple web interface for exploring career relationships.

Core capabilities

Analyze a person's career profile

Identify matching skills

Recommend suitable jobs

Rank candidates for a job based on skill overlap

Identify missing skills for a candidate

Find professionals with similar skill profiles

Query career relationships using graph traversal

Expose functionality through REST APIs

Provide a browser-based interface for interacting with the graph

Features

1. Career Analysis

Given a person ID, CareerGraph retrieves the person's profile and matching skills and recommends suitable job roles.

Example

Person: Rahul Sharma

Skills:
- Java
- Spring Boot
- SQL
- Git

Recommended Jobs:
- Java Developer
- Spring Boot Developer
- Full Stack Developer
- Backend Developer
- Software Engineer

2. Ranked Candidates

CareerGraph can find and rank candidates for a particular job.

For example, a Spring Boot Developer position may require:

Java
Spring Boot
SQL
REST API

Candidates are ranked according to the percentage of required skills they possess.

Example

Kiran Rao        100%
Rahul Sharma      75%
Sneha Patel       75%
Arjun Kumar       50%
Priya Reddy       25%
Ananya Singh       0%

This demonstrates graph-based skill matching rather than simply returning an unranked list of candidates.

3. Skill Gap Analysis

CareerGraph compares a person's skills against the skills required for a particular job.

Example

Job: Spring Boot Developer

Matching Skills:
- Java
- Spring Boot
- SQL

Missing Skills:
- REST API

This allows the system to identify the skills a candidate needs to develop for a target role.

4. Similar Professionals

CareerGraph can identify professionals with similar skill profiles.

Similarity is calculated from overlapping skills between professionals.

Example

Kiran Rao        75%
Sneha Patel      50%
Arjun Kumar      50%
Priya Reddy      25%
Ananya Singh     25%

This demonstrates how graph relationships can be used to discover related professional profiles.

Screenshots

Career Analysis

The main CareerGraph interface allows a person to be analyzed using their person ID. The application displays the person's matching skills and recommended career roles.



Ranked Candidates

CareerGraph ranks candidates according to how many of the required job skills they possess.



Skill Gap and Similar Professionals

The application identifies matching and missing skills for a target job and also finds professionals with similar skill profiles.



Graph Data Model

CareerGraph uses a graph model to represent career-related entities and their relationships.

Nodes

The graph contains the following node types:

Person

Skill

Job

Company

Course

Relationships

The current graph uses relationships such as:

Person -[:HAS_SKILL]-> Skill
Job -[:REQUIRES]-> Skill
Job -[:OFFERED_BY]-> Company
Person -[:WORKED_AT]-> Company
Course -[:TEACHES]-> Skill
Person -[:COMPLETED]-> Course

These relationships allow career questions to be represented as graph traversal problems.

For example:

Person
   |
   | HAS_SKILL
   v
 Skill
   ^
   | REQUIRES
   |
  Job

A candidate can therefore be compared with a job by traversing from the person to their skills and comparing those skills with the skills required by the job.

Why a Graph Database?

Career data is naturally relationship-heavy.

A person can have multiple skills, work for multiple companies, complete multiple courses, and be suitable for multiple jobs.

Similarly, a job can require multiple skills and be offered by a company.

Representing these relationships directly as graph edges makes relationship-oriented queries easier to express.

For example:

Person -> Skills -> Jobs
Person -> Skills -> Similar Professionals
Job -> Required Skills -> Candidates
Person -> Skills -> Missing Job Skills

CareerGraph therefore uses graph traversal and relationship matching instead of relying primarily on large numbers of relational joins.

Career Matching Logic

The application uses skill overlap to determine how closely a candidate matches a job.

Matching Percentage

Matching Percentage =
Number of Matching Skills
------------------------- × 100
Number of Required Skills

For example, if a job requires:

Java
Spring Boot
SQL
REST API

and a candidate has:

Java
Spring Boot
SQL

then:

Matching Skills = 3
Required Skills = 4

Match = 3 / 4 × 100
      = 75%

The same relationship-based matching approach is used for candidate ranking and skill-gap analysis.

Application Architecture

The application follows a layered Spring Boot architecture.

                     ┌──────────────────────┐
                     │      Web Browser     │
                     │   HTML / CSS / JS    │
                     └──────────┬───────────┘
                                │
                                │ HTTP
                                ▼
                     ┌──────────────────────┐
                     │   REST Controllers   │
                     │  CareerController    │
                     │  HealthController    │
                     └──────────┬───────────┘
                                │
                                ▼
                     ┌──────────────────────┐
                     │    CareerService     │
                     │    Business Logic    │
                     └──────────┬───────────┘
                                │
                                ▼
                     ┌──────────────────────┐
                     │   CareerRepository   │
                     │    Graph Queries     │
                     └──────────┬───────────┘
                                │
                                ▼
                     ┌──────────────────────┐
                     │       CognoDB        │
                     │    Graph Database    │
                     └──────────────────────┘

Project Structure

careergraph-backend/
│
├── data/
│   └── seed.cypher
│
├── screenshots/
│   ├── career-analysis.png
│   ├── ranked-candidates.png
│   └── skill-gap.png
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── careergraph/
│   │   │           ├── CareergraphBackendApplication.java
│   │   │           │
│   │   │           ├── config/
│   │   │           │   └── CognoDBConfig.java
│   │   │           │
│   │   │           ├── controller/
│   │   │           │   ├── CareerController.java
│   │   │           │   └── HealthController.java
│   │   │           │
│   │   │           ├── dto/
│   │   │           │   ├── CandidateResponse.java
│   │   │           │   ├── RecommendedJobsResponse.java
│   │   │           │   ├── SimilarProfessional.java
│   │   │           │   ├── SimilarProfessionalsResponse.java
│   │   │           │   └── SkillGapResponse.java
│   │   │           │
│   │   │           ├── exception/
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   └── ResourceNotFoundException.java
│   │   │           │
│   │   │           ├── repository/
│   │   │           │   └── CareerRepository.java
│   │   │           │
│   │   │           └── service/
│   │   │               └── CareerService.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       │
│   │       └── static/
│   │           ├── app.js
│   │           ├── index.html
│   │           └── style.css
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── careergraph/
│                   ├── CareergraphBackendApplicationTests.java
│                   ├── controller/
│                   │   └── CareerControllerTest.java
│                   └── service/
│                       └── CareerServiceTest.java
│
├── .dockerignore
├── .gitignore
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md

Technology Stack

Backend

Java

Spring Boot

Spring Web

Maven

REST APIs

Database

CognoDB

Graph data model

Cypher queries

Neo4j-compatible Java driver

Frontend

HTML

CSS

JavaScript

Testing

JUnit

Spring Boot Test

Deployment

Docker

Render

Source Control

Git

GitHub

API

The backend exposes REST endpoints for career-related operations.

Feature

Method

Endpoint

Recommended Jobs

GET

/api/persons/{personId}/jobs

Ranked Candidates

GET

/api/jobs/{jobId}/candidates

Skill Gap

GET

/api/persons/{personId}/skill-gap/{jobId}

Similar Professionals

GET

/api/persons/{personId}/similar-professionals

Health Check

GET

/api/health

Health Check

GET /api/health

This endpoint verifies the connection between the Spring Boot application and CognoDB.

A successful response is:

CognoDB connection successful!

Database Configuration

The application reads database configuration through environment variables.

spring.application.name=careergraph-backend

cognodb.uri=${COGNODB_URI}
cognodb.username=${COGNODB_USERNAME}
cognodb.password=${COGNODB_PASSWORD}

server.port=${PORT:8080}

The actual credentials are not stored in the source code.

The following environment variables are required:

COGNODB_URI
COGNODB_USERNAME
COGNODB_PASSWORD

The application also supports the PORT environment variable used by the deployment platform.

Running Locally

1. Clone the repository

git clone https://github.com/Vamshikrishna2525/careergraph-backend.git
cd careergraph-backend

2. Configure environment variables

Set the following variables in your local environment:

COGNODB_URI=<your-cognodb-uri>
COGNODB_USERNAME=<your-cognodb-username>
COGNODB_PASSWORD=<your-cognodb-password>

Do not commit database credentials to GitHub.

3. Load seed data

Run the contents of:

data/seed.cypher

against the CognoDB database.

4. Build the application

On Windows:

.\mvnw.cmd clean package -DskipTests

Or:

.\mvnw.cmd clean package

The generated JAR is located under:

target/careergraph-backend-0.0.1-SNAPSHOT.jar

5. Run the application

java -jar target/careergraph-backend-0.0.1-SNAPSHOT.jar

The application uses port 8080 by default.

Open:

http://localhost:8080

Docker

CareerGraph includes a Dockerfile for containerized deployment.

The Docker image builds the Spring Boot application and runs the generated JAR.

Build

docker build -t careergraph-backend .

Run

docker run -p 8080:8080 \
  -e COGNODB_URI=<your-uri> \
  -e COGNODB_USERNAME=<your-username> \
  -e COGNODB_PASSWORD=<your-password> \
  careergraph-backend

Deployment

The application is deployed as a Docker-based web service.

Deployment Flow

GitHub Repository
       │
       ▼
     Render
       │
       ▼
  Docker Build
       │
       ▼
Spring Boot Application
       │
       ▼
     CognoDB

The application is configured to use the deployment platform's PORT environment variable:

server.port=${PORT:8080}

Live Demo

The deployed CareerGraph application is available at:

https://careergraph-backend-pdiv.onrender.com

The free deployment instance may take some time to respond after a period of inactivity.

GitHub Repository

Source code:

https://github.com/Vamshikrishna2525/careergraph-backend

Sample Graph Relationships

The project includes seed graph data in:

data/seed.cypher

The graph represents relationships between people, skills, jobs, companies, and courses.

Example relationship patterns:

Person -[:HAS_SKILL]-> Skill
Job -[:REQUIRES]-> Skill
Job -[:OFFERED_BY]-> Company
Person -[:WORKED_AT]-> Company
Course -[:TEACHES]-> Skill
Person -[:COMPLETED]-> Course

These relationships form the foundation for career analysis and matching queries.

Important Cypher Operations

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

Testing

The project contains tests for the application, controller layer, and service layer.

Test classes include:

CareergraphBackendApplicationTests
CareerControllerTest
CareerServiceTest

Tests can be executed with:

.\mvnw.cmd test

For a deployment build where tests are intentionally skipped:

.\mvnw.cmd clean package -DskipTests

Key Use Cases

Candidate to Job

Person
   │
   └── HAS_SKILL
          │
          ▼
        Skill
          ▲
          │
       REQUIRES
          │
          Job

This allows the system to determine how well a candidate matches a job.

Skill Gap

Person Skills
     │
     │ compare
     ▼
Required Job Skills
     │
     ├── Matching Skills
     │
     └── Missing Skills

Similar Professionals

Person A
   │
   ├── Skill 1
   ├── Skill 2
   └── Skill 3


Person B
   │
   ├── Skill 1
   ├── Skill 2
   └── Skill 4

The overlapping skills can be used to determine professional similarity.

Security and Configuration

Database credentials are supplied through environment variables rather than being hard-coded into the application.

The following values should never be committed to Git:

COGNODB_URI
COGNODB_USERNAME
COGNODB_PASSWORD

Local configuration should use environment variables or another secure secret-management mechanism.

Future Improvements

Potential future improvements include:

More detailed candidate ranking criteria

Experience-based candidate matching

Education-based matching

Company recommendations

Course recommendations for missing skills

More advanced similarity scoring

Authentication and authorization

Candidate and recruiter dashboards

Pagination for large candidate datasets

Additional graph analytics

Improved visualization of graph relationships

Conclusion

CareerGraph demonstrates how a graph database can be applied to career intelligence problems where relationships between people, skills, jobs, companies, and courses are central to the application.

Instead of treating career information as isolated records, CareerGraph models these entities as connected data and uses those relationships to provide:

Career analysis

Job recommendations

Candidate ranking

Skill-gap analysis

Similar-professional discovery

The project combines Spring Boot, Java, CognoDB, Cypher, JavaScript, Docker, and Render to provide a complete graph-powered career intelligence application.

Author

Vamshi Krishna Yadagiri

GitHub:

https://github.com/Vamshikrishna2525