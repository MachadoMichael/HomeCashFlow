package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.TeamDTO;
import com.machado.HomeCashFlow.entities.Team;
import com.machado.HomeCashFlow.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping("/team")
    public ResponseEntity<Team> save(@RequestBody @Valid TeamDTO teamDTO) {
        return teamService.save(teamDTO);
    }

    @GetMapping("/team")
    public ResponseEntity<List<Team>> getAll() {
        return teamService.getAll();
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID team_id) {
        return teamService.getOne(team_id);
    }

    @PutMapping("/team/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID team_id,
                                         @RequestBody @Valid TeamDTO teamDTO) {
        return teamService.update(team_id, teamDTO);
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID team_id) {
        return teamService.delete(team_id);
    }


}
