package Restaurant.example.Restaurant.dto;

import java.time.LocalDateTime;

import Restaurant.example.Restaurant.model.Customer;
import Restaurant.example.Restaurant.model.ReservationStatus;
import Restaurant.example.Restaurant.model.TableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
	private Long id;
    private Customer customer;
    private TableEntity table;
    private LocalDateTime reservationTime;
    private ReservationStatus status;
}
