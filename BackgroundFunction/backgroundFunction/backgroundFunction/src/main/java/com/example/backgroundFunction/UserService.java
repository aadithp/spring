package com.example.backgroundFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "userCache")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @CacheEvict(cacheNames = "users", allEntries = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Cacheable(cacheNames = "users")
    public Map<String,Long> getUser(Long userId) {
        return Map.of("countryId", userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId))
                .getCountry().getId());
    }

    @Cacheable(cacheNames = "users", key = "#id", unless = "#result == null")
    public List<User> getUsersByCountryId(Long countryId) {
        return userRepository.findByCountryId(countryId);
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    public User addUser(Long countryId,
                        User users) {
        return countryRepository.findById(countryId)
                .map(country -> {
                    users.setCountry(country);
                    return userRepository.save(users);
                }).orElseThrow(() -> new ResourceNotFoundException("Country not found with id " + countryId));
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    public User updateUser(Long countryId,
                           Long userId,
                           User usersRequest) {
        if(!countryRepository.existsById(countryId)) {
            throw new ResourceNotFoundException("Country not found with id " + countryId);
        }

        return userRepository.findById(userId)
                .map(users -> {
                    users.setName(usersRequest.getName());
                    return userRepository.save(users);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "user", key = "#userId"),
            @CacheEvict(cacheNames = "users", allEntries = true) })
    public ResponseEntity<?> deleteUser(Long countryId,
                                        Long userId) {
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
