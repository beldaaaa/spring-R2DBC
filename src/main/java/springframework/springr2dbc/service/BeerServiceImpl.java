package springframework.springr2dbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springframework.springr2dbc.mapper.BeerMapper;
import springframework.springr2dbc.model.BeerDTO;
import springframework.springr2dbc.repository.BeerRepository;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

private final BeerRepository repository;
private final BeerMapper mapper;

    @Override
    public Flux<BeerDTO> beerList() {
        return repository.findAll()
                .map(mapper::beerToBeerDto);
    }
}
