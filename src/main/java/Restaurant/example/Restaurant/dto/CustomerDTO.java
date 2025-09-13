package Restaurant.example.Restaurant.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	 private Long id;
	    private String name;
	    private String email;
	    @Column(unique = true)
	    private String phone;
	    private List<ReservationDTO> reservations;
	    private LocalDateTime createdAt; 
}
