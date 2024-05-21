package springframework.springr2dbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.model.CustomerDTO;
import springframework.springr2dbc.service.CustomerService;

import static springframework.springr2dbc.controller.BeerController.LOCALHOST;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    static final String CUSTOMER_PATH = "/api/v2/customer";
    static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";
    private final CustomerService customerService;

    @DeleteMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer customerId) {

        return customerService.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(customerDTO -> customerService.deleteCustomer(customerDTO.getId()))
                .thenReturn(ResponseEntity
                        .noContent()
                        .build());
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> patchCustomer(@PathVariable Integer customerId, @Validated @RequestBody CustomerDTO customerDTO) {

        return customerService.patchCustomer(customerId, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedCustomer -> ResponseEntity
                        .ok()
                        .build());
    }

    @PutMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> updateCustomer(@PathVariable Integer customerId, @Validated @RequestBody CustomerDTO customerDTO) {

        return customerService.updateCustomer(customerId, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedDto -> ResponseEntity
                        .noContent()
                        .build());
    }

    @PostMapping(CUSTOMER_PATH)
    Mono<ResponseEntity<CustomerDTO>> createCustomer(@Validated @RequestBody CustomerDTO customerDTO) {

        return customerService.saveCustomer(customerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl(LOCALHOST + CUSTOMER_PATH + "/" + savedDto
                                        .getId())
                                .build()
                                .toUri())
                        .build());
    }

    @GetMapping(CUSTOMER_PATH_ID)
    Mono<CustomerDTO> getCustomerById(@PathVariable Integer customerId) {

        return customerService.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    //one to many => flux
    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> customerList() {

        return customerService.customerList();

    }
}
