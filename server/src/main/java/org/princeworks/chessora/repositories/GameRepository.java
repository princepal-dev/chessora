package org.princeworks.chessora.repositories;

import org.princeworks.chessora.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {}
