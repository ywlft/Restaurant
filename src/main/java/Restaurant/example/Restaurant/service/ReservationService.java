package Restaurant.example.Restaurant.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Restaurant.example.Restaurant.dto.ReservationDTO;
import Restaurant.example.Restaurant.model.Reservation;
import Restaurant.example.Restaurant.model.ReservationStatus;
import Restaurant.example.Restaurant.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;

    @Transactional
    public ReservationDTO save(ReservationDTO dto) {
        Long tableId = dto.getTable().getId();


        Reservation savedEntity = repository.save(toEntity(dto));
        return toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public ReservationDTO getById(Long id) {
        return repository.findById(id).map(ReservationService::toDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ReservationDTO> getAll() {
        return repository.findAll().stream()
                .map(ReservationService::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDTO update(ReservationDTO dto) {
        Reservation updated = repository.save(toEntity(dto));
        return toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Entity → DTO
    private static ReservationDTO toDto(Reservation ent) {
    	  ReservationDTO dto = new ReservationDTO();
    	    dto.setId(ent.getId());
    	    dto.setCustomer(ent.getCustomer());
    	    dto.setReservationTime(ent.getReservationTime());
    	    dto.setTable(ent.getTable());
    	    

    	    // Eğer rezervasyon zamanı şu anı geçtiyse, status COMPLETED olsun
    	    if (ent.getReservationTime().isBefore(LocalDateTime.now().minusHours(1))) {
    	        dto.setStatus(ReservationStatus.COMPLETED);
    	        ent.setStatus(ReservationStatus.İptal);
    	        
    	    } else {
    	        dto.setStatus(ent.getStatus());
    	    }

    	    return dto;
    }

    // DTO → Entity
    private static Reservation toEntity(ReservationDTO dto) {
    	  Reservation ent = new Reservation();
    	    ent.setId(dto.getId());
    	    ent.setCustomer(dto.getCustomer());
    	    ent.setReservationTime(dto.getReservationTime());
    	    ent.setTable(dto.getTable());

    	    // Eğer rezervasyon zamanı şu anı geçtiyse, status COMPLETED olsun
    	    if (dto.getReservationTime().isBefore(LocalDateTime.now().minusHours(1))) {
    	        ent.setStatus(ReservationStatus.COMPLETED);
    	    } else {
    	        ent.setStatus(dto.getStatus());
    	    }

    	    return ent;
    }
}