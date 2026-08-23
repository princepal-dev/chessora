package org.princeworks.chessora.repositories;

import org.princeworks.chessora.entity.multiplayer.RoomParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomParticipantRepository extends JpaRepository<RoomParticipant, Long> {}
