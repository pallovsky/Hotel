package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.dto.request.CompanyUpdateRequest;
import pl.edu.agh.hotel.dto.response.CompanyResponse;
import pl.edu.agh.hotel.dto.response.FundsResponse;
import pl.edu.agh.hotel.dto.response.MessageResponse;
import pl.edu.agh.hotel.exceptions.ForbiddenException;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.*;
import pl.edu.agh.hotel.service.CompanyService;
import pl.edu.agh.hotel.service.GameService;
import pl.edu.agh.hotel.service.RoundService;
import pl.edu.agh.hotel.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class CompanyController {

    private UserService userService;
    private GameService gameService;
    private RoundService roundService;
    private CompanyService companyService;

    @Autowired
    public CompanyController(UserService userService, GameService gameService, RoundService roundService, CompanyService companyService) {
        this.userService = userService;
        this.gameService = gameService;
        this.roundService = roundService;
        this.companyService = companyService;
    }

    @GetMapping("/api/games/{gameId}/company")
    public ResponseEntity<CompanyResponse> getCompany(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token,
            @PathVariable UUID gameId) throws UnauthorizedException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent()) {

            List<Game> games = currentUser.get().getGames();
            if (games.contains(game.get())) {
                Company company = companyService.getCompanyByUserAndGame(currentUser.get(), game.get());

                return ResponseEntity.ok(CompanyResponse.from(company));
            } else {
                throw new ForbiddenException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @GetMapping("/api/games/{gameId}/company/funds")
    public ResponseEntity<FundsResponse> getFunds(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token,
            @PathVariable UUID gameId) throws UnauthorizedException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent()) {

            List<Game> games = currentUser.get().getGames();
            if (games.contains(game.get())) {
                Company company = companyService.getCompanyByUserAndGame(currentUser.get(), game.get());

                Double estimatedProfits = company.getOffers().stream().filter(Offer::getActive).mapToDouble(Offer::getPrice).sum();
                Double estimatedExpenses =
                        company.getOffers().stream().filter(Offer::getActive).mapToDouble(Offer::getCosts).sum() +
                        company.getOffers().stream().flatMap(offer -> offer.getInvestments().stream()).filter(Investment::getActive).mapToDouble(Investment::getCost).sum();

                return ResponseEntity.ok(FundsResponse.from(company, estimatedProfits, estimatedExpenses));
            } else {
                throw new ForbiddenException();
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("/api/games/{gameId}/company")
    public ResponseEntity<MessageResponse> updateCompany(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token,
            @PathVariable UUID gameId,
            @RequestBody CompanyUpdateRequest request) throws UnauthorizedException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent()) {

            List<Game> games = currentUser.get().getGames();
            if (games.contains(game.get())) {
                Company company = companyService.getCompanyByUserAndGame(currentUser.get(), game.get());

                company.setName(request.getName());
                company.setMission(request.getMission());

                companyService.save(company);

                return ResponseEntity.ok(new MessageResponse("Company successfully updated"));
            } else {
                throw new ForbiddenException();
            }
        } else {
            throw new ForbiddenException();
        }
    }
}
