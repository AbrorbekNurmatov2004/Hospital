package hospital.medflow.service;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.permission.PermissionCreateDto;
import hospital.medflow.dto.permission.PermissionDto;
import hospital.medflow.dto.permission.PermissionUpdateDto;
import hospital.medflow.exception.BadRequestException;
import hospital.medflow.mapper.PermissionMapper;
import hospital.medflow.model.Permission;
import hospital.medflow.repository.PermissionRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.service.base.CRUDService;
import hospital.medflow.utils.ErrorConstants;
import hospital.medflow.validator.PermissionValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService extends AbstractService<PermissionRepository, PermissionMapper, PermissionValidator>
        implements CRUDService<PermissionDto, PermissionUpdateDto, PermissionCreateDto, BaseCriteria, String> {

    public PermissionService(PermissionRepository repository, PermissionMapper mapper, PermissionValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public DataList<List<PermissionDto>> getAll(BaseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Permission> permissions = repository.findAllByCriteria(criteria.getSearch(), pageable);
        List<PermissionDto> dto = mapper.toDto(permissions.getContent());
        return DataList.<List<PermissionDto>>builder()
                .data(dto)
                .totalPages(permissions.getTotalPages())
                .allElements(permissions.getTotalElements())
                .build();
    }

    @Override
    public PermissionDto get(String id) {
        Permission permission = validator.existsAndGet(id);
        return mapper.toDto(permission);
    }

    @Override
    public PermissionDto create(PermissionCreateDto dto) {
        Permission permission = mapper.fromDto(dto);
        Permission saved = repository.save(permission);
        return mapper.toDto(saved);
    }

    @Override
    public PermissionDto update(String id, PermissionUpdateDto dto) {
        Permission permission = validator.existsAndGet(id);
        mapper.fromDto(dto, permission);
        return mapper.toDto(repository.save(permission));
    }

    @Override
    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestException(ErrorConstants.CANT_DELETE_PERMISSION);
        }
    }
}
