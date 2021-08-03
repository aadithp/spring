package com.example.backgroundFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/users")
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/users/{userId}")
    public Map<String,Long> getUser(@PathVariable Long userId) {
        return Map.of("countryId", userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId))
                .getCountry().getId());
    }

    @GetMapping("/countries/{countryId}/users")
    public List<User> getUsersByCountryId(@PathVariable Long countryId) {
        return userRepository.findByCountryId(countryId);
    }

    @PostMapping("/countries/{countryId}/users")
    public User addUser(@PathVariable Long countryId,
                        @RequestBody User users) {
        return countryRepository.findById(countryId)
                .map(country -> {
                    users.setCountry(country);
                    return userRepository.save(users);
                }).orElseThrow(() -> new ResourceNotFoundException("Country not found with id " + countryId));
    }

    @PutMapping("/countries/{countryId}/users/{userId}")
    public User updateUser(@PathVariable Long countryId,
                           @PathVariable Long userId,
                           @RequestBody User usersRequest) {
        if(!countryRepository.existsById(countryId)) {
            throw new ResourceNotFoundException("Country not found with id " + countryId);
        }

        return userRepository.findById(userId)
                .map(users -> {
                    users.setName(usersRequest.getName());
                    return userRepository.save(users);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("/countries/{countryId}/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long countryId,
                                          @PathVariable Long userId) {
        if(!countryRepository.existsById(countryId)) {
            throw new ResourceNotFoundException("Country not found with id " + countryId);
        }

        return userRepository.findById(userId)
                .map(users -> {
                    userRepository.delete(users);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

    }
}