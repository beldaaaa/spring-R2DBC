package springframework.springr2dbc.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springframework.springr2dbc.repositories.BeerRepository;
import springframework.springr2dbc.domain.Beer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) {
        loadBeerData();
    }

    private void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                Beer beer1 = Beer.builder()
                        .beerName("12")
                        .beerStyle("tocene")
                        .upc("456")
                        .price(new BigDecimal("25.90"))
                        .quantityOnHand(40)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();
                Beer beer2 = Beer.builder()
                        .beerName("10")
                        .beerStyle("tocene")
                        .upc("444")
                        .price(new BigDecimal("12.90"))
                        .quantityOnHand(120)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();
                Beer beer3 = Beer.builder()
                        .beerName("1000")
                        .beerStyle("tocene")
                        .upc("99")
                        .price(new BigDecimal("50.90"))
                        .quantityOnHand(12)
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3)).subscribe();
            }
        });

    }
}

