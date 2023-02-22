package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.dto.response.MessageResponse;
import pl.edu.agh.hotel.dto.response.OfferResponse;
import pl.edu.agh.hotel.exceptions.ForbiddenException;
import pl.edu.agh.hotel.exceptions.NotFoundException;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.Company;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.model.Offer;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.service.CompanyService;
import pl.edu.agh.hotel.service.GameService;
import pl.edu.agh.hotel.service.OfferService;
import pl.edu.agh.hotel.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class OfferController {

    private UserService userService;
    private GameService gameService;
    private OfferService offerService;
    private CompanyService companyService;

    @Autowired
    public OfferController(UserService userService, GameService gameService, OfferService offerService, CompanyService companyService) {
        this.userService = userService;
        this.gameService = gameService;
        this.offerService = offerService;
        this.companyService = companyService;
    }

    @GetMapping("api/games/{gameId}/offers")
    public ResponseEntity<List<OfferResponse>> getOffers(
            @PathVariable UUID gameId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException, NotFoundException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent() && currentUser.get().getGames().contains(game.get())) {
            Company company = companyService.getCompanyByUserAndGame(currentUser.get(), game.get());
            List<Offer> offers = company.getOffers();

            return ResponseEntity.ok(offers.stream().map(OfferResponse::from).toList());
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("api/games/{gameId}/offers/{offerId}")
    public ResponseEntity<MessageResponse> updateOffers(
            @PathVariable UUID gameId,
            @PathVariable UUID offerId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException, ForbiddenException, NotFoundException {
        Optional<User> currentUser = userService.findByToken(token);
        Optional<Game> game = gameService.findById(gameId);

        if (currentUser.isPresent() && game.isPresent() && currentUser.get().getGames().contains(game.get())) {
            Company company = companyService.getCompanyByUserAndGame(currentUser.get(), game.get());
            Optional<Offer> offer = offerService.findById(offerId);

            if (offer.isEmpty() || !company.getOffers().contains(offer.get())) {
                throw new NotFoundException();
            }

            Offer newOffer = offer.get();
            newOffer.setActive(!offer.get().getActive());
            offerService.save(newOffer);

            return ResponseEntity.ok(new MessageResponse("Offer was updated successfully."));
        } else {
            throw new ForbiddenException();
        }
    }
}
