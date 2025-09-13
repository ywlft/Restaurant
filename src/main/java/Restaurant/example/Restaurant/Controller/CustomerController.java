package Restaurant.example.Restaurant.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import Restaurant.example.Restaurant.dto.CustomerDTO;
import Restaurant.example.Restaurant.model.Customer;

import Restaurant.example.Restaurant.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CustomerController {

    private final CustomerService customerService;

    // --------------------- React App için (maskelenmiş) ---------------------
    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAll(); // maskeli DTO döner
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO dto = customerService.getById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/customers", consumes = "application/json")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.save(customerDTO);
    }

    @GetMapping(value = "/customers", params = "phone")
    public List<CustomerDTO> getCustomerByPhone(@RequestParam String phone) {
        List<CustomerDTO> allMatching = customerService.getAll().stream()
                .filter(c -> c.getPhone().equals(phone))
                .toList();
        return allMatching;
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);
        CustomerDTO updated = customerService.update(customerDTO);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --------------------- Admin Paneli için (maskesiz) ---------------------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/customers")
    public List<Customer> getAllCustomersForAdmin() {
        return customerService.getAllEntities(); // repository.findAll() döner, maskesiz
    }

    @GetMapping("/admin/customers/{id}")
    public ResponseEntity<Customer> getCustomerByIdForAdmin(@PathVariable Long id) {
        Customer customer = customerService.getEntityById(id);
        if (customer != null) return ResponseEntity.ok(customer);
        return ResponseEntity.notFound().build();
    }
}
