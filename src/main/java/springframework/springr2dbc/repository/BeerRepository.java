package springframework.springr2dbc.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import springframework.springr2dbc.domain.Beer;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
