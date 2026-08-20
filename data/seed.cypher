// ===============================
// CAREERGRAPH - SEED DATA
// ===============================

// Clear existing data
MATCH (n)
DETACH DELETE n;


// ===============================
// SKILLS
// ===============================

CREATE
(:Skill {id: 'S001', name: 'Java', category: 'Backend'}),
(:Skill {id: 'S002', name: 'Spring Boot', category: 'Backend'}),
(:Skill {id: 'S003', name: 'React', category: 'Frontend'}),
(:Skill {id: 'S004', name: 'JavaScript', category: 'Frontend'}),
(:Skill {id: 'S005', name: 'SQL', category: 'Database'}),
(:Skill {id: 'S006', name: 'Python', category: 'Backend'}),
(:Skill {id: 'S007', name: 'HTML', category: 'Frontend'}),
(:Skill {id: 'S008', name: 'CSS', category: 'Frontend'}),
(:Skill {id: 'S009', name: 'Git', category: 'Tools'}),
(:Skill {id: 'S010', name: 'REST API', category: 'Backend'});


// ===============================
// COMPANIES
// ===============================

CREATE
(:Company {
    id: 'C001',
    name: 'TechNova Solutions',
    location: 'Hyderabad'
}),

(:Company {
    id: 'C002',
    name: 'Infosys',
    location: 'Hyderabad'
}),

(:Company {
    id: 'C003',
    name: 'Wipro',
    location: 'Bangalore'
}),

(:Company {
    id: 'C004',
    name: 'TCS',
    location: 'Hyderabad'
}),

(:Company {
    id: 'C005',
    name: 'NextGen Technologies',
    location: 'Pune'
});


// ===============================
// PEOPLE
// ===============================

CREATE
(:Person {
    id: 'P001',
    name: 'Rahul Sharma',
    email: 'rahul@example.com',
    location: 'Hyderabad'
}),

(:Person {
    id: 'P002',
    name: 'Priya Reddy',
    email: 'priya@example.com',
    location: 'Hyderabad'
}),

(:Person {
    id: 'P003',
    name: 'Arjun Kumar',
    email: 'arjun@example.com',
    location: 'Bangalore'
}),

(:Person {
    id: 'P004',
    name: 'Sneha Patel',
    email: 'sneha@example.com',
    location: 'Pune'
}),

(:Person {
    id: 'P005',
    name: 'Kiran Rao',
    email: 'kiran@example.com',
    location: 'Hyderabad'
}),

(:Person {
    id: 'P006',
    name: 'Ananya Singh',
    email: 'ananya@example.com',
    location: 'Bangalore'
});


// ===============================
// JOBS
// ===============================

CREATE
(:Job {
    id: 'J001',
    title: 'Java Developer',
    experience: '0-2 years',
    location: 'Hyderabad'
}),

(:Job {
    id: 'J002',
    title: 'Spring Boot Developer',
    experience: '1-3 years',
    location: 'Hyderabad'
}),

(:Job {
    id: 'J003',
    title: 'Full Stack Developer',
    experience: '0-2 years',
    location: 'Bangalore'
}),

(:Job {
    id: 'J004',
    title: 'Backend Developer',
    experience: '1-3 years',
    location: 'Pune'
}),

(:Job {
    id: 'J005',
    title: 'Software Engineer',
    experience: '0-2 years',
    location: 'Hyderabad'
});


// ===============================
// COURSES
// ===============================

CREATE
(:Course {
    id: 'CR001',
    name: 'Java Programming',
    platform: 'Udemy'
}),

(:Course {
    id: 'CR002',
    name: 'Spring Boot Masterclass',
    platform: 'Coursera'
}),

(:Course {
    id: 'CR003',
    name: 'React Fundamentals',
    platform: 'Udemy'
}),

(:Course {
    id: 'CR004',
    name: 'SQL Essentials',
    platform: 'Coursera'
}),

(:Course {
    id: 'CR005',
    name: 'Python for Developers',
    platform: 'edX'
});


// ===============================
// PERSON -> SKILL
// ===============================

MATCH
(p1:Person {id: 'P001'}),
(p2:Person {id: 'P002'}),
(p3:Person {id: 'P003'}),
(p4:Person {id: 'P004'}),
(p5:Person {id: 'P005'}),
(p6:Person {id: 'P006'}),
(java:Skill {id: 'S001'}),
(spring:Skill {id: 'S002'}),
(react:Skill {id: 'S003'}),
(js:Skill {id: 'S004'}),
(sql:Skill {id: 'S005'}),
(python:Skill {id: 'S006'}),
(html:Skill {id: 'S007'}),
(css:Skill {id: 'S008'}),
(git:Skill {id: 'S009'}),
(api:Skill {id: 'S010'})

CREATE
(p1)-[:HAS_SKILL]->(java),
(p1)-[:HAS_SKILL]->(spring),
(p1)-[:HAS_SKILL]->(sql),
(p1)-[:HAS_SKILL]->(git),

(p2)-[:HAS_SKILL]->(java),
(p2)-[:HAS_SKILL]->(react),
(p2)-[:HAS_SKILL]->(js),
(p2)-[:HAS_SKILL]->(html),
(p2)-[:HAS_SKILL]->(css),

(p3)-[:HAS_SKILL]->(python),
(p3)-[:HAS_SKILL]->(sql),
(p3)-[:HAS_SKILL]->(git),
(p3)-[:HAS_SKILL]->(api),

(p4)-[:HAS_SKILL]->(java),
(p4)-[:HAS_SKILL]->(spring),
(p4)-[:HAS_SKILL]->(react),
(p4)-[:HAS_SKILL]->(api),

(p5)-[:HAS_SKILL]->(java),
(p5)-[:HAS_SKILL]->(spring),
(p5)-[:HAS_SKILL]->(sql),
(p5)-[:HAS_SKILL]->(api),

(p6)-[:HAS_SKILL]->(python),
(p6)-[:HAS_SKILL]->(react),
(p6)-[:HAS_SKILL]->(js),
(p6)-[:HAS_SKILL]->(git);


// ===============================
// JOB -> SKILL
// ===============================

MATCH
(j1:Job {id: 'J001'}),
(j2:Job {id: 'J002'}),
(j3:Job {id: 'J003'}),
(j4:Job {id: 'J004'}),
(j5:Job {id: 'J005'}),
(java:Skill {id: 'S001'}),
(spring:Skill {id: 'S002'}),
(react:Skill {id: 'S003'}),
(js:Skill {id: 'S004'}),
(sql:Skill {id: 'S005'}),
(python:Skill {id: 'S006'}),
(html:Skill {id: 'S007'}),
(git:Skill {id: 'S009'}),
(api:Skill {id: 'S010'})

CREATE
(j1)-[:REQUIRES]->(java),
(j1)-[:REQUIRES]->(sql),
(j1)-[:REQUIRES]->(git),

(j2)-[:REQUIRES]->(java),
(j2)-[:REQUIRES]->(spring),
(j2)-[:REQUIRES]->(sql),
(j2)-[:REQUIRES]->(api),

(j3)-[:REQUIRES]->(java),
(j3)-[:REQUIRES]->(react),
(j3)-[:REQUIRES]->(js),
(j3)-[:REQUIRES]->(html),

(j4)-[:REQUIRES]->(java),
(j4)-[:REQUIRES]->(spring),
(j4)-[:REQUIRES]->(api),

(j5)-[:REQUIRES]->(java),
(j5)-[:REQUIRES]->(python),
(j5)-[:REQUIRES]->(sql),
(j5)-[:REQUIRES]->(git);


// ===============================
// JOB -> COMPANY
// ===============================

MATCH
(j1:Job {id: 'J001'}),
(j2:Job {id: 'J002'}),
(j3:Job {id: 'J003'}),
(j4:Job {id: 'J004'}),
(j5:Job {id: 'J005'}),
(c1:Company {id: 'C001'}),
(c2:Company {id: 'C002'}),
(c3:Company {id: 'C003'}),
(c4:Company {id: 'C004'}),
(c5:Company {id: 'C005'})

CREATE
(j1)-[:OFFERED_BY]->(c2),
(j2)-[:OFFERED_BY]->(c1),
(j3)-[:OFFERED_BY]->(c3),
(j4)-[:OFFERED_BY]->(c5),
(j5)-[:OFFERED_BY]->(c4);


// ===============================
// PERSON -> COMPANY
// ===============================

MATCH
(p1:Person {id: 'P001'}),
(p2:Person {id: 'P002'}),
(p3:Person {id: 'P003'}),
(p4:Person {id: 'P004'}),
(p5:Person {id: 'P005'}),
(p6:Person {id: 'P006'}),
(c1:Company {id: 'C001'}),
(c2:Company {id: 'C002'}),
(c3:Company {id: 'C003'}),
(c4:Company {id: 'C004'})

CREATE
(p1)-[:WORKED_AT]->(c1),
(p2)-[:WORKED_AT]->(c2),
(p3)-[:WORKED_AT]->(c3),
(p4)-[:WORKED_AT]->(c4),
(p5)-[:WORKED_AT]->(c1),
(p6)-[:WORKED_AT]->(c3);


// ===============================
// COURSE -> SKILL
// ===============================

MATCH
(cr1:Course {id: 'CR001'}),
(cr2:Course {id: 'CR002'}),
(cr3:Course {id: 'CR003'}),
(cr4:Course {id: 'CR004'}),
(cr5:Course {id: 'CR005'}),
(java:Skill {id: 'S001'}),
(spring:Skill {id: 'S002'}),
(react:Skill {id: 'S003'}),
(sql:Skill {id: 'S005'}),
(python:Skill {id: 'S006'})

CREATE
(cr1)-[:TEACHES]->(java),
(cr2)-[:TEACHES]->(spring),
(cr3)-[:TEACHES]->(react),
(cr4)-[:TEACHES]->(sql),
(cr5)-[:TEACHES]->(python);


// ===============================
// PERSON -> COURSE
// ===============================

MATCH
(p1:Person {id: 'P001'}),
(p2:Person {id: 'P002'}),
(p3:Person {id: 'P003'}),
(p4:Person {id: 'P004'}),
(cr1:Course {id: 'CR001'}),
(cr2:Course {id: 'CR002'}),
(cr3:Course {id: 'CR003'}),
(cr4:Course {id: 'CR004'}),
(cr5:Course {id: 'CR005'})

CREATE
(p1)-[:COMPLETED]->(cr1),
(p1)-[:COMPLETED]->(cr2),
(p2)-[:COMPLETED]->(cr3),
(p3)-[:COMPLETED]->(cr4),
(p3)-[:COMPLETED]->(cr5),
(p4)-[:COMPLETED]->(cr1),
(p4)-[:COMPLETED]->(cr3);
