package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.hotel.dto.request.LoginRequest;
import pl.edu.agh.hotel.dto.response.TokenResponse;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.Token;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.service.TokenService;
import pl.edu.agh.hotel.service.UserService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class AuthController {

    public final long TOKEN_VALIDITY_TIME = 60 * 60;

    private UserService userService;
    private TokenService tokenService;

    @Autowired
    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) throws UnauthorizedException {
        Optional<User> user = userService.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            Optional<Token> optionalToken = tokenService.findByUserId(user.get().getId());
            Token token;

            if (optionalToken.isPresent()) {
                if (optionalToken.get().getValidUntil().isAfter(Instant.now())) {
                    token = optionalToken.get();
                    token.setValidUntil(Instant.now().plusSeconds(TOKEN_VALIDITY_TIME));
                } else {
                    tokenService.deleteById(optionalToken.get().getId());
                    token = new Token(null, user.get().getId(), UUID.randomUUID(), Instant.now().plusSeconds(TOKEN_VALIDITY_TIME));
                }
            } else {
                token = new Token(null, user.get().getId(), UUID.randomUUID(), Instant.now().plusSeconds(TOKEN_VALIDITY_TIME));
            }

            tokenService.save(token);
            return ResponseEntity.ok(TokenResponse.fromToken(token, user.get().getRole()));
        } else {
            throw new UnauthorizedException();
        }
    }
}
