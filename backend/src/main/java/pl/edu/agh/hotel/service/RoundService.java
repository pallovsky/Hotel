package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.Round;
import pl.edu.agh.hotel.repository.RoundRepository;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class RoundService {

    private RoundRepository roundRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Transactional
    public void save(Round round) {
        this.roundRepository.save(round);
    }
    @Transactional
    public void saveAll(List<Round> rounds) {
        this.roundRepository.saveAll(rounds);
    }
    @Transactional
    public Round getByGameIdAndUserId(UUID gameId, UUID userId) {
        return roundRepository.getByGameIdAndUserId(gameId, userId);
    }

    @Transactional
    public List<Round> getByGameId(UUID gameId) {
        return roundRepository.getByGameId(gameId);
    }
}
