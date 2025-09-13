package Restaurant.example.Restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Restaurant.example.Restaurant.model.User;
import Restaurant.example.Restaurant.repository.UserRepository;

@Service
public class UserService {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    public User addUser(User user) {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	    }

	    public User updateUser(Long id, User updatedUser) {
	        return userRepository.findById(id).map(user -> {
	            user.setUsername(updatedUser.getUsername());
	            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
	                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
	            }
	            user.setRole(updatedUser.getRole());
	            return userRepository.save(user);
	        }).orElseThrow(() -> new RuntimeException("User not found"));
	    }

	    public void deleteUser(Long id) {
	        userRepository.deleteById(id);
	    }
}
