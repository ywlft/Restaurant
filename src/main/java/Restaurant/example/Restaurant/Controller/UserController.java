package Restaurant.example.Restaurant.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Restaurant.example.Restaurant.model.User;
import Restaurant.example.Restaurant.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	


	    @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private PasswordEncoder encoder;

	    @GetMapping
	    public List<User> getUsers() {
	        return userRepository.findAll();
	    }

	    @PostMapping
	    public ResponseEntity<?> addUser(@RequestBody User user) {
	        user.setPassword(encoder.encode(user.getPassword()));
	        userRepository.save(user);
	        return ResponseEntity.ok(user);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
	        User u = userRepository.findById(id).orElseThrow();
	        u.setUsername(user.getUsername());
	        if (!user.getPassword().isEmpty()) u.setPassword(encoder.encode(user.getPassword()));
	        u.setRole(user.getRole());
	        userRepository.save(u);
	        return ResponseEntity.ok(u);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
	        userRepository.deleteById(id);
	        return ResponseEntity.ok(Map.of("message","Silindi"));
	    }
	
}
