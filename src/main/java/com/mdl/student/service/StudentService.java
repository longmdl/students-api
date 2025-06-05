package com.mdl.student.service;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.request.CreateStudentRequest;

import java.util.List;

public interface StudentService {

    List<StudentEntity> list(String name);

    StudentEntity get(int id);

    StudentEntity create(CreateStudentRequest request);


}
