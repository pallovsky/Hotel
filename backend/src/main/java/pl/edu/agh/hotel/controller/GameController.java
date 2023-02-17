package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.model.response.GameResponse;
import pl.edu.agh.hotel.service.GameService;
import pl.edu.agh.hotel.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class GameController {
    private GameService gameService;
    private UserService userService;

    @Autowired
    public GameController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/api/games")
    public ResponseEntity<List<GameResponse>> getUsers(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent()) {

            if (currentUser.get().isAdmin()) {
                List<Game> games = gameService.findAll();

                return ResponseEntity.ok(games.stream().map(GameResponse::from).toList());
            } else {
                List<Game> games = currentUser.get().getGames();

                return ResponseEntity.ok(games.stream().map(GameResponse::from).toList());
            }
        } else {
            return ResponseEntity.status(403).body(Collections.emptyList());
        }
    }

    @PostMapping("/api/games")
    public ResponseEntity<List<GameResponse>> getUsers(
            @RequestBody NewGameRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent()) {

        } else {
            return ResponseEntity.status(403).body(Collections.emptyList());
        }
    }
}
