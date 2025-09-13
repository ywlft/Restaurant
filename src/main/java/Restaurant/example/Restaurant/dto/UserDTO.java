package Restaurant.example.Restaurant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private List<String> roles;
    public UserDTO(Long id, String username, List<String> roles) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı adı null veya boş olamaz");
        }
        this.id = id;
        this.username = username;
        this.roles = roles != null ? roles : Collections.emptyList();
    }
}