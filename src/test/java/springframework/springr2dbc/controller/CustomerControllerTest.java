package springframework.springr2dbc.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.domain.Customer;
import springframework.springr2dbc.model.CustomerDTO;

import static springframework.springr2dbc.controller.BeerController.LOCALHOST;
import static springframework.springr2dbc.controller.CustomerController.CUSTOMER_PATH;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {
    int falseId = 128;
    @Autowired
    WebTestClient webTestClient;

    Customer helperCustomer() {
        return Customer.builder()
                .customerName("Nameless")
                .build();
    }

    @Test
    void deleteCustomerNotFound() {
        webTestClient.delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, falseId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(6)
    void deleteCustomer() {
        webTestClient.delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void patchCustomerNotFound() {
        webTestClient.patch()
                .uri(CustomerController.CUSTOMER_PATH_ID, falseId)
                .body(Mono.just(helperCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateCustomerNotFound() {
        webTestClient.put()
                .uri(CustomerController.CUSTOMER_PATH_ID, falseId)
                .body(Mono.just(helperCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateCustomerBadRequest() {
        Customer wrongCustomer = helperCustomer();
        wrongCustomer.setCustomerName("");

        webTestClient.put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 2)
                .body(Mono.just(wrongCustomer), CustomerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(5)
    void updateCustomer() {
        webTestClient.put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 3)
                .body(Mono.just(helperCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Order(3)
    @Test
    void createCustomer() {

        webTestClient.post()
                .uri(CUSTOMER_PATH)
                .body(Mono.just(helperCustomer()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(LOCALHOST + CUSTOMER_PATH + "/4");
    }

    @Test
    @Order(2)
    void findById() {
        webTestClient.get()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(1)
    void customerList() {
        webTestClient.get()
                .uri(CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }
}