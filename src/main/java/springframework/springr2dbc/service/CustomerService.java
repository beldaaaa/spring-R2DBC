package springframework.springr2dbc.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.model.CustomerDTO;

public interface CustomerService {

    Flux<CustomerDTO> customerList();

    Mono<CustomerDTO> getCustomerById(Integer customerId);

    Mono<CustomerDTO> saveCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<Void> deleteCustomer(Integer customerId);
}
