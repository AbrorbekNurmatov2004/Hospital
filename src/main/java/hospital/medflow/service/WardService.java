package hospital.medflow.service;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.ward.WardCreateDto;
import hospital.medflow.dto.ward.WardDto;
import hospital.medflow.dto.ward.WardUpdateDto;
import hospital.medflow.mapper.WardMapper;
import hospital.medflow.model.Ward;
import hospital.medflow.repository.WardRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.service.base.CRUDService;
import hospital.medflow.validator.WardValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WardService extends AbstractService<WardRepository, WardMapper, WardValidator>
        implements CRUDService<WardDto, WardUpdateDto, WardCreateDto, BaseCriteria, String> {

    public WardService(WardRepository repository, WardMapper mapper, WardValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public DataList<List<WardDto>> getAll(BaseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Ward> wards = repository.findAllWards(pageable);
        List<WardDto> dto = mapper.toDto(wards.getContent());
        return DataList.<List<WardDto>>builder()
                .data(dto)
                .totalPages(wards.getTotalPages())
                .allElements(wards.getTotalElements())
                .build();
    }

    @Override
    public WardDto get(String id) {
        Ward ward = validator.existAndGet(id);
        return mapper.toDto(ward);
    }

    @Override
    public WardDto create(WardCreateDto dto) {
        Ward ward = mapper.fromDto(dto);
        Ward savedWard = repository.save(ward);
        return mapper.toDto(savedWard);
    }

    @Override
    public WardDto update(String id, WardUpdateDto dto) {
        Ward ward = validator.existAndGet(id);
        mapper.fromDto(dto, ward);
        return mapper.toDto(repository.save(ward));
    }

    @Override
    public void delete(String id) {
        Ward ward = validator.existAndGet(id);
        ward.setDeleted(true);
        repository.save(ward);
    }
}
