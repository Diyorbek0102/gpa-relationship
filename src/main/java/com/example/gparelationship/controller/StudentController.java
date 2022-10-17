package com.example.gparelationship.controller;

import com.example.gparelationship.dto.StudentDto;
import com.example.gparelationship.entity.Address;
import com.example.gparelationship.entity.Group;
import com.example.gparelationship.entity.Student;
import com.example.gparelationship.entity.Subject;
import com.example.gparelationship.repository.AddressRepository;
import com.example.gparelationship.repository.GroupRepository;
import com.example.gparelationship.repository.StudentRepository;
import com.example.gparelationship.repository.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    public StudentController(StudentRepository studentRepository, AddressRepository addressRepository, GroupRepository groupRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
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

    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto) {

        boolean exists =
                studentRepository.existsByFirstNameAndLastName(studentDto.getFirstName(), studentDto.getLastName());
        boolean exists1 =
                addressRepository.existsByCityAndDistrictAndStreet(studentDto.getCity(), studentDto.getDistrict(), studentDto.getStreet());

        if (exists)
            return "Student already exist";
        if (exists1)
            return "Address already exist";

        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = new Address(null, studentDto.getCity(), studentDto.getDistrict(), studentDto.getStreet());
        student.setAddress(address);

        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isEmpty()) {
            return "Bunday guruh yo'q";
        }
        Group group = optionalGroup.get();
        student.setGroup(group);

        List<Subject> subjects = subjectRepository.findAllById(studentDto.getSubjectList());
        student.setSubjects(subjects);

        studentRepository.save(student);
        return "Student added";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        try {
            studentRepository.deleteById(id);
            return "student deleted";
        } catch (Exception e) {
            return "Error deleting";
        }
    }

    @PutMapping("/{studentId}")
    public String editStudent(
            @RequestBody StudentDto studentDto, @PathVariable Integer studentId
    ) {

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            return "Bunday student yo'q";
        }
        Student editingStudent = optionalStudent.get();
        editingStudent.setFirstName(studentDto.getFirstName());
        editingStudent.setLastName(studentDto.getLastName());

        Address address = new Address(null, studentDto.getCity(), studentDto.getDistrict(), studentDto.getStreet());
        editingStudent.setAddress(address);

        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isEmpty()) {
            return "Bunday guruh yo'q";
        }
        Group group = optionalGroup.get();
        editingStudent.setGroup(group);

        List<Subject> subjects = subjectRepository.findAllById(studentDto.getSubjectList());
        editingStudent.setSubjects(subjects);

        studentRepository.save(editingStudent);
        return "Student added";
    }

}
