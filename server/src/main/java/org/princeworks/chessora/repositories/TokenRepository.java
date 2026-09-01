package org.princeworks.chessora.repositories;

import org.princeworks.chessora.entity.user.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
    @Query("SELECT vt from VerificationToken vt JOIN FETCH vt.user WHERE vt.token = :token")
    Optional<VerificationToken> findByToken(String token);
}
