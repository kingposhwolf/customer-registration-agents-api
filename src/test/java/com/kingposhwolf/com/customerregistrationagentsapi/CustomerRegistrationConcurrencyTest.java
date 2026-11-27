package com.kingposhwolf.com.customerregistrationagentsapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingposhwolf.com.customerregistrationagentsapi.repositories.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "JWT_SECRETE=dGVzdC1vbmx5LXNlY3JldC1rZXktZm9yLWludGVncmF0aW9uLXRlc3RzLTEyMzQ=",
                "spring.jpa.show-sql=false"
        })
class CustomerRegistrationConcurrencyTest {

    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    static {
        POSTGRES.start();
    }

    private static final String NIDA = "19940721333330000344";

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void tenConcurrentRetriesCreateExactlyOneCustomer() throws Exception {
        HttpEntity<Map<String, Object>> request = registration(login());

        int attempts = 10;
        ExecutorService pool = Executors.newFixedThreadPool(attempts);
        CountDownLatch gate = new CountDownLatch(1);
        List<Future<Integer>> calls = new ArrayList<>();

        for (int i = 0; i < attempts; i++) {
            calls.add(pool.submit(() -> {
                gate.await();
                return rest.postForEntity("/api/v1/customers", request, String.class).getStatusCode().value();
            }));
        }
        gate.countDown();

        List<Integer> statuses = new ArrayList<>();
        for (Future<Integer> call : calls) {
            statuses.add(call.get(30, TimeUnit.SECONDS));
        }
        pool.shutdown();

        assertThat(statuses).filteredOn(s -> s == 201).hasSize(1);
        assertThat(statuses).filteredOn(s -> s != 201).allMatch(s -> s == 409);
        assertThat(customerRepository.findAll())
                .filteredOn(c -> NIDA.equals(c.getNida()))
                .hasSize(1);
    }

    private String login() {
        JsonNode body = rest.postForEntity("/api/v1/auth/login",
                Map.of("phoneNumber", "+255692428600", "password", "password"), JsonNode.class).getBody();
        return body.path("data").path("token").asText();
    }

    private HttpEntity<Map<String, Object>> registration(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = Map.of("name", "Neema Mushi", "dob", "1994-07-21", "wardId", 1, "nida", NIDA);
        return new HttpEntity<>(body, headers);
    }
}
