package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.Token;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.repository.TokenRepository;
import pl.edu.agh.hotel.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Transactional
    public Optional<User> findByToken(String tokenString) throws UnauthorizedException {
        try {
            if (tokenString == null) {
                throw new UnauthorizedException();
            }
            UUID tokenValue = UUID.fromString(tokenString);
            Token token = tokenRepository.findByValue(tokenValue);

            if (token != null) {
                UUID userId = token.getUserId();
                return userRepository.findById(userId);
            } else {
                return Optional.empty();
            }
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }

    @Transactional
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
