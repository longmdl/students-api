package com.mdl.student.service.impl;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.entity.TeamEntity;
import com.mdl.student.repository.TeamRepository;
import com.mdl.student.request.CreateTeamRequest;
import com.mdl.student.request.UpdateTeamNameRequest;
import com.mdl.student.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public List<TeamEntity> list(String name){
        return teamRepository.findAllbyName(name);
    }
    @Override
    public TeamEntity create(CreateTeamRequest request) {
        TeamEntity team = TeamEntity.builder()
                .name(request.getName())
                .updatedAt(new Date())
                .deletedAt(null)
                .build();

        return teamRepository.save(team);
    }


    public Optional<TeamEntity> getByName(String teamName) {
        return teamRepository.findById(teamName);
    }

    public TeamEntity update(String teamName, UpdateTeamNameRequest request) {
        // 1) Load the existing team
        TeamEntity existing = teamRepository.findById(teamName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot update: no team found with name = " + teamName));

        String newName = request.getNewTeamName();
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("New team name must not be empty");
        }
        if (teamRepository.existsById(newName)) {
            throw new IllegalArgumentException("A team with name '" + newName + "' already exists.");
        }

        // 2) Build a new TeamEntity with the updated name
        TeamEntity renamed = TeamEntity.builder()
                .name(newName)
                .updatedAt(new Date())
                .deletedAt(existing.getDeletedAt())
                // Note: We are not copying over the 'students' list here because
                //   JPA will treat this as a brand‐new row. If you had children
                //   (StudentEntity) pointing to the old teamName, you must re‐assign them.
                .build();

        // 3) Save the new entity first
        TeamEntity savedRenamed = teamRepository.save(renamed);

        // 4) Delete the old entity
        teamRepository.deleteById(teamName);

        return savedRenamed;
    }

    public TeamEntity delete(String teamName) {
        Optional<TeamEntity> opt = teamRepository.findById(teamName);
        if (opt.isEmpty()) {
            return null;
        }
        TeamEntity team = opt.get();
        team.setDeletedAt(new Date());
        teamRepository.save(team);
        return team;
    }
}
