package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.dtos.TeamDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.entities.Team;
import com.machado.HomeCashFlow.repositories.TeamRepository;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public ResponseEntity<Team> save(Team team) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamRepository.save(teamRepository.save(team)));
    }

    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.findAll());
    }

    public ResponseEntity<Object> getOne(UUID team_id) {
        Optional<Team> team = teamRepository.findById(team_id);
        return team.<ResponseEntity<Object>>map(
                value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found."));
    }

    public ResponseEntity<Object> update(UUID team_id, TeamDTO teamDTO) {
        Optional<Team> team = teamRepository.findById(team_id);
        if (team.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");
        }
        BeanUtils.copyProperties(teamDTO, team.get());
        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.save(team.get()));
    }

    public ResponseEntity<Object> delete(UUID team_id) {
        Optional<Team> team = teamRepository.findById(team_id);
        if (team.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");
        }
        teamRepository.delete(team.get());
        return ResponseEntity.status(HttpStatus.OK).body("Team deleted successfully.");
    }
}
