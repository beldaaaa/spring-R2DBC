package springframework.springr2dbc.mapper;

import org.mapstruct.Mapper;
import springframework.springr2dbc.domain.Beer;
import springframework.springr2dbc.model.BeerDTO;

@Mapper
public interface BeerMapper {

Beer beerDtoToBeer(BeerDTO beerDTO);
BeerDTO beerToBeerDTO(Beer beer);
}
