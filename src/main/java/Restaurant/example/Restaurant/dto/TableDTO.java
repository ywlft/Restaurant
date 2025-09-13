package Restaurant.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    private Long id;
    private int tableNumber;
    private int capacity;
    private boolean isEmpty;
}