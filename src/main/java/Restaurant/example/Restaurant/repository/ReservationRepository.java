package Restaurant.example.Restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Restaurant.example.Restaurant.model.Reservation;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	
}
