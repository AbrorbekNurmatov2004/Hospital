package hospital.medflow.dto.login;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
public class TokenDto {
    private String token;
    private Date expiry;
}
