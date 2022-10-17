package com.example.gparelationship.dto;

import com.example.gparelationship.entity.Subject;
import lombok.Data;

import java.util.List;

@Data
public class StudentDto {

    private String firstName;
    private String lastName;
    private String city;
    private String district;
    private String street;
    private Integer groupId;
    private List<Subject> subjectList;

}
