package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.entities.Team;
import com.machado.HomeCashFlow.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public Optional<Team> getOne(UUID team_id) {
        return teamRepository.findById(team_id);
    }

    public void delete(Team team) {
        teamRepository.delete(team);
    }
}
