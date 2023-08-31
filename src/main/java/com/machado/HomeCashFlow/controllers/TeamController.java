package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.TeamDTO;
import com.machado.HomeCashFlow.entities.Team;
import com.machado.HomeCashFlow.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TeamController {


    @Autowired
    TeamService teamService;

    @PostMapping("/team")
    public ResponseEntity<Object> save(@RequestBody @Valid TeamDTO teamDTO) {
        Team teamModel = new Team();
        BeanUtils.copyProperties(teamDTO, teamModel);
        List<Team> filteredTeams = teamService.getAll().stream().
                filter(team -> team.getId().equals(teamModel.getId())).toList();

        if (!filteredTeams.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already registered.");

        return ResponseEntity.status(HttpStatus.OK).body(teamService.save(teamModel));
    }

    @GetMapping("/team")
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getAll());
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID team_id) {

        Optional<Team> team = teamService.getOne(team_id);
        if (team.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");

        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    @PutMapping("/team/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID team_id,
                                         @RequestBody @Valid TeamDTO teamDTO) {

        Optional<Team> team = teamService.getOne(team_id);
        if (team.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");

        BeanUtils.copyProperties(teamDTO, team.get());
        return ResponseEntity.status(HttpStatus.OK).body(teamService.save(team.get()));
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID team_id) {

        Optional<Team> team = teamService.getOne(team_id);
        if (team.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");

        Team teamModel = new Team();
        BeanUtils.copyProperties(team, teamModel);
        teamService.delete(teamModel);

        return ResponseEntity.status(HttpStatus.OK).body("Team deleted with success");
    }
}
