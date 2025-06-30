package com.mdl.student.controller;

import com.mdl.student.entity.TeamEntity;
import com.mdl.student.ratelimiter.RedisRateLimiter;
import com.mdl.student.request.CreateTeamRequest;
import com.mdl.student.request.UpdateTeamNameRequest;
import com.mdl.student.service.impl.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Controller for Team management with Redis-backed rate limiting.
 */
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamServiceImpl teamService;
    private final RedisRateLimiter rateLimiter;

    /**
     * Centralized rate-limit check.
     */
    private void checkRateLimit(String clientId) {
        if (rateLimiter.isRateLimited(clientId)) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Rate limit exceeded. Try again later."
            );
        }
    }

    /**
     * Create a new Team.
     * POST /teams
     * Body: { "name": "Team Name" }
     */
    @PostMapping
    public @ResponseBody TeamEntity create(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @RequestBody CreateTeamRequest request) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return teamService.create(request);
    }

    /**
     * List teams, optionally filtered by name substring.
     * GET /teams?name=someName
     */
    @GetMapping
    public @ResponseBody List<TeamEntity> list(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @RequestParam(value = "name", required = false) String name) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return teamService.list(name);
    }

    /**
     * Fetch a single Team by its name (primary key).
     * GET /teams/{name}
     */
    @GetMapping("/{name}")
    public @ResponseBody TeamEntity findByName(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("name") String teamName) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        Optional<TeamEntity> opt = teamService.getByName(teamName);
        return opt.orElse(null);
    }

    /**
     * Update a Team’s data.
     * PUT /teams/{name}
     */
    @PutMapping("/{name}")
    public @ResponseBody TeamEntity updateByName(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("name") String teamName,
            @RequestBody UpdateTeamNameRequest request) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return teamService.update(teamName, request);
    }

    /**
     * Delete a Team by its name.
     * DELETE /teams/{name}
     */
    @DeleteMapping("/{name}")
    public @ResponseBody TeamEntity deleteByName(
            @RequestHeader(value = "X-Client-Id", required = false) String clientId,
            @PathVariable("name") String teamName) {
        if (clientId == null || clientId.isBlank()) {
            clientId = "anonymous";
        }
        checkRateLimit(clientId);
        return teamService.delete(teamName);
    }
}
