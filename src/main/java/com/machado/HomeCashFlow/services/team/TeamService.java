package com.machado.HomeCashFlow.services.team;

import com.machado.HomeCashFlow.entities.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamService {


    Team save(Team team);

    List<Team> getAll();

    Optional<Team> getOne(UUID team_id);

    void delete(Team team);

    Team getByName(String name);
}
