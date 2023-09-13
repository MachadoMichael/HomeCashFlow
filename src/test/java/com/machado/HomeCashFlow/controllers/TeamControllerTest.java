package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.TeamDTO;
import com.machado.HomeCashFlow.entities.Team;

import com.machado.HomeCashFlow.repositories.TeamRepository;
import com.machado.HomeCashFlow.services.team.TeamServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {

    @InjectMocks
    TeamController controller;

    @Mock
    private TeamServiceImp service;

    @Mock
    private TeamRepository repository;

    private Team team;
    private TeamDTO teamDTO;

    @BeforeEach
    public void setup() {

        List<Integer> members = new ArrayList<>();
        members.add(1);
        members.add(2);

        teamDTO = new TeamDTO(
                "home",
                members);

        team = new Team();
        BeanUtils.copyProperties(teamDTO, team);
        team.setId(UUID.randomUUID());

    }

    @Test
    void saveTeamAndReturnHttpCreated() {
        ResponseEntity<Object> response = assertDoesNotThrow(() -> controller.save(teamDTO));
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(service.save(team)), response);
    }

    @Test
    void getAllTeamAndResponseOkAndListIntoBody() {
        ResponseEntity<List<Team>> teamList = assertDoesNotThrow(() -> controller.getAll());
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(service.getAll()), teamList);
    }

    @Test
    void getTeamByIdIfTeamIdFounded() {


        team.setId(UUID.randomUUID());

        ResponseEntity<Object> responseGetById = controller.getOne(team.getId());
        Optional<Team> teamByService = service.getOne(team.getId());
        if (teamByService.isEmpty()) {
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found."), responseGetById);
        } else {
            assertEquals(ResponseEntity.status(HttpStatus.OK).body(teamByService), responseGetById);
        }
    }

    @Test
    void updateTeamById() {
        Team newTeam = new Team();
        BeanUtils.copyProperties(team, newTeam);
        newTeam.setId(UUID.randomUUID());

        ResponseEntity<Object> responsePutById = controller.update(newTeam.getId(), teamDTO);
        Optional<Team> selectedTeam = service.getOne(newTeam.getId());
        if (selectedTeam.isEmpty())
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found."), responsePutById);
        else {
            BeanUtils.copyProperties(teamDTO, selectedTeam.get());
            repository.save(selectedTeam.get());
        }

    }

    @Test
    void deleteTeamById() {
        Optional<Team> responseGet = service.getOne(team.getId());
        ResponseEntity<Object> responseDelete = controller.delete(team.getId());

        if (responseGet.isEmpty())
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found."), responseDelete);
        else
            assertEquals(ResponseEntity.status(HttpStatus.OK).body("Team deleted with success"), responseDelete);
    }


}

