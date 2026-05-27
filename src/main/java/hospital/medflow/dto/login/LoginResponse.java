package hospital.medflow.dto.login;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginResponse {
    private TokenDto accessToken;
    private TokenDto refreshToken;
}
