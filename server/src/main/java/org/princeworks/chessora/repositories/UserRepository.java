package org.princeworks.chessora.repositories;

import org.princeworks.chessora.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
