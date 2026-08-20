package com.careergraph.controller;

import org.neo4j.driver.Driver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final Driver driver;

    public HealthController(Driver driver) {
        this.driver = driver;
    }

    @GetMapping("/api/health")
    public String health() {

        try (var session = driver.session()) {

            var result = session.run("RETURN 'CognoDB connection successful!' AS message");

            return result.single()
                    .get("message")
                    .asString();

        } catch (Exception e) {
            return "CognoDB connection failed: " + e.getMessage();
        }
    }
}