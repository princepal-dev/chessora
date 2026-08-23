package org.princeworks.chessora.repositories;

import org.princeworks.chessora.entity.game.Moves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovesRepository extends JpaRepository<Moves, Long> {}
