package com.mdl.student.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateTeamNameRequest {
    private String newTeamName;
}