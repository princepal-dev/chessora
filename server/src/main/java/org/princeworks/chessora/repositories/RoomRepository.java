package org.princeworks.chessora.repositories;

import org.princeworks.chessora.entity.multiplayer.Room;
import org.princeworks.chessora.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomCode(Long roomCode);
    Page<Room> findByRoomCreator(Pageable pageable, User user);
}
