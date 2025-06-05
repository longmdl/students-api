package com.mdl.student.service;

import com.mdl.student.entity.TeamEntity;
import com.mdl.student.request.CreateStudentRequest;
import com.mdl.student.request.CreateTeamRequest;

import java.util.List;

public interface TeamService {

    List<TeamEntity> list(String name);


    TeamEntity create(CreateTeamRequest request);
}
