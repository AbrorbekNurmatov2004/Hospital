package hospital.medflow.dto.ward;

import hospital.medflow.model.enums.WardType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WardDto {
    private String id;
    private WardType type;
    private Integer capacity;
    private Integer occupied;
}
