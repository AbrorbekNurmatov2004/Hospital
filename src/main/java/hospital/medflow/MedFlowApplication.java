package hospital.medflow;

import hospital.medflow.model.User;
import hospital.medflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MedFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedFlowApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsernameAndDeletedFalse("admin").isEmpty()) {
                User authUser = new User();
                authUser.setSuperAdmin(true);
                authUser.setFirstName("Super");
                authUser.setLastName("Admin");
                authUser.setUsername("admin");
                authUser.setPassword(passwordEncoder.encode("12345678"));
                userRepository.save(authUser);
            }
        };
    }
}
