package Restaurant.example.Restaurant.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Restaurant.example.Restaurant.dto.CustomerDTO;
import Restaurant.example.Restaurant.dto.ReservationDTO;
import Restaurant.example.Restaurant.model.Customer;
import Restaurant.example.Restaurant.model.Reservation;
import Restaurant.example.Restaurant.model.ReservationStatus;
import Restaurant.example.Restaurant.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
//maskeli
    @Transactional
    public ReservationDTO save(ReservationDTO dto) {
        Reservation ent = toEntity(dto);
        Reservation saved = repository.save(ent);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public ReservationDTO getById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ReservationDTO> getAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
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

    //maskesiz
    @Transactional(readOnly = true)
    public ReservationDTO getByIdForAdmin(Long id) {
        return repository.findById(id).map(this::toDtoForAdmin).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ReservationDTO> getAllForAdmin() {
        return repository.findAll().stream().map(this::toDtoForAdmin).collect(Collectors.toList());
    }

    private ReservationDTO toDto(Reservation ent) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(ent.getId());
        dto.setReservationTime(ent.getReservationTime());
        dto.setTable(ent.getTable());
        dto.setStatus(ent.getReservationTime().isBefore(LocalDateTime.now().minusHours(1))
                ? ReservationStatus.COMPLETED
                : ent.getStatus());

        if (ent.getCustomer() != null) {
            Customer cust = ent.getCustomer();
            CustomerDTO custDto = new CustomerDTO();
            custDto.setId(cust.getId());
            custDto.setName(maskName(cust.getName()));
            custDto.setEmail(maskEmail(cust.getEmail()));
            custDto.setPhone(cust.getPhone());
            custDto.setCreatedAt(cust.getCreatedAt());
            custDto.setReservations(null); 
            dto.setCustomer(custDto);
        }

        return dto;
    }

    private ReservationDTO toDtoForAdmin(Reservation ent) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(ent.getId());
        dto.setReservationTime(ent.getReservationTime());
        dto.setTable(ent.getTable());
        dto.setStatus(ent.getStatus());

        if (ent.getCustomer() != null) {
            Customer cust = ent.getCustomer(); 
            CustomerDTO custDto = new CustomerDTO();
            custDto.setId(cust.getId());
            custDto.setName(cust.getName());
            custDto.setEmail(cust.getEmail());
            custDto.setPhone(cust.getPhone());
            custDto.setCreatedAt(cust.getCreatedAt());
            custDto.setReservations(null); 
            dto.setCustomer(custDto);
        }

        return dto;
    }

    private Reservation toEntity(ReservationDTO dto) {
        Reservation ent = new Reservation();
        ent.setId(dto.getId());
        ent.setReservationTime(dto.getReservationTime());
        ent.setTable(dto.getTable());
        ent.setStatus(dto.getStatus());

        if (dto.getCustomer() != null) {
            Customer cust = new Customer();
            cust.setId(dto.getCustomer().getId());
            cust.setName(dto.getCustomer().getName());
            cust.setEmail(dto.getCustomer().getEmail());
            cust.setPhone(dto.getCustomer().getPhone());
            cust.setCreatedAt(dto.getCustomer().getCreatedAt());
            ent.setCustomer(cust);
        }

        return ent;
    }

   //maske
    private String maskName(String name) {
        if (name == null || name.isEmpty()) return "";
        return name.charAt(0) + "*".repeat(Math.max(name.length() - 1, 4));
    }

    private String maskEmail(String email) {
        if (email == null || email.isEmpty()) return "";
        int atIndex = email.indexOf("@");
        if (atIndex <= 1) return "*".repeat(Math.max(email.length(), 4));
        return email.charAt(0) + "*".repeat(atIndex - 1) + email.substring(atIndex);
    }
}
