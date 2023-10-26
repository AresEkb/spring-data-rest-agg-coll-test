package com.example.aggregatecollection;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ParentRepositoryTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RepositoryEntityLinks repositoryEntityLinks;

    @BeforeEach
    void setup() {
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void entityIsCreated() {
        String collectionUrl = getCollectionUrlPath(Parent.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String request1 = """
                {
                  "id": 1,
                  "name": "parent1"
                }
                """;

        ResponseEntity<String> response1 = testRestTemplate.postForEntity(
                collectionUrl, new HttpEntity<>(request1, requestHeaders), String.class);
        System.out.println(response1.getBody());
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI entityUrl = response1.getHeaders().getLocation();

        ResponseEntity<String> response2 = testRestTemplate.getForEntity(entityUrl, String.class);
        System.out.println(response2.getBody());
        assertThat(response2.getBody()).isEqualTo(response1.getBody());
    }

    @Test
    void aggregateCollectionIsCreated() {
        String collectionUrl = getCollectionUrlPath(Parent.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String request1 = """
                {
                  "id": 1,
                  "name": "parent1"
                }
                """;

        ResponseEntity<String> response1 = testRestTemplate.postForEntity(
                collectionUrl, new HttpEntity<>(request1, requestHeaders), String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI entityUrl = response1.getHeaders().getLocation();

        String request2 = """
                {
                  "id": 1,
                  "name": "parent1",
                  "children": [
                    {
                      "id": 1,
                      "name": "child1",
                      "parent": "%s"
                    },
                    {
                      "id": 2,
                      "name": "child2",
                      "parent": "%s"
                    }
                  ]
                }
                """.formatted(entityUrl, entityUrl);

        ResponseEntity<String> response2 = testRestTemplate.exchange(
                entityUrl, HttpMethod.PUT, new HttpEntity<>(request2, requestHeaders), String.class);
        System.out.println(response2.getBody());
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> response3 = testRestTemplate.getForEntity(entityUrl, String.class);
        System.out.println(response3.getBody());
        assertThat(response3.getBody()).isEqualTo(response2.getBody());
    }

    @Test
    void itemIsUpdatedInAggregateCollection() {
        String collectionUrl = getCollectionUrlPath(Parent.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String request1 = """
                {
                  "id": 1,
                  "name": "parent1"
                }
                """;

        ResponseEntity<String> response1 = testRestTemplate.postForEntity(
                collectionUrl, new HttpEntity<>(request1, requestHeaders), String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI entityUrl = response1.getHeaders().getLocation();

        String request2 = """
                {
                  "id": 1,
                  "name": "parent1",
                  "children": [
                    {
                      "id": 1,
                      "name": "child1",
                      "parent": "%s"
                    },
                    {
                      "id": 2,
                      "name": "child2",
                      "parent": "%s"
                    }
                  ]
                }
                """.formatted(entityUrl, entityUrl);

        ResponseEntity<String> response2 = testRestTemplate.exchange(
                entityUrl, HttpMethod.PUT, new HttpEntity<>(request2, requestHeaders), String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        String request3 = """
                {
                  "id": 1,
                  "name": "parent1",
                  "children": [
                    {
                      "id": 1,
                      "name": "child1",
                      "parent": "%s"
                    },
                    {
                      "id": 2,
                      "name": "child2a",
                      "parent": "%s"
                    }
                  ]
                }
                """.formatted(entityUrl, entityUrl);

        ResponseEntity<String> response3 = testRestTemplate.exchange(
                entityUrl, HttpMethod.PUT, new HttpEntity<>(request3, requestHeaders), String.class);
        System.out.println(response3.getBody());
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> response4 = testRestTemplate.getForEntity(entityUrl, String.class);
        System.out.println(response4.getBody());
        assertThat(response4.getBody()).isEqualTo(response3.getBody());
    }

    @Test
    void itemIsAddedToAggregateCollection() {
        String collectionUrl = getCollectionUrlPath(Parent.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String request1 = """
                {
                  "id": 1,
                  "name": "parent1"
                }
                """;

        ResponseEntity<String> response1 = testRestTemplate.postForEntity(
                collectionUrl, new HttpEntity<>(request1, requestHeaders), String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI entityUrl = response1.getHeaders().getLocation();

        String request2 = """
                {
                  "id": 1,
                  "name": "parent1",
                  "children": [
                    {
                      "id": 1,
                      "name": "child1",
                      "parent": "%s"
                    }
                  ]
                }
                """.formatted(entityUrl);

        ResponseEntity<String> response2 = testRestTemplate.exchange(
                entityUrl, HttpMethod.PUT, new HttpEntity<>(request2, requestHeaders), String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        String request3 = """
                {
                  "id": 1,
                  "name": "parent1",
                  "children": [
                    {
                      "id": 1,
                      "name": "child1",
                      "parent": "%s"
                    },
                    {
                      "id": 2,
                      "name": "child2",
                      "parent": "%s"
                    }
                  ]
                }
                """.formatted(entityUrl, entityUrl);

        ResponseEntity<String> response3 = testRestTemplate.exchange(
                entityUrl, HttpMethod.PUT, new HttpEntity<>(request3, requestHeaders), String.class);
        System.out.println(response3.getBody());
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> response4 = testRestTemplate.getForEntity(entityUrl, String.class);
        System.out.println(response4.getBody());
        assertThat(response4.getBody()).isEqualTo(response3.getBody());
    }

    @Test
    void itemIsDeletedFromAggregateCollection() {
        String collectionUrl = getCollectionUrlPath(Parent.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String request1 = """
                {
                  "id": 1,
                  "name": "parent1"
                }
                """;

        ResponseEntity<String> response1 = testRestTemplate.postForEntity(
                collectionUrl, new HttpEntity<>(request1, requestHeaders), String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI entityUrl = response1.getHeaders().getLocation();

        String request2 = """
                {
                  "id": 1,
                  "name": "parent1",
                  "children": [
                    {
                      "id": 1,
                      "name": "child1",
                      "parent": "%s"
                    },
                    {
                      "id": 2,
                      "name": "child2",
                      "parent": "%s"
                    }
                  ]
                }
                """.formatted(entityUrl, entityUrl);

        ResponseEntity<String> response2 = testRestTemplate.exchange(
                entityUrl, HttpMethod.PUT, new HttpEntity<>(request2, requestHeaders), String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        String request3 = """
                {
                  "id": 1,
                  "name": "parent1",
                  "children": [
                    {
                      "id": 1,
                      "name": "child1",
                      "parent": "%s"
                    }
                  ]
                }
                """.formatted(entityUrl, entityUrl);

        ResponseEntity<String> response3 = testRestTemplate.exchange(
                entityUrl, HttpMethod.PUT, new HttpEntity<>(request3, requestHeaders), String.class);
        System.out.println(response3.getBody());
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> response4 = testRestTemplate.getForEntity(entityUrl, String.class);
        System.out.println(response4.getBody());
        assertThat(response4.getBody()).isEqualTo(response3.getBody());
    }

    protected String getCollectionUrlPath(Class<?> entityClass) {
        if (RequestContextHolder.getRequestAttributes() == null) {
            var request = new MockHttpServletRequest();
            var requestAttributes = new ServletRequestAttributes(request);
            RequestContextHolder.setRequestAttributes(requestAttributes);
        }
        return repositoryEntityLinks.linkToCollectionResource(entityClass).toUri().getPath();
    }

}
