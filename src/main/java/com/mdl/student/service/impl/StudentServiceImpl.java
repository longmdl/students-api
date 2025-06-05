package com.mdl.student.service.impl;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.entity.TeamEntity;
import com.mdl.student.exception.CustomException;
import com.mdl.student.repository.StudentRepository;
import com.mdl.student.repository.TeamRepository;
import com.mdl.student.request.CreateStudentRequest;
import com.mdl.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final TeamRepository teamRepository;

    public Iterable<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<StudentEntity> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public List<StudentEntity> getStudentsByName(String name) {
        return studentRepository.findAllByName(name);
    }

    public List<StudentEntity> getActiveStudent() {
        return studentRepository.findByDeletedAtIsNull();
    }
    public StudentEntity updateStudentName(String id, String newName) {
        Optional<StudentEntity> opt = studentRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setName(newName);
        student.setUpdatedAt(new Date());
        return studentRepository.save(student);
    }

    public StudentEntity updateStudentEmail(String id, String newEmail) {
        Optional<StudentEntity> opt = studentRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setEmail(newEmail);
        student.setUpdatedAt(new Date());
        return studentRepository.save(student);
    }

    public StudentEntity updateStudentTeam(String id, TeamEntity newTeam) {
        Optional<StudentEntity> opt = studentRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setTeam(newTeam);
        student.setUpdatedAt(new Date());
        return studentRepository.save(student);
    }

    public StudentEntity updateBirthday(String id, Date newBirthday) {
        Optional<StudentEntity> opt = studentRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setBirthdate(newBirthday);
        student.setUpdatedAt(new Date());
        return studentRepository.save(student);
    }

    public StudentEntity deleteStudentById(String id) {
        Optional<StudentEntity> opt = studentRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setDeletedAt(new Date());
        studentRepository.save(student);
        return student;
    }

    public List<StudentEntity> searchStudent(String searchText) {
        return studentRepository.findStudentBySearchText(searchText);
    }

    @Override
    public List<StudentEntity> list(String name) {
        return studentRepository.findAllByName(name);
    }

    @Override
    public StudentEntity get(String id) {
        return null;
    }

    @Override
    public StudentEntity create(CreateStudentRequest request) throws CustomException {
        // 1) Look up the TeamEntity by its name:
        TeamEntity team = teamRepository.findById(request.getTeam())
                .orElseThrow(() -> new CustomException(
                        "No team found with name = " + request.getTeam(), 404));

        // 2) Build and save the StudentEntity:
        StudentEntity student = StudentEntity.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .birthdate(request.getBirthdate())
                .team(team)
                .createAt(new Date())
                .updatedAt(new Date())
                .deletedAt(null)
                .build();

        return studentRepository.save(student);
    }
}
