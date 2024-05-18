package springframework.springr2dbc.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import springframework.springr2dbc.domain.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {
}
