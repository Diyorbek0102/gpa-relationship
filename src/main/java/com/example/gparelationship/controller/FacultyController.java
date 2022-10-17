package com.example.gparelationship.controller;

import com.example.gparelationship.dto.FacultyDto;
import com.example.gparelationship.entity.Faculty;
import com.example.gparelationship.entity.University;
import com.example.gparelationship.repository.FacultyRepository;
import com.example.gparelationship.repository.UniversityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;

    public FacultyController(FacultyRepository facultyRepository, UniversityRepository universityRepository) {
        this.facultyRepository = facultyRepository;
        this.universityRepository = universityRepository;
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto){

        if (facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId())) {
            return "Universitetda funday facultet avvaldan bor";
        }

        Optional<University> optionalUniversity =
                universityRepository.findById(facultyDto.getUniversityId());
        if (optionalUniversity.isPresent()) {
            Faculty faculty = new Faculty();
            faculty.setName(facultyDto.getName());
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty added";
        }
        return "Universitet not found";
    }

    @GetMapping
    public List<Faculty> getFaculties(){
        return facultyRepository.findAll();
    }

    @GetMapping("/byUniversityId/{id}")
    public List<Faculty> getFacultyByUniversityId(@PathVariable Integer id){
        return facultyRepository.findAllByUniversityId(id);
    }

    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id){
        try {
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        }catch (Exception e){
            return "Faculty o'chirishda xatolik";
        }
    }

    @PutMapping("{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());
            Optional<University> optionalUniversity = universityRepository.findById(id);
            if (optionalUniversity.isEmpty()) {
                return "University not found";
            }
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty edited";
        }
        return "Faculty not found";
    }

}
