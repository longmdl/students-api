package com.mdl.student.controller;

import com.mdl.student.entity.TeamEntity;
import com.mdl.student.request.CreateTeamRequest;
import com.mdl.student.request.UpdateTeamNameRequest;
import com.mdl.student.service.impl.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamServiceImpl teamService;

    /**
     * Create a new Team.
     * POST /teams
     * Body: { "name": "Team Name" }
     */
    @PostMapping
    public @ResponseBody TeamEntity create(@RequestBody CreateTeamRequest request) {
        return teamService.create(request);
    }

    /**
     * List teams, optionally filtered by name substring.
     * GET /teams?name=someName
     */
    @GetMapping
    public @ResponseBody List<TeamEntity> list(
            @RequestParam(value = "name", required = false) String name
    ) {
        return teamService.list(name);
    }

    /**
     * Fetch a single Team by its name (primary key).
     * GET /teams/{name}
     */
    @GetMapping("/{name}")
    public @ResponseBody TeamEntity findByName(
            @PathVariable("name") String teamName
    ) {
        Optional<TeamEntity> opt = teamService.getByName(teamName);
        return opt.orElse(null);
    }

    /**
     * Update a Team’s data.
     * PUT /teams/{name}
     * Body: { "name": "New Team Name", "updatedAt": "...", "deletedAt": "..." }
     * (Assumes UpdateTeamNameRequest has fields for whatever you want to allow changing.)
     */
    @PutMapping("/{name}")
    public @ResponseBody TeamEntity updateByName(
            @PathVariable("name") String teamName,
            @RequestBody UpdateTeamNameRequest request
    ) {
        // Example: Change a team’s name and timestamps
        return teamService.update(teamName, request);
    }

    /**
     * Delete a Team by its name.
     * DELETE /teams/{name}
     */
    @DeleteMapping("/{name}")
    public @ResponseBody TeamEntity deleteByName(
            @PathVariable("name") String teamName
    ) {
        return teamService.delete(teamName);
    }
}
