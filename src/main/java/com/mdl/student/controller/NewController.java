package com.mdl.student.controller;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.request.CreateStudentRequest;
import com.mdl.student.request.UpdateBirthdayStudentRequest;
import com.mdl.student.request.UpdateNameStudentRequest;
import com.mdl.student.request.UpdateTeamStudentRequest;
import com.mdl.student.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class NewController {

    private final StudentServiceImpl studentService;


    @PostMapping()
    public @ResponseBody StudentEntity create(
            @RequestBody CreateStudentRequest request) {
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
            @PathVariable("id") Integer studentID) {

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
            @PathVariable("id") Integer studentID,

            @RequestBody
            UpdateNameStudentRequest request) {

        return studentService.updateStudentName(studentID, request.getName());
    }

    @PutMapping("/{id}/team")
    public @ResponseBody StudentEntity updateById(
            @PathVariable("id") Integer studentID,

            @RequestBody
            UpdateTeamStudentRequest request) {

        return studentService.updateStudentTeam(studentID, request.getTeam());
    }

    @PutMapping("/{id}/birthday")
    public @ResponseBody StudentEntity updateBirthdayById(
            @PathVariable("id") Integer studentID,

            @RequestBody
            UpdateBirthdayStudentRequest request) {

        return studentService.updateBirthday(studentID, request.getBirthday());
    }


    @DeleteMapping("/{id}")
    public @ResponseBody StudentEntity deleteByID(
            @PathVariable("id") Integer studentID) {

        return studentService.deleteStudentById(studentID);
    }
}
