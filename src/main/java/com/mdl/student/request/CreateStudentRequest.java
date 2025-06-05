package com.mdl.student.request;

import com.mdl.student.entity.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateStudentRequest {
    String id;
    String name;
    String email;
    String team;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthdate;
}
