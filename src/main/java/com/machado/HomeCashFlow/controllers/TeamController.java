package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.TeamDTO;
import com.machado.HomeCashFlow.entities.Team;
import com.machado.HomeCashFlow.services.team.TeamService;
import com.machado.HomeCashFlow.services.team.TeamServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/team")
public class TeamController {


    @Autowired
    TeamService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid TeamDTO teamDTO) {
        Team teamModel = new Team();
        BeanUtils.copyProperties(teamDTO, teamModel);
        Team selectedTeam = service.getByName(teamDTO.name());

        if (selectedTeam != null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Name already registered.");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(teamModel));
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID team_id) {

        Optional<Team> team = service.getOne(team_id);
        if (team.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");

        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID team_id,
                                         @RequestBody @Valid TeamDTO teamDTO) {

        Optional<Team> team = service.getOne(team_id);
        if (team.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");

        BeanUtils.copyProperties(teamDTO, team.get());
        return ResponseEntity.status(HttpStatus.OK).body(service.save(team.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID team_id) {

        Optional<Team> team = service.getOne(team_id);
        if (team.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");

        Team teamModel = new Team();
        BeanUtils.copyProperties(team, teamModel);
        service.delete(teamModel);

        return ResponseEntity.status(HttpStatus.OK).body("Team deleted with success");
    }
}
