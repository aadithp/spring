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
    private CountryService countryService;

    @GetMapping("/countries")
    public List<Country> getCountries() {
        return this.countryService.getCountries();
    }

    @PostMapping("/countries")
    public Country createCountry(@RequestBody Country country) {
        return this.countryService.createCountry(country);
    }

    @PutMapping("/countries/{countryId}")
    public Country updateCountry(@PathVariable Long countryId,
                                  @RequestBody Country countryRequest) {
        return this.countryService.updateCountry(countryId, countryRequest);
    }


    @DeleteMapping("/countries/{countryId}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long countryId) {
        return this.countryService.deleteCountry(countryId);
    }
}