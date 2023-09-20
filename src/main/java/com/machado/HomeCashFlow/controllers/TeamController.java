package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.AddMemberDTO;
import com.machado.HomeCashFlow.dtos.TeamDTO;
import com.machado.HomeCashFlow.entities.Team;
import com.machado.HomeCashFlow.entities.User;
import com.machado.HomeCashFlow.services.team.TeamService;
import com.machado.HomeCashFlow.services.team.TeamServiceImp;
import com.machado.HomeCashFlow.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/team")
public class TeamController {


    @Autowired
    TeamService service;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid TeamDTO teamDTO) {
        Team teamModel = new Team();
        BeanUtils.copyProperties(teamDTO, teamModel);
        Team selectedTeam = service.getByName(teamDTO.name());

        if (selectedTeam != null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Name already registered.");

        User member = userService.getByEmail(teamDTO.ownerEmail());
        if (member != null) {

            teamModel.setMembers(new ArrayList<>());
            teamModel.getMembers().add(member.getId());
            Team newTeam = service.save(teamModel);

            member.getTeams().add(newTeam.getId());
            userService.save(member);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Team created with success");
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

    @PostMapping("/addMember")
    public ResponseEntity<Object> addNewMember(@RequestBody AddMemberDTO addMemberDTO) {
        Optional<Team> selectedTeam = service.getOne(addMemberDTO.teamId());
        if (selectedTeam.isPresent()) {
            User selectedUser = userService.getByEmail(addMemberDTO.userEmail());
            selectedUser.getTeams().add(addMemberDTO.teamId());
            userService.save(selectedUser);
            selectedTeam.get().getMembers().add(selectedUser.getId());
            Team teamModel = new Team();
            BeanUtils.copyProperties(selectedTeam, teamModel);
            service.save(teamModel);
            return ResponseEntity.status(HttpStatus.OK).body("New member add in team " + selectedTeam.get().getName());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");
    }



}
