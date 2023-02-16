package pl.edu.agh.hotel.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.hotel.model.Token;
import pl.edu.agh.hotel.repository.TokenRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class TokenService {

    private TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public Optional<Token> findByUserId(UUID userId) {
        return Optional.ofNullable(tokenRepository.findByUserId(userId));
    }

    @Transactional
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Transactional
    public void deleteById(UUID id) {
        tokenRepository.deleteById(id);
    }
}
