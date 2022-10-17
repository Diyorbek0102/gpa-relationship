package com.example.gparelationship.repository;

import com.example.gparelationship.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);

    List<Subject> findAllById(List<Subject> subjectList);
}
