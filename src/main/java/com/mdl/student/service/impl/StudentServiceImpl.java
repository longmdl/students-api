package com.mdl.student.service.impl;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.repository.UserRepository;
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

    private final UserRepository userRepository;

    public Iterable<StudentEntity> getAllStudents() {
        return userRepository.findAll();
    }

    public Optional<StudentEntity> getStudentById(Integer id) {
        return userRepository.findById(id);
    }

    public List<StudentEntity> getStudentsByName(String name) {
        return userRepository.findByName(name);
    }

    public List<StudentEntity> getActiveStudent() {
        return userRepository.findByDeletedAtIsNull();
    }
    public StudentEntity updateStudentName(Integer id, String newName) {
        Optional<StudentEntity> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setName(newName);
        student.setUpdatedAt(new Date());
        return userRepository.save(student);
    }

    public StudentEntity updateStudentTeam(Integer id, String newTeam) {
        Optional<StudentEntity> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setTeam(newTeam);
        student.setUpdatedAt(new Date());
        return userRepository.save(student);
    }

    public StudentEntity updateBirthday(Integer id, Date newBirthday) {
        Optional<StudentEntity> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setBirthdate(newBirthday);
        student.setUpdatedAt(new Date());
        return userRepository.save(student);
    }

    public StudentEntity deleteStudentById(Integer id) {
        Optional<StudentEntity> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        StudentEntity student = opt.get();
        student.setDeletedAt(new Date());
        userRepository.save(student);
        return student;
    }

    public List<StudentEntity> searchStudent(String searchText) {
        return userRepository.findStudentBySearchText(searchText);
    }

    @Override
    public List<StudentEntity> list() {
        return userRepository.findAll();
    }

    @Override
    public StudentEntity get(int id) {
        return null;
    }

    @Override
    public StudentEntity create(CreateStudentRequest request) {
        StudentEntity student = StudentEntity.builder()
                .id(String.format("student-%s", UUID.randomUUID()))
                .name(request.getName())
                .team(request.getTeam())
                .birthdate(request.getBirthDate())
                .createAt(new Date())
                .updatedAt(new Date())
                .deletedAt(null)
                .build();
        return userRepository.save(student);
    }
}
