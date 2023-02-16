package pl.edu.agh.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.hotel.model.Token;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Token findByUserId(UUID userId);

    Token findByValue(UUID value);
}
