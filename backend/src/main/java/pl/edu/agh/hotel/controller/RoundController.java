package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.dto.response.MessageResponse;
import pl.edu.agh.hotel.exceptions.ForbiddenException;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.model.Round;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.service.GameService;
import pl.edu.agh.hotel.service.RoundService;
import pl.edu.agh.hotel.service.UserService;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class RoundController {

    private UserService userService;
    private GameService gameService;
    private RoundService roundService;

    @Autowired
    public RoundController(UserService userService, GameService gameService, RoundService roundService) {
        this.userService = userService;
        this.gameService = gameService;
        this.roundService = roundService;
    }

    @PostMapping("/api/games/{gameId}/nextRound")
    public ResponseEntity<MessageResponse> moveGameToNextRound(
            @PathVariable UUID gameId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent() && currentUser.get().isAdmin()) {
            List<Round> rounds = roundService.getByGameId(gameId);
            Integer currentGlobalRound = game.get().getGlobalRound();

            for (Round round : rounds) {
                if (round.getRound() != currentGlobalRound + 1) {
                    return ResponseEntity.status(400).body(new MessageResponse("Users are still in previous round."));
                }
            }

            Game gameUpdate = game.get();
            gameUpdate.setGlobalRound(game.get().getGlobalRound() + 1);
            gameService.save(gameUpdate);

            return ResponseEntity.ok(new MessageResponse("Round has been updated"));
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("users/{userId}/games/{gameId}/nextRound")
    public ResponseEntity<MessageResponse> moveUserToNextRound(
            @PathVariable UUID gameId,
            @PathVariable UUID userId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent() && currentUser.get().getId() == userId) {
            if (!game.get().getUsers().contains(currentUser.get())) {
                throw new ForbiddenException();
            }

            Round round = roundService.getByGameIdAndUserId(gameId, userId);
            Integer currentUserRound = round.getRound();

            if (!Objects.equals(currentUserRound, game.get().getGlobalRound())) {
                return ResponseEntity.status(400).body(new MessageResponse("Can't go yet to another round."));
            }

            round.setRound(round.getRound() + 1);
            roundService.save(round);

            return ResponseEntity.ok(new MessageResponse("Round has been updated"));
        } else {
            throw new ForbiddenException();
        }
    }
}
