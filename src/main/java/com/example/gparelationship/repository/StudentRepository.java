package com.example.gparelationship.repository;

import com.example.gparelationship.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);

    Page<Student> findByGroup_Faculty_Id(Integer group_faculty_id, Pageable pageable);

    Page<Student> findByGroup_Id(Integer group_id, Pageable pageable);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

}
