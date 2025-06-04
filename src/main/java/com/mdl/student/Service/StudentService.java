package com.mdl.student.Service;

import com.mdl.student.Model.Student;
import com.mdl.student.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserRepository userRepository;


    public Student createStudent(Integer id, String name, String team, Date birthday) {

        Student student = Student.builder()
                .id(id)
                .name(name)
                .name(team)
                .birthday(birthday)
                .createAt(new Date())
                .updatedAt(new Date())
                .deletedAt(null)
                .build();
        return userRepository.save(student);
    }


    public Iterable<Student> getAllStudents() {
        return userRepository.findAll();
    }


    public Optional<Student> getStudentById(Integer id) {
        return userRepository.findById(id);
    }

    public List<Student> getStudentsByName(String name) {
        return userRepository.findByName(name);
    }

    public List<Student> getActiveStudent() {
        return userRepository.findByDeletedAtIsNull();
    }
    public Student updateStudentName(Integer id, String newName) {
        Optional<Student> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        Student student = opt.get();
        student.setName(newName);
        student.setUpdatedAt(new Date());
        return userRepository.save(student);
    }

    public Student updateStudentTeam(Integer id, String newTeam) {
        Optional<Student> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        Student student = opt.get();
        student.setTeam(newTeam);
        student.setUpdatedAt(new Date());
        return userRepository.save(student);
    }

    public Student updateBirthday(Integer id, Date newBirthday) {
        Optional<Student> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        Student student = opt.get();
        student.setBirthday(newBirthday);
        student.setUpdatedAt(new Date());
        return userRepository.save(student);
    }


    public Student deleteStudentById(Integer id) {
        Optional<Student> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        Student student = opt.get();
        student.setDeletedAt(new Date());
        userRepository.save(student);
        return student;
    }

    public List<Student> searchStudent(String searchText) {
        return userRepository.findStudentBySearchText(searchText);
    }
}
