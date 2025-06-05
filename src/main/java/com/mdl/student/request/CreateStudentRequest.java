package com.mdl.student.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateStudentRequest {

    String name;
    String email;
    String team;
    Date birthDate;
}
