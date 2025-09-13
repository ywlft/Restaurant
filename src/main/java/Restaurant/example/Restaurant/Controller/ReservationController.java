package Restaurant.example.Restaurant.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Restaurant.example.Restaurant.dto.ReservationDTO;
import Restaurant.example.Restaurant.service.ReservationService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;
	
	   // Tüm rezervasyonları listele
    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAll();
    }

    // ID ile rezervasyon getir
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO dto = reservationService.getById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO saved = reservationService.save(reservationDTO);
            return ResponseEntity.ok(saved);
        } catch (IllegalStateException e) {
            // 400 Bad Request dön ve mesajı kullanıcıya ver
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(Map.of("error", "Sunucu hatası, lütfen tekrar deneyin"));
        }
    }

    // Rezervasyon güncelle
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        reservationDTO.setId(id);
        ReservationDTO updated = reservationService.update(reservationDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Rezervasyon sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}