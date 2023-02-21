package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.dto.response.EmailResponse;
import pl.edu.agh.hotel.exceptions.ForbiddenException;
import pl.edu.agh.hotel.exceptions.NotFoundException;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.Company;
import pl.edu.agh.hotel.model.Email;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.service.GameService;
import pl.edu.agh.hotel.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class EmailController {

    private UserService userService;
    private GameService gameService;

    @Autowired
    public EmailController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("api/games/{gameId}/emails")
    public ResponseEntity<List<EmailResponse>> getEmails(
            @PathVariable UUID gameId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException, NotFoundException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent() && currentUser.get().getGames().contains(game.get())) {
            Optional<Company> currentCompany = currentUser.get().getCompanies().stream().filter(company -> company.getGame() == game.get()).findFirst();

            if (currentCompany.isPresent()) {
                return ResponseEntity.ok(currentCompany.get().getEmails().stream().map(EmailResponse::from).toList());
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new ForbiddenException();
        }
    }
}
