package hospital.medflow.mapper;

import hospital.medflow.dto.ward.WardCreateDto;
import hospital.medflow.dto.ward.WardDto;
import hospital.medflow.dto.ward.WardUpdateDto;
import hospital.medflow.model.Ward;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class WardMapper {

    public List<WardDto> toDto(List<Ward> wards) {
        if (wards == null) {
            return Collections.emptyList();
        }
        return wards.stream().map(this::toDto).toList();
    }

    public WardDto toDto(Ward wards) {
        return WardDto.builder().
                id(wards.getId()).
                type(wards.getType()).
                capacity(wards.getCapacity()).
                occupied(wards.getOccupied()).build();
    }

    public Ward fromDto(WardCreateDto dto) {
        Ward ward = new Ward();
        ward.setType(dto.getType());
        ward.setCapacity(dto.getCapacity());
        ward.setOccupied(dto.getOccupied());
        return ward;
    }

    public void fromDto(WardUpdateDto dto, Ward ward) {
        ward.setType(dto.getType());
        ward.setCapacity(dto.getCapacity());
    }

}