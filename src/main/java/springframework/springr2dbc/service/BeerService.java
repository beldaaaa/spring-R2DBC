package springframework.springr2dbc.service;


import reactor.core.publisher.Flux;
import springframework.springr2dbc.model.BeerDTO;

public interface BeerService {

    Flux<BeerDTO> beerList();
}
