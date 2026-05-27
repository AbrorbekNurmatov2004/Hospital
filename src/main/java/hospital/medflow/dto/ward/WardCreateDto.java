package hospital.medflow.dto.ward;

import hospital.medflow.model.enums.WardType;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WardCreateDto {
    @NotNull
    private WardType type;
    @NotNull
    @Min(value = 1) @Max(value = 50)
    private Integer capacity;
    @NotNull
    @Min(value = 0)
    private Integer occupied;
}
