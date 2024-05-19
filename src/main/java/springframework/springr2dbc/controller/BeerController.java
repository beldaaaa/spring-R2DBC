package springframework.springr2dbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.model.BeerDTO;
import springframework.springr2dbc.service.BeerService;

@RestController
@RequiredArgsConstructor
public class BeerController {
    static final String LOCALHOST = "http://localhost:8080";
    static final String BEER_PATH = "/api/v2/beer";
    static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;

    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteBeer(@PathVariable Integer beerId) {

        return beerService.deleteBeer(beerId)
                .thenReturn(ResponseEntity
                        .noContent()
                        .build());
    }

    @PatchMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> patchBeer(@PathVariable Integer beerId, @Validated @RequestBody BeerDTO beerDTO) {

        return beerService.patchBeer(beerId, beerDTO)
                .map(savedBeer -> ResponseEntity
                        .ok()
                        .build());
    }

    @PutMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> updateBeer(@PathVariable Integer beerId, @Validated @RequestBody BeerDTO beerDTO) {

        return beerService.updateBeer(beerId, beerDTO)
                .map(savedDto -> ResponseEntity
                        .noContent()
                        .build());
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<BeerDTO>> createBeer(@Validated @RequestBody BeerDTO beerDTO) {

        return beerService.saveBeer(beerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl(LOCALHOST + BEER_PATH + "/" + savedDto
                                        .getId())
                                .build()
                                .toUri())
                        .build());
    }

    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable Integer beerId) {

        return beerService.getBeerById(beerId);
    }

    //one to many => flux
    @GetMapping(BEER_PATH)
    Flux<BeerDTO> beerList() {

        return beerService.beerList();

    }
}
