package Restaurant.example.Restaurant.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import Restaurant.example.Restaurant.dto.CustomerDTO;
import Restaurant.example.Restaurant.model.Customer;
import Restaurant.example.Restaurant.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO dto = customerService.getById(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO saved = customerService.save(customerDTO);
            return ResponseEntity.status(201).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);
        try {
            CustomerDTO updated = customerService.update(customerDTO);
            if (updated != null) return ResponseEntity.ok(updated);
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", "Müşteri bulunamadı"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping(value = "/customers", params = "phone")
    public List<CustomerDTO> getCustomerByPhone(@RequestParam String phone) {
        return customerService.getAll().stream()
                .filter(c -> c.getPhone().equals(phone))
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/customers")
    public List<Customer> getAllCustomersForAdmin() {
        return customerService.getAllEntities(); 
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/customers/{id}")
    public ResponseEntity<Customer> getCustomerByIdForAdmin(@PathVariable Long id) {
        Customer customer = customerService.getEntityById(id);
        if (customer != null) return ResponseEntity.ok(customer);
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/customers")
    public ResponseEntity<?> addCustomerForAdmin(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO saved = customerService.save(customerDTO);
            return ResponseEntity.status(201).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/customers/{id}")
    public ResponseEntity<?> updateCustomerForAdmin(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);
        try {
            CustomerDTO updated = customerService.update(customerDTO);
            if (updated != null) return ResponseEntity.ok(updated);
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/customers/{id}")
    public ResponseEntity<?> deleteCustomerForAdmin(@PathVariable Long id) {
        try {
            customerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", "Müşteri bulunamadı"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
