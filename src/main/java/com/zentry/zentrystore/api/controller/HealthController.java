package com.zentry.zentrystore.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String welcome() {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Zentry Store API</title>
                <style>
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }
                    
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        padding: 20px;
                    }
                    
                    .container {
                        background: white;
                        border-radius: 20px;
                        box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                        padding: 40px;
                        max-width: 600px;
                        width: 100%;
                        text-align: center;
                    }
                    
                    .status-badge {
                        display: inline-block;
                        background: #10b981;
                        color: white;
                        padding: 8px 20px;
                        border-radius: 50px;
                        font-size: 14px;
                        font-weight: bold;
                        margin-bottom: 20px;
                        animation: pulse 2s infinite;
                    }
                    
                    @keyframes pulse {
                        0%, 100% { opacity: 1; }
                        50% { opacity: 0.7; }
                    }
                    
                    h1 {
                        color: #1f2937;
                        margin-bottom: 10px;
                        font-size: 2.5em;
                    }
                    
                    .emoji {
                        font-size: 4em;
                        margin-bottom: 20px;
                        animation: bounce 2s infinite;
                    }
                    
                    @keyframes bounce {
                        0%, 100% { transform: translateY(0); }
                        50% { transform: translateY(-10px); }
                    }
                    
                    .version {
                        color: #6b7280;
                        font-size: 14px;
                        margin-bottom: 30px;
                    }
                    
                    .endpoints {
                        background: #f9fafb;
                        border-radius: 10px;
                        padding: 20px;
                        margin-top: 30px;
                        text-align: left;
                    }
                    
                    .endpoints h2 {
                        color: #374151;
                        margin-bottom: 15px;
                        font-size: 1.2em;
                    }
                    
                    .endpoint-list {
                        list-style: none;
                    }
                    
                    .endpoint-list li {
                        padding: 8px 0;
                        border-bottom: 1px solid #e5e7eb;
                        color: #4b5563;
                    }
                    
                    .endpoint-list li:last-child {
                        border-bottom: none;
                    }
                    
                    .endpoint-name {
                        font-weight: bold;
                        color: #667eea;
                    }
                    
                    .timestamp {
                        margin-top: 20px;
                        color: #9ca3af;
                        font-size: 12px;
                    }
                    
                    a {
                        color: #667eea;
                        text-decoration: none;
                    }
                    
                    a:hover {
                        text-decoration: underline;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="status-badge">✓ ONLINE</div>
                    <div class="emoji">🚀</div>
                    <h1>Zentry Store API</h1>
                    <p class="version">Version 1.0.0</p>
                    <p style="color: #6b7280; margin-bottom: 20px;">
                        La API está funcionando correctamente
                    </p>
                    
                    <div class="endpoints">
                        <h2>📍 Endpoints Disponibles</h2>
                        <ul class="endpoint-list">
                            <li><span class="endpoint-name">Auth:</span> /api/auth</li>
                            <li><span class="endpoint-name">Users:</span> /api/users</li>
                            <li><span class="endpoint-name">Publications:</span> /api/publications</li>
                            <li><span class="endpoint-name">Favorites:</span> /api/favorites</li>
                            <li><span class="endpoint-name">Messages:</span> /api/messages</li>
                            <li><span class="endpoint-name">Conversations:</span> /api/conversations</li>
                            <li><span class="endpoint-name">Ratings:</span> /api/ratings</li>
                            <li><span class="endpoint-name">Notifications:</span> /api/notifications</li>
                            <li><span class="endpoint-name">Reports:</span> /api/reports</li>
                        </ul>
                    </div>
                    
                    <p class="timestamp">
                        """ + LocalDateTime.now().toString() + """
                    </p>
                </div>
            </body>
            </html>
            """;
    }

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Zentry Store API is healthy");
        return ResponseEntity.ok(response);
    }
}