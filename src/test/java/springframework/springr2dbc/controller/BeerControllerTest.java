package springframework.springr2dbc.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.domain.Beer;
import springframework.springr2dbc.model.BeerDTO;

import java.math.BigDecimal;

import static springframework.springr2dbc.controller.BeerController.LOCALHOST;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {
    int falseId = 128;
    @Autowired
    WebTestClient webTestClient;

    Beer helperBeer() {
        return Beer.builder()
                .beerName("Svijany")
                .beerStyle("10")
                .upc("1")
                .price(new BigDecimal("99.9"))
                .quantityOnHand(1)
                .build();
    }

    @Test
    void deleteBeerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID, falseId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Order(6)
    @Test
    void deleteBeer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void patchIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(BeerController.BEER_PATH_ID, falseId)
                .body(Mono.just(helperBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateBeerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, falseId)
                .body(Mono.just(helperBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateBeerBadRequest() {
        Beer wrongBeer = helperBeer();
        wrongBeer.setBeerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 3)
                .body(Mono.just(wrongBeer), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(5)
    @Test
    void updateBeer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID, 3)
                .body(Mono.just(helperBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void createWrongDataBeer() {
        Beer wrongBeer = helperBeer();
        wrongBeer.setBeerName("?");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(wrongBeer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Order(3)
    @Test
    void createBeer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(helperBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(LOCALHOST + BeerController.BEER_PATH + "/4");
    }

    @Test
    void findByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH_ID, falseId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Order(2)
    @Test
    void findById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH_ID, 2)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Order(1)
    @Test
    void beerList() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }
}