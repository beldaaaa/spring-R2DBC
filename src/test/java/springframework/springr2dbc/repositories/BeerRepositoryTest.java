package springframework.springr2dbc.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import springframework.springr2dbc.config.DatabaseConfig;
import springframework.springr2dbc.domain.Beer;
import springframework.springr2dbc.repository.BeerRepository;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void createJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(helperBeer()));
    }

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