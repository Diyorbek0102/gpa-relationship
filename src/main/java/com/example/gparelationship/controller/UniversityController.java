package com.example.gparelationship.controller;

import com.example.gparelationship.dto.UniversityDto;
import com.example.gparelationship.entity.Address;
import com.example.gparelationship.entity.University;
import com.example.gparelationship.repository.AddressRepository;
import com.example.gparelationship.repository.UniversityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/university")
public class UniversityController {

    private final UniversityRepository universityRepository;
    private final AddressRepository addressRepository;

    public UniversityController(UniversityRepository universityRepository, AddressRepository addressRepository) {
        this.universityRepository = universityRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

    @GetMapping("/{id}")
    public University getUniversityById(@PathVariable Integer id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        return optionalUniversity.orElse(null);
    }

    @PostMapping
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(addressRepository.save(address));
        universityRepository.save(university);
        return "University added";
    }

    @DeleteMapping("/{id}")
    public String deleteUniversity(@PathVariable Integer id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            universityRepository.delete(optionalUniversity.get());
            return "Universitet o'chirildi";
        }
        return "Universitet oldindan yo'q";
    }

    @PutMapping("/{id}")
    public String editUniversity(
            @PathVariable Integer id, @RequestBody UniversityDto universityDto
    ) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();
            university.setName(universityDto.getName());

            Address address = university.getAddress();
            address.setStreet(universityDto.getStreet());
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());

            addressRepository.save(address);
            universityRepository.save(university);
            return "University edited";
        }
        return "University not found";
    }

}
