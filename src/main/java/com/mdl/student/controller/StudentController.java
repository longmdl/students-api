package com.mdl.student.controller;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.exception.CustomException;
import com.mdl.student.ratelimiter.RedisRateLimiter;
import com.mdl.student.request.*;
import com.mdl.student.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Controller with Redis-backed rate limiting applied to endpoints.
 */
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;
    private final RedisRateLimiter rateLimiter;

    /**
     * Centralized rate-limit check.
     */
    private void checkRateLimit(String clientId) {
        if (rateLimiter.isRateLimited(clientId)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded. Try again later.");
        }
    }

    @PostMapping
    public @ResponseBody StudentEntity create(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @RequestBody CreateStudentRequest request) throws CustomException {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.create(request);
    }

    @GetMapping
    public @ResponseBody List<StudentEntity> list(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @RequestParam(value = "name", required = false) String name) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.list(name);
    }

    @GetMapping("/active")
    public @ResponseBody Iterable<StudentEntity> getAllActiveUsers(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.getActiveStudent();
    }

    @GetMapping("/{id}")
    public @ResponseBody StudentEntity findByID(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("id") String studentID) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        Optional<StudentEntity> opt = studentService.getStudentById(studentID);
        return opt.orElse(null);
    }

    @GetMapping("/search")
    public @ResponseBody ResponseEntity<List<StudentEntity>> searchStudent(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @RequestParam String searchText) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        List<StudentEntity> found = studentService.searchStudent(searchText);
        if (!found.isEmpty()) {
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public @ResponseBody List<StudentEntity> findByName(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("name") String name) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.getStudentsByName(name);
    }

    @PutMapping("/{id}")
    public @ResponseBody StudentEntity updateName(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("id") String studentID,
            @RequestBody UpdateNameStudentRequest request) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.updateStudentName(studentID, request.getName());
    }

    @PutMapping("/{id}/team")
    public @ResponseBody StudentEntity updateTeam(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("id") String studentID,
            @RequestBody UpdateTeamStudentRequest request) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.updateStudentTeam(studentID, request.getTeam());
    }

    @PutMapping("/{id}/birthday")
    public @ResponseBody StudentEntity updateBirthday(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("id") String studentID,
            @RequestBody UpdateBirthdayStudentRequest request) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.updateBirthday(studentID, request.getBirthday());
    }

    @PutMapping("/{id}/email")
    public @ResponseBody StudentEntity updateEmail(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("id") String studentID,
            @RequestBody UpdateEmailStudentRequest request) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.updateStudentEmail(studentID, request.getEmail());
    }

    @DeleteMapping("/{id}")
    public @ResponseBody StudentEntity deleteByID(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("id") String studentID) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return studentService.deleteStudentById(studentID);
    }
}
