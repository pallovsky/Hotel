package pl.edu.agh.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.hotel.model.Game;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
