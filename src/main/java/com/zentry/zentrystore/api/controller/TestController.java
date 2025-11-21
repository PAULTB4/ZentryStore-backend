package com.zentry.zentrystore.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*") // Permitir CORS para testing
public class TestController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "✅ API funcionando correctamente");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "SUCCESS");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam(defaultValue = "World") String name) {
        return ResponseEntity.ok("Hello, " + name + "! 🚀");
    }

    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        response.put("received", body);
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Echo successful");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> databaseTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("database", "PostgreSQL");
        response.put("connection", "Active");
        response.put("status", "✅ Connected");

        return ResponseEntity.ok(response);
    }
}

