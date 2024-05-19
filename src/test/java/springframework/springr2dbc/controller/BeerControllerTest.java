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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

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
    @Order(5)
@Test
void deleteBeer(){
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus()
                .isNoContent();
}
    @Order(4)
    @Test
    void updateBeer() {
        webTestClient.put()
                .uri(BeerController.BEER_PATH_ID, 3)
                .body(Mono.just(helperBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }
    @Order(3)
    @Test
    void createBeer() {
        webTestClient.post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(helperBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(LOCALHOST + BeerController.BEER_PATH + "/5");
    }
    @Order(2)
    @Test
    void findById() {
        webTestClient.get()
                .uri(BeerController.BEER_PATH_ID, 2)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(BeerDTO.class);
    }
@Order(1)
    @Test
    void beerList() {
        webTestClient.get()
                .uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }
}