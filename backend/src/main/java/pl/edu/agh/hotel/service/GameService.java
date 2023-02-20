package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.repository.GameRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class GameService {
    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Transactional
    public Optional<Game> findById(UUID gameId) {
        return gameRepository.findById(gameId);
    }

    @Transactional
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Transactional
    public void deleteById(UUID gameId) {
        gameRepository.deleteById(gameId);
    }
}
