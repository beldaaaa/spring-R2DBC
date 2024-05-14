package springframework.springr2dbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import springframework.springr2dbc.model.BeerDTO;
import springframework.springr2dbc.service.BeerService;

@RestController
@RequiredArgsConstructor
public class BeerController {

    private static final String BEER_PATH = "/api/v2/beer";
private final BeerService beerService;
    //one to many => flux
    @GetMapping(BEER_PATH)
    Flux<BeerDTO> beerList() {
        return beerService.beerList();

    }
}
