package com.example.gparelationship.controller;

import com.example.gparelationship.entity.Subject;
import com.example.gparelationship.repository.SubjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable Integer id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.orElse(null);
    }

    @PostMapping
    public String addSubject(@RequestBody Subject subject) {
        if (subjectRepository.existsByName(subject.getName())) {
            return "Subject already exist";
        }
        subjectRepository.save(subject);
        return "Subject added";
    }

    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Integer id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty()) {
            return "Subject already exist!";
        }
        Subject subject = optionalSubject.get();
        subjectRepository.delete(subject);
        return "Subject deleted!";
    }

    @PutMapping("/{id}")
    public String editSubject(
            @PathVariable Integer id,
            @RequestBody Subject subject
    ) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty()) {
            return "Bunday subject oldindan yo'q";
        }
        Subject subject1 = optionalSubject.get();
        if (subject.getName().equals(subject1.getName())){
            return "Bir xil narsani o'zgartirmayman";
        }
        subject1.setName(subject.getName());
        return "Subject edited";
    }

}
