package com.mdl.student.controller;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.exception.CustomException;
import com.mdl.student.request.*;
import com.mdl.student.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;


    @PostMapping()
    public @ResponseBody StudentEntity create(
            @RequestBody CreateStudentRequest request) throws CustomException {
        return studentService.create(request);
    }

    @GetMapping
    public @ResponseBody List<StudentEntity> list(
            @RequestParam(value = "name", required = false) String name
    ) {
        return studentService.list(name);
    }

    @GetMapping("/active")
    public @ResponseBody Iterable<StudentEntity> getAllActiveUsers() {
        return studentService.getActiveStudent();
    }

    @GetMapping("/{id}")
    public @ResponseBody StudentEntity findByID(
            @PathVariable("id") String studentID) {

        Optional<StudentEntity> opt = studentService.getStudentById(studentID);

        return opt.orElse(null);
    }

    @GetMapping("/search")
    public @ResponseBody ResponseEntity<List<StudentEntity>> searchStudent(@RequestParam String searchText) {
        List<StudentEntity> foundStudents = studentService.searchStudent(searchText);
        if (!foundStudents.isEmpty()) {
            return ResponseEntity.ok(foundStudents);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/name/{name}")
    public @ResponseBody List<StudentEntity> findByName(
            @PathVariable("name") String name) {

        return studentService.getStudentsByName(name);
    }

    @PutMapping("/{id}")
    public @ResponseBody StudentEntity updateById(
            @PathVariable("id") String studentID,

            @RequestBody
            UpdateNameStudentRequest request) {

        return studentService.updateStudentName(studentID, request.getName());
    }

    @PutMapping("/{id}/team")
    public @ResponseBody StudentEntity updateById(
            @PathVariable("id") String studentID,

            @RequestBody
            UpdateTeamStudentRequest request) {

        return studentService.updateStudentTeam(studentID, request.getTeam());
    }

    @PutMapping("/{id}/birthday")
    public @ResponseBody StudentEntity updateBirthdayById(
            @PathVariable("id") String studentID,

            @RequestBody
            UpdateBirthdayStudentRequest request) {

        return studentService.updateBirthday(studentID, request.getBirthday());
    }

    @PutMapping("/{id}/email")
    public @ResponseBody StudentEntity updateEmailById(
            @PathVariable("id") String studentID,

            @RequestBody
            UpdateEmailStudentRequest request) {

        return studentService.updateStudentEmail(studentID, request.getEmail());
    }


    @DeleteMapping("/{id}")
    public @ResponseBody StudentEntity deleteByID(
            @PathVariable("id") String studentID) {

        return studentService.deleteStudentById(studentID);
    }
}
