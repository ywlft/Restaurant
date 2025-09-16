package Restaurant.example.Restaurant.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Restaurant.example.Restaurant.dto.CustomerDTO;
import Restaurant.example.Restaurant.dto.ReservationDTO;
import Restaurant.example.Restaurant.model.Customer;
import Restaurant.example.Restaurant.model.Reservation;
import Restaurant.example.Restaurant.model.ReservationStatus;
import Restaurant.example.Restaurant.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    // React maskelii
    public CustomerDTO save(CustomerDTO dto) {
        List<Customer> existingList = repository.findByPhone(dto.getPhone());
        if (!existingList.isEmpty()) {
            return toDto(existingList.get(0));
        }
        Customer ent = toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Customer saved = repository.save(ent);
        return toDto(saved);
    }

    public CustomerDTO getById(Long id) {
        Customer ent = repository.findById(id).orElse(null);
        return ent != null ? toDto(ent) : null;
    }

    public List<CustomerDTO> getAll() {
        List<Customer> customers = repository.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : customers) {
            dtos.add(toDto(customer));
        }
        return dtos;
    }

    public CustomerDTO update(CustomerDTO dto) {
        Customer ent = repository.save(toEntity(dto));
        return toDto(ent);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private CustomerDTO toDto(Customer ent) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(ent.getId());
        dto.setName(maskName(ent.getName()));
        dto.setEmail(maskEmail(ent.getEmail()));
        dto.setPhone(ent.getPhone());
        dto.setCreatedAt(ent.getCreatedAt());

        if (ent.getReservations() != null) {
            List<ReservationDTO> resDtos = new ArrayList<>();
            for (Reservation res : ent.getReservations()) {
                ReservationDTO resDto = new ReservationDTO();
                resDto.setId(res.getId());
                resDto.setReservationTime(res.getReservationTime());
                resDto.setStatus(res.getReservationTime().isBefore(LocalDateTime.now().minusHours(1))
                        ? ReservationStatus.COMPLETED
                        : res.getStatus());
                resDto.setTable(res.getTable());
                resDto.setCustomer(null);
                resDtos.add(resDto);
            }
            dto.setReservations(resDtos);
        }
        return dto;
    }

    private Customer toEntity(CustomerDTO dto) {
        Customer ent = new Customer();
        ent.setId(dto.getId());
        ent.setName(dto.getName());
        ent.setEmail(dto.getEmail());
        ent.setPhone(dto.getPhone());
        ent.setCreatedAt(LocalDateTime.now());
        return ent;
    }

    //adminiçin
    public List<Customer> getAllEntities() {
        return repository.findAll();
    }

    public Customer getEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // admin için
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
