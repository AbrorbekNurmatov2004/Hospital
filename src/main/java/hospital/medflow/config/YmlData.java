package hospital.medflow.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class YmlData {

    @Value("${application.access-token:60000}")
    private Long accessToken;

    @Value("${application.refresh-token:86400000}")
    private Long refreshToken;

    @Value("${application.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${application.max-file-size:5242880}")
    private long maxFileSize;

    @Value("${application.file-source:user-data/images}")
    private String fileRoot;

}
