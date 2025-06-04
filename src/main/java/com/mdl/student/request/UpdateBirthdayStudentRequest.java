package com.mdl.student.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UpdateBirthdayStudentRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday;
}
