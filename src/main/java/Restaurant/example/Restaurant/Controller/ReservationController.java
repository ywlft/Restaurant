package Restaurant.example.Restaurant.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import Restaurant.example.Restaurant.dto.ReservationDTO;
import Restaurant.example.Restaurant.service.ReservationService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

 
    @GetMapping("/reservation")
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAll();
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO dto = reservationService.getById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> addReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO saved = reservationService.save(reservationDTO);
            return ResponseEntity.status(201).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        reservationDTO.setId(id);
        try {
            ReservationDTO updated = reservationService.update(reservationDTO);
            if (updated != null) return ResponseEntity.ok(updated);
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", "Rezervasyon bulunamadı"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/reservation")
    public List<ReservationDTO> getAllReservationsForAdmin() {
        return reservationService.getAllForAdmin();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/reservation/{id}")
    public ResponseEntity<ReservationDTO> getReservationByIdForAdmin(@PathVariable Long id) {
        ReservationDTO dto = reservationService.getByIdForAdmin(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/reservation")
    public ResponseEntity<?> addReservationForAdmin(@RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO saved = reservationService.save(reservationDTO);
            return ResponseEntity.status(201).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/reservation/{id}")
    public ResponseEntity<?> updateReservationForAdmin(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        reservationDTO.setId(id);
        try {
            ReservationDTO updated = reservationService.update(reservationDTO);
            if (updated != null) return ResponseEntity.ok(updated);
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/reservation/{id}")
    public ResponseEntity<?> deleteReservationForAdmin(@PathVariable Long id) {
        try {
            reservationService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", "Rezervasyon bulunamadı"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
