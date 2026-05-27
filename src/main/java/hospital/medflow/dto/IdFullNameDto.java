package hospital.medflow.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdFullNameDto {
    private String id;
    private String firstName;
    private String lastName;
}
