package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.dto.request.NewGameRequest;
import pl.edu.agh.hotel.dto.response.MessageResponse;
import pl.edu.agh.hotel.exceptions.ForbiddenException;
import pl.edu.agh.hotel.exceptions.NotFoundException;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.model.Round;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.model.response.GameResponse;
import pl.edu.agh.hotel.service.GameService;
import pl.edu.agh.hotel.service.RoundService;
import pl.edu.agh.hotel.service.UserService;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class GameController {
    private GameService gameService;
    private UserService userService;
    private RoundService roundService;

    @Autowired
    public GameController(UserService userService, GameService gameService, RoundService roundService) {
        this.userService = userService;
        this.gameService = gameService;
        this.roundService = roundService;
    }

    @GetMapping("/api/games/{gameId}")
    public ResponseEntity<GameResponse> getGame(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token,
            @PathVariable UUID gameId) throws UnauthorizedException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent()) {
            if (currentUser.get().isAdmin()) {
                GameResponse response = GameResponse.from(game.get());
                response.setUserRound(game.get().getGlobalRound());

                return ResponseEntity.ok(response);
            } else {
                List<Game> games = currentUser.get().getGames();
                if (games.contains(game.get())) {
                    Integer userRound = roundService.getByGameIdAndUserId(gameId, currentUser.get().getId()).getRound();
                    GameResponse response = GameResponse.from(game.get());
                    response.setUserRound(userRound);

                    return ResponseEntity.ok(response);
                } else {
                    throw new ForbiddenException();
                }
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GetMapping("/api/games")
    public ResponseEntity<List<GameResponse>> getGames(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException {
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
            throw new ForbiddenException();
        }
    }

    @PostMapping("/api/games")
    public ResponseEntity<MessageResponse> createGame(
            @RequestBody NewGameRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, NotFoundException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent() && currentUser.get().isAdmin()) {
            List<User> users = new ArrayList<>();
            for (UUID userId : request.getUsers()) {
                User byId = userService.getById(userId);
                users.add(byId);
            }
            Game game = Game.from(request, users);
            gameService.save(game);

            List<Round> rounds = users.stream().map(user -> new Round(null, user, game, 1)).toList();
            roundService.saveAll(rounds);

            return ResponseEntity.status(201).body(new MessageResponse("Game was created."));
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("api/games/{gameId}")
    public ResponseEntity<MessageResponse> deleteGame(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token,
            @PathVariable UUID gameId
    ) throws UnauthorizedException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent() && currentUser.get().isAdmin()) {
            Optional<Game> existingGame = gameService.findById(gameId);

            if (existingGame.isEmpty()) {
                return ResponseEntity.status(400).body(new MessageResponse("Invalid request"));
            }

            gameService.deleteById(gameId);

            return ResponseEntity.status(204).body(new MessageResponse("User deleted"));
        } else {
            throw new UnauthorizedException();
        }
    }
}
