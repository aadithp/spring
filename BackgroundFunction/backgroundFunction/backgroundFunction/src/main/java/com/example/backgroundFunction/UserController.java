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
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public Map<String,Long> getUser(@PathVariable Long userId) {
        return this.userService.getUser(userId);
    }

//    @GetMapping("/countries/{countryId}/users")
//    public List<User> getUsersByCountryId(@PathVariable Long countryId) {
//        return userService.getUsersByCountryId(countryId);
//    }

    @PostMapping("/countries/{countryId}/users")
    public User addUser(@PathVariable Long countryId,
                        @RequestBody User users) {
        return this.userService.addUser(countryId, users);
    }

    @PutMapping("/countries/{countryId}/users/{userId}")
    public User updateUser(@PathVariable Long countryId,
                           @PathVariable Long userId,
                           @RequestBody User usersRequest) {
        return this.userService.updateUser(countryId, userId, usersRequest);
    }

    @DeleteMapping("/countries/{countryId}/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long countryId,
                                          @PathVariable Long userId) {
        return this.userService.deleteUser(countryId, userId);

    }
}