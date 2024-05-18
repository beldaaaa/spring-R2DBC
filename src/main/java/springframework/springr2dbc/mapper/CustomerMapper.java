package springframework.springr2dbc.mapper;

import org.mapstruct.Mapper;
import springframework.springr2dbc.domain.Customer;
import springframework.springr2dbc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
