package com.example.backgroundFunction;

import java.util.List;
import java.util.Optional;

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

@Service
//@CacheConfig(cacheNames = "countryCache")
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

//    @Cacheable(cacheNames = "countries")
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

//    @CacheEvict(cacheNames = "countries", allEntries = true)
    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

//    @CacheEvict(cacheNames = "countries", allEntries = true)
    public Country updateCountry(Long countryId,
                                 Country countryRequest) {
        return countryRepository.findById(countryId)
                .map(country -> {
                    country.setName(countryRequest.getName());
                    return countryRepository.save(country);
                }).orElseThrow(() -> new ResourceNotFoundException("Country not found with id " + countryId));
    }


//    @Caching(evict = { @CacheEvict(cacheNames = "country", key = "#countryId"),
//            @CacheEvict(cacheNames = "countries", allEntries = true) })
    public ResponseEntity<?> deleteCountry(Long countryId) {
        return countryRepository.findById(countryId)
                .map(country -> {
                    countryRepository.delete(country);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Country not found with id " + countryId));
    }
}
