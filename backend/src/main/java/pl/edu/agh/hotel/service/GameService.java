package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.repository.GameRepository;

import java.util.List;

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
    public void save(Game game) {
        gameRepository.save(game);
    }
}
