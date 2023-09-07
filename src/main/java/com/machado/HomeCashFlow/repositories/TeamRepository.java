package com.machado.HomeCashFlow.repositories;

import com.machado.HomeCashFlow.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    Team findByName(String name);
}
