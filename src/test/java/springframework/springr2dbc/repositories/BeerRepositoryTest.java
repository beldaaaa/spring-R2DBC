package springframework.springr2dbc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import springframework.springr2dbc.domain.Beer;

import java.math.BigDecimal;

@DataR2dbcTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    Beer helperBeer() {
        return Beer.builder()
                .beerName("Policka")
                .beerStyle("11")
                .upc("661")
                .price(new BigDecimal("99.9"))
                .quantityOnHand(444)
                .build();
    }

    @Test
    void saveNewBeer() {
        beerRepository.save(helperBeer())
                .subscribe(beer -> System.out.println("Beer saved: " + beer));
    }
}