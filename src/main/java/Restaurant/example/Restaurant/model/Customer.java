	package Restaurant.example.Restaurant.model;
	import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
	import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
	
	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public class Customer {
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		 private Long id;
		    private String name;
		    private String email;
		    @Column(unique = true)
		    private String phone;
		    private LocalDateTime createdAt; 
		    
		    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		    @JsonManagedReference
		    private List<Reservation> reservations;
		    
	}
