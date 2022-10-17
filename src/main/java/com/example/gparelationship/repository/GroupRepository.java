package com.example.gparelationship.repository;

import com.example.gparelationship.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findAllByFaculty_University_Id(Integer faculty_university_id);

    /*@Query("select groups from groups where groups.faculty.university.id =: universityId")
    List<Group> getGroupByUniversityId(Integer universityId);*/

}
