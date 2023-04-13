package com.example.springdeploy.controller;

import com.example.springdeploy.entites.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {


    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response  =
                testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());

        List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println(laptops.size());
    }

    @Test
    void findOneById() {
        ResponseEntity<Laptop> response  =
                testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "mark": "Acer",
                    "code": "ABC-123",
                    "characteristics": "5Ram",
                    "price": 1250000,
                    "realeaseDate": "0020-06-02",
                    "online": true
                  }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        Laptop result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("Acer", result.getMark());

        System.out.println(result);

    }

    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "mark": "Acer",
                    "code": "ABC-123",
                    "characteristics": "5Ram",
                    "price": 1250000,
                    "realeaseDate": "0020-06-02",
                    "online": true
                  }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);

        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        String jsonUpdate = """
                {
                    "id": 4,
                    "mark": "HP",
                    "code": "ABC-123",
                    "characteristics": "5Ram",
                    "price": 1250000,
                    "realeaseDate": "0020-06-02",
                    "online": true
                  }
                """;

        HttpEntity<String> request2 = new HttpEntity<>(jsonUpdate,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT, request2, Laptop.class);

        Laptop result = response.getBody();

        assertEquals("HP", result.getMark());
        System.out.println(result);
    }

    @Test
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "mark": "Acer",
                    "code": "ABC-123",
                    "characteristics": "5Ram",
                    "price": 1250000,
                    "realeaseDate": "0020-06-02",
                    "online": true
                  }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);

        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops/1", HttpMethod.DELETE, HttpEntity.EMPTY, Laptop.class);
        Laptop result = response.getBody();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<Laptop[]> response2  =
                testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        List<Laptop> laptops = Arrays.asList(response2.getBody());

        System.out.println(laptops);
        System.out.println(result);
    }

    @Test
    void deleteAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "mark": "Acer",
                    "code": "ABC-123",
                    "characteristics": "5Ram",
                    "price": 1250000,
                    "realeaseDate": "0020-06-02",
                    "online": true
                  }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);

        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);


        ResponseEntity<Laptop[]> response = testRestTemplate.exchange("/api/laptops", HttpMethod.DELETE, HttpEntity.EMPTY, Laptop[].class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<Laptop[]> response2  =
                testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        List<Laptop> laptops = Arrays.asList(response2.getBody());

        System.out.println(laptops);

    }
}