package com.mdl.student.controller;

import com.mdl.student.Model.Student;
import com.mdl.student.Service.StudentService;
import com.mdl.student.request.UpdateBirthdayStudentRequest;
import com.mdl.student.request.UpdateNameStudentRequest;
import com.mdl.student.request.UpdateTeamStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class NewController {

    private final StudentService studentService;


    @PostMapping("/{name}/{id}/{team}/{birthday}")
    public @ResponseBody Student addNewUser(
            @PathVariable("name") String name,
            @PathVariable("id") Integer studentID,
            @PathVariable("team") String team,
            @PathVariable("birthday")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday
    ) {

        return studentService.createStudent(studentID, name, team, birthday);
    }

    @GetMapping
    public @ResponseBody Iterable<Student> getAllUsers() {
        return studentService.getAllStudents();
    }

    @GetMapping("/active")
    public @ResponseBody Iterable<Student> getAllActiveUsers() {
        return studentService.getActiveStudent();
    }

    @GetMapping("/{id}")
    public @ResponseBody Student findByID(
            @PathVariable("id") Integer studentID) {

        Optional<Student> opt = studentService.getStudentById(studentID);

        return opt.orElse(null);
    }

    @GetMapping("/search")
    public @ResponseBody ResponseEntity<List<Student>> searchStudent(@RequestParam String searchText){
        List<Student> foundStudents = studentService.searchStudent(searchText);
        if (!foundStudents.isEmpty()) {
            return ResponseEntity.ok(foundStudents);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/name/{name}")
    public @ResponseBody List<Student> findByName(
            @PathVariable("name") String name) {

        return studentService.getStudentsByName(name);
    }

    @PutMapping("/{id}")
    public @ResponseBody Student updateById(
            @PathVariable("id") Integer studentID,

            @RequestBody
            UpdateNameStudentRequest request) {

        return studentService.updateStudentName(studentID, request.getName());
    }

    @PutMapping("/{id}/team")
    public @ResponseBody Student updateById(
            @PathVariable("id") Integer studentID,

            @RequestBody
            UpdateTeamStudentRequest request) {

        return studentService.updateStudentTeam(studentID, request.getTeam());
    }

    @PutMapping("/{id}/birthday")
    public @ResponseBody Student updateBirthdayById(
            @PathVariable("id") Integer studentID,

            @RequestBody
            UpdateBirthdayStudentRequest request) {

        return studentService.updateBirthday(studentID, request.getBirthday());
    }


    @DeleteMapping("/{id}")
    public @ResponseBody Student deleteByID(
            @PathVariable("id") Integer studentID) {

        return studentService.deleteStudentById(studentID);
    }
}
