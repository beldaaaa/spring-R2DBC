package springframework.springr2dbc.service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.model.BeerDTO;

public interface BeerService {

    Flux<BeerDTO> beerList();

    Mono<BeerDTO> getBeerById(Integer beerId);

    Mono<BeerDTO> saveBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO);
}
