package pl.edu.agh.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.hotel.model.Round;

import java.util.List;
import java.util.UUID;

public interface RoundRepository extends JpaRepository<Round, UUID> {
    
    Round getByGameIdAndUserId(UUID gameId, UUID userId);

    List<Round> getByGameId(UUID gameId);
}
