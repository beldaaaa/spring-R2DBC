package springframework.springr2dbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.springr2dbc.mapper.CustomerMapper;
import springframework.springr2dbc.model.CustomerDTO;
import springframework.springr2dbc.repository.CustomerRepository;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public Flux<CustomerDTO> customerList() {

        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(Integer customerId) {

        return customerRepository.findById(customerId)
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> saveCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer -> {
                    foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    return foundCustomer;
                })
                .flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer -> {
                    if (StringUtils.hasText(customerDTO.getCustomerName())) {
                        foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return foundCustomer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<Void> deleteCustomer(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }
}
