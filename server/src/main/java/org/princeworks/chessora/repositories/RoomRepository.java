package org.princeworks.chessora.repositories;

import org.princeworks.chessora.entity.multiplayer.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomCode(Integer roomCode);
}
