package hospital.medflow.service;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.role.RoleCreateDto;
import hospital.medflow.dto.role.RoleDto;
import hospital.medflow.dto.role.RoleUpdateDto;
import hospital.medflow.exception.BadRequestException;
import hospital.medflow.mapper.RoleMapper;
import hospital.medflow.model.Role;
import hospital.medflow.repository.RoleRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.service.base.CRUDService;
import hospital.medflow.utils.ErrorConstants;
import hospital.medflow.validator.RoleValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends AbstractService<RoleRepository, RoleMapper, RoleValidator>
        implements CRUDService<RoleDto, RoleUpdateDto, RoleCreateDto, BaseCriteria, String> {

    public RoleService(RoleRepository repository, RoleMapper mapper, RoleValidator validator) {
        super(repository, mapper, validator);
    }

    public DataList<List<RoleDto>> getAll(BaseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Role> roles = repository.findAllRoles(pageable, criteria.getSearch());
        List<RoleDto> dto = mapper.toDto(roles.getContent());
        return DataList.<List<RoleDto>>builder()
                .data(dto)
                .totalPages(roles.getTotalPages())
                .allElements(roles.getTotalElements())
                .build();
    }

    @Override
    public RoleDto get(String id) {
        Role role = validator.existsAndGet(id);
        return mapper.toDto(role);
    }

    @Override
    public RoleDto create(RoleCreateDto dto) {
        validator.existByCode(dto.getCode());
        Role role = mapper.fromDto(dto);
        return mapper.toDto(repository.save(role));
    }

    @Override
    public RoleDto update(String id, RoleUpdateDto dto) {
        Role role = validator.existsAndGet(id);
        mapper.fromDto(role, dto);
        return mapper.toDto(repository.save(role));
    }

    @Override
    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestException(ErrorConstants.CANT_DELETE_ROLE);
        }
    }
}
