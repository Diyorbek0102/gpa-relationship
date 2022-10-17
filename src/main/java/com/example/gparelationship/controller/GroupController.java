package com.example.gparelationship.controller;

import com.example.gparelationship.dto.GroupDto;
import com.example.gparelationship.entity.Faculty;
import com.example.gparelationship.entity.Group;
import com.example.gparelationship.repository.FacultyRepository;
import com.example.gparelationship.repository.GroupRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;

    public GroupController(GroupRepository groupRepository, FacultyRepository facultyRepository) {
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId) {
        return groupRepository.findAllByFaculty_University_Id(universityId);
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        Optional<Faculty> optionalFaculty
                = facultyRepository.findById(groupDto.getFacultyId());
        if (optionalFaculty.isEmpty()) {
            return "Faculty not found";
        }
        group.setFaculty(optionalFaculty.get());
        groupRepository.save(group);
        return "Group added";
    }

}
