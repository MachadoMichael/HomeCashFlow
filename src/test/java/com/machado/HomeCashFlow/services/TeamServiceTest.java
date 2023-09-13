package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.dtos.TeamDTO;
import com.machado.HomeCashFlow.entities.Team;
import com.machado.HomeCashFlow.repositories.TeamRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.machado.HomeCashFlow.services.team.TeamServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @InjectMocks
    TeamServiceImp service;

    @Mock
    TeamRepository repository;

    Team team;

    @BeforeEach
    public void setUp() {
        team = new Team();
        List<Integer> members = new ArrayList<>();
        members.add(1);
        members.add(2);

        TeamDTO teamDTO = new TeamDTO(
                "home",
                members);
        BeanUtils.copyProperties(teamDTO, team);
    }

    @Test
    void getAllTeams() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(team));
        List<Team> teamList = service.getAll();

        assertEquals(Collections.singletonList(team), teamList);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);

    }

    @Test
    void saveNewTeam() {
        Team newTeam = new Team();
        BeanUtils.copyProperties(team, newTeam);
        newTeam.setId(UUID.randomUUID());

        Mockito.when(service.save(team)).thenReturn(newTeam);
        assertEquals(newTeam, service.save(team));
    }

    @Test
    void getOneTeamById() {
        team.setId(UUID.randomUUID());
        Mockito.when(service.getOne(team.getId())).thenReturn(Optional.ofNullable(team));

        assertEquals(service.getOne(team.getId()), Optional.ofNullable(team));
    }
}
