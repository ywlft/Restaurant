package Restaurant.example.Restaurant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import Restaurant.example.Restaurant.dto.TableDTO;

import Restaurant.example.Restaurant.model.TableEntity;
import Restaurant.example.Restaurant.repository.TableEntityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableEntityRepository tableRepository;

    
    public List<TableDTO> getAllTables() {
        return tableRepository.findAll()
                .stream()
                .map(t -> new TableDTO(t.getId(), t.getTableNumber(), t.getCapacity(), t.isEmpty()))
                .collect(Collectors.toList());
    }

    
    public TableDTO getTableById(Long id) {
        TableEntity t = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Masa bulunamadı: " + id));
        return new TableDTO(t.getId(), t.getTableNumber(), t.getCapacity(),t.isEmpty());
    }

 
    public TableDTO addTable(TableDTO dto) {
        TableEntity t = new TableEntity(null, dto.getTableNumber(), dto.getCapacity(),dto.isEmpty());
        TableEntity saved = tableRepository.save(t);
        return new TableDTO(saved.getId(), saved.getTableNumber(), saved.getCapacity(),saved.isEmpty());
    }


    public void deleteTable(Long id) {
        if (!tableRepository.existsById(id)) {
            throw new RuntimeException("Masa bulunamadı: " + id);
        }
        tableRepository.deleteById(id);
    }

    public TableDTO updateTable(Long id, TableDTO dto) {
        TableEntity table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Masa bulunamadı: " + id));

     
        table.setTableNumber(dto.getTableNumber());
        table.setCapacity(dto.getCapacity());
        table.setEmpty(dto.isEmpty());

        TableEntity updated = tableRepository.save(table);

        return new TableDTO(updated.getId(), updated.getTableNumber(), updated.getCapacity(), updated.isEmpty());
    }
    
    
    
    
    
}
