package com.example.backgroundFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/countries")
    public Page<Country> getCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    @PostMapping("/countries")
    public Country createCountry(@RequestBody Country country) {
        return countryRepository.save(country);
    }

    @PutMapping("/countries/{countryId}")
    public Country updateCountry(@PathVariable Long countryId,
                                  @RequestBody Country countryRequest) {
        return countryRepository.findById(countryId)
                .map(country -> {
                    country.setName(countryRequest.getName());
                    return countryRepository.save(country);
                }).orElseThrow(() -> new ResourceNotFoundException("Country not found with id " + countryId));
    }


    @DeleteMapping("/countries/{countryId}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long countryId) {
        return countryRepository.findById(countryId)
                .map(country -> {
                    countryRepository.delete(country);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Country not found with id " + countryId));
    }
}