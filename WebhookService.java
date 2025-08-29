package com.example.bajaj.service;

import com.example.bajaj.model.WebhookResponse;
import com.example.bajaj.model.SubmissionRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService implements CommandLineRunner {

    private static final String INIT_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bajaj SQL Challenge -Question 2");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");       
        requestBody.put("regNo", "22BAI1456");     
        requestBody.put("email", "john@example.com"); 

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<WebhookResponse> response =
                restTemplate.exchange(INIT_URL, HttpMethod.POST, entity, WebhookResponse.class);

        WebhookResponse webhookResponse = response.getBody();

        if (webhookResponse == null) {
            System.err.println("Failed to get webhook response.");
            return;
        }

        System.out.println("Webhook Registered. Got URL & Token.");

        String webhookUrl = webhookResponse.getWebhookUrl();
        String accessToken = webhookResponse.getAccessToken();

        String finalQuery =
            "SELECT e1.emp_id, e1.first_name, e1.last_name, d.department_name, " +
            "COUNT(e2.emp_id) AS younger_employees_count " +
            "FROM employee e1 " +
            "JOIN department d ON e1.department = d.department_id " +
            "LEFT JOIN employee e2 " +
            "ON e1.department = e2.department AND e2.dob > e1.dob " +
            "GROUP BY e1.emp_id, e1.first_name, e1.last_name, d.department_name " +
            "ORDER BY e1.emp_id DESC;";

        SubmissionRequest submission = new SubmissionRequest(finalQuery);

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setContentType(MediaType.APPLICATION_JSON);
        authHeaders.setBearerAuth(accessToken);

        HttpEntity<SubmissionRequest> submissionEntity = new HttpEntity<>(submission, authHeaders);

        ResponseEntity<String> submissionResponse =
                restTemplate.exchange(webhookUrl, HttpMethod.POST, submissionEntity, String.class);

        System.out.println("Submission Response: " + submissionResponse.getBody());
    }
}
