package Restaurant.example.Restaurant.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import Restaurant.example.Restaurant.model.User;
import Restaurant.example.Restaurant.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            if (repo.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("1234"));
                admin.setRole("ROLE_ADMIN");
                repo.save(admin);

                User user = new User();
                user.setUsername("user");
                user.setPassword(encoder.encode("1234"));
                user.setRole("ROLE_USER");
                repo.save(user);

                System.out.println("Örnek kullanıcılar eklendi.");
            }
        };
    }
}
