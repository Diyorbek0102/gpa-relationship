package com.example.gparelationship.repository;

import com.example.gparelationship.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    boolean existsByNameAndUniversityId(String name, Integer university_id);
    List<Faculty> findAllByUniversityId(Integer university_id);

}
