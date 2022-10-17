package com.example.gparelationship.controller;

import com.example.gparelationship.entity.Student;
import com.example.gparelationship.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudent(@RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAll(pageable);
    }

    //2. UNIVERSITY
    @GetMapping("/{universityId}")
    public Page<Student> getStudentsByUniversityId(
            @PathVariable Integer universityId,
            @RequestParam Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
    }

    //3. FACULTET DEKANI
    @GetMapping("/{facultyId}")
    public Page<Student> getStudentByFacultyId(
            @PathVariable Integer facultyId, @RequestParam Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findByGroup_Faculty_Id(facultyId, pageable);
    }

    //4. GURUH RAHBAR
    @GetMapping("/{groupId}")
    public Page<Student> getStudentByGroupId(
            @PathVariable Integer groupId,
            @RequestParam Integer page
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findByGroup_Id(groupId, pageable);
    }

}
