package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.dto.response.InvestmentResponse;
import pl.edu.agh.hotel.dto.response.MessageResponse;
import pl.edu.agh.hotel.exceptions.ForbiddenException;
import pl.edu.agh.hotel.exceptions.NotFoundException;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.*;
import pl.edu.agh.hotel.service.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class InvestmentController {

    private UserService userService;
    private GameService gameService;
    private OfferService offerService;
    private CompanyService companyService;
    private InvestmentService investmentService;

    @Autowired
    public InvestmentController(UserService userService, GameService gameService, OfferService offerService, CompanyService companyService, InvestmentService investmentService) {
        this.userService = userService;
        this.gameService = gameService;
        this.offerService = offerService;
        this.companyService = companyService;
        this.investmentService = investmentService;
    }

    @GetMapping("api/games/{gameId}/investments")
    public ResponseEntity<List<InvestmentResponse>> getInvestments(
            @PathVariable UUID gameId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent() && currentUser.get().getGames().contains(game.get())) {
            Company company = companyService.getCompanyByUserAndGame(currentUser.get(), game.get());
            List<Offer> offers = company.getOffers();
            List<Investment> investments = offers.stream().flatMap(offer -> offer.getInvestments().stream()).toList();

            return ResponseEntity.ok(investments.stream().map(InvestmentResponse::from).toList());
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("api/games/{gameId}/investments/{investmentId}")
    public ResponseEntity<MessageResponse> updateOffers(
            @PathVariable UUID gameId,
            @PathVariable UUID investmentId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException, NotFoundException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent() && currentUser.get().getGames().contains(game.get())) {
            Company company = companyService.getCompanyByUserAndGame(currentUser.get(), game.get());
            Optional<Investment> investment = investmentService.findById(investmentId);

            if (investment.isEmpty() || investment.get().getOffer().getCompany() != company) {
                throw new NotFoundException();
            }

            Investment newInvestment = investment.get();
            newInvestment.setActive(true);
            investmentService.save(newInvestment);

            return ResponseEntity.ok(new MessageResponse("Investment was updated successfully."));
        } else {
            throw new ForbiddenException();
        }
    }
}
