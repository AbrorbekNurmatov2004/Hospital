package hospital.medflow.dto.ward;

import hospital.medflow.model.enums.WardType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WardUpdateDto {
    @NotNull
    private WardType type;
    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    private Integer capacity;
}
