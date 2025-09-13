package Restaurant.example.Restaurant.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import Restaurant.example.Restaurant.dto.TableDTO;
import Restaurant.example.Restaurant.service.TableService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    // Tüm masaları listele (herkes erişebilir)
    @GetMapping
    public List<TableDTO> getAllTables() {
        return tableService.getAllTables();
    }

    // ID ile masa getir (herkes erişebilir)
    @GetMapping("/{id}")
    public ResponseEntity<TableDTO> getTableById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tableService.getTableById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Yeni masa ekle (sadece admin)
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public TableDTO addTable(@RequestBody TableDTO dto) {
        return tableService.addTable(dto);
    }

    // Masa sil (sadece admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        try {
            tableService.deleteTable(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Masa güncelle (sadece admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<TableDTO> updateTable(@PathVariable Long id, @RequestBody TableDTO dto) {
        try {
            return ResponseEntity.ok(tableService.updateTable(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
