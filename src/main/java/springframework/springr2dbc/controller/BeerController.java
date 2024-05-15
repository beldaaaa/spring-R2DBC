package springframework.springr2dbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.model.BeerDTO;
import springframework.springr2dbc.service.BeerService;

@RestController
@RequiredArgsConstructor
public class BeerController {
    private static final String LOCALHOST = "http://localhost:8080";
    private static final String BEER_PATH = "/api/v2/beer";
    private static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;

    @PutMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> updateBeer(@PathVariable Integer beerId, @RequestBody BeerDTO beerDTO) {
        beerService.updateBeer(beerId,beerDTO).subscribe();

        return Mono.just(ResponseEntity.ok().build());
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<BeerDTO>> createBeer(@RequestBody BeerDTO beerDTO) {
        return beerService.saveBeer(beerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl(LOCALHOST + BEER_PATH + "/" + savedDto.getId()).build().toUri()).build());
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
