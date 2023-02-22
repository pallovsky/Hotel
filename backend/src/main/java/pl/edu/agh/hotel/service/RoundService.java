package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.*;
import pl.edu.agh.hotel.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
public class RoundService {

    private RoundRepository roundRepository;
    private CompanyRepository companyRepository;
    private EmailRepository emailRepository;
    private OfferRepository offerRepository;
    private InvestmentsRepository investmentsRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository, CompanyRepository companyRepository, EmailRepository emailRepository, OfferRepository offerRepository, InvestmentsRepository investmentsRepository) {
        this.roundRepository = roundRepository;
        this.companyRepository = companyRepository;
        this.emailRepository = emailRepository;
        this.offerRepository = offerRepository;
        this.investmentsRepository = investmentsRepository;
    }

    @Transactional
    public Round getByGameIdAndUserId(UUID gameId, UUID userId) {
        return roundRepository.getByGameIdAndUserId(gameId, userId);
    }

    @Transactional
    public List<Round> getByGameId(UUID gameId) {
        return roundRepository.getByGameId(gameId);
    }

    @Transactional
    public void save(Round round) {
        this.roundRepository.save(round);
    }

    @Transactional
    public void saveAll(List<Round> rounds) {
        this.roundRepository.saveAll(rounds);
    }

    @Transactional
    public void prepareRoundForUser(User user, Game game) {
        Company company = companyRepository.getByUserAndGame(user, game);

        List<Offer> activeOffers = company.getOffers().stream().filter(Offer::getActive).toList();
        List<Investment> activeInvestments = company.getOffers().stream().flatMap(offer -> offer.getInvestments().stream()).filter(Investment::getActive).toList();
        double totalProfits = 0.0;
        double totalCosts = 0.0;

        for (Investment investment : activeInvestments) {
            Offer offer = investment.getOffer();
            offer.setPrice((1 + investment.getPriceChange()) * offer.getPrice());
            offer.setCosts((1 + investment.getCostsChange()) * offer.getCosts());
            offerRepository.save(offer);

            investment.setActive(false);
            investment.setFinished(true);
            investmentsRepository.save(investment);

            totalCosts = totalCosts + investment.getCost();
        }

        for (Offer offer : activeOffers) {
            totalProfits = totalProfits + offer.getPrice();
        }

        company.setFunds(company.getFunds() + totalProfits - totalCosts);
        companyRepository.save(company);
    }

    @Transactional
    public void prepareNextRound(Game game) {
        int roundNumber = game.getGlobalRound() + 1;
        if (roundNumber == 1) {
            prepareFirstRound(game);
        } else {
            List<Company> companies = game.getCompanies();
            List<Email> newEmails = this.getEmailsForRound(roundNumber);

            companies.forEach(company -> {
                List<Email> companyEmails = newEmails.stream().peek(email -> email.setCompany(company)).toList();
                emailRepository.saveAll(companyEmails);
            });
        }
    }

    @Transactional
    public void prepareFirstRound(Game game) {
        List<Round> rounds = game.getUsers().stream().map(user -> new Round(null, user, game, 1)).toList();
        this.saveAll(rounds);

        Double initialFunds = 1000.0;
        List<Company> companies = game.getUsers().stream().map(user -> new Company(null, null, null, initialFunds, initialFunds, user, game, Collections.emptyList(), Collections.emptyList())).toList();
        companyRepository.saveAll(companies);

        List<Email> startingEmails = this.getEmailsForRound(1);
        companies.forEach(company -> {
            List<Email> companyEmails = startingEmails.stream().peek(email -> email.setCompany(company)).toList();
            emailRepository.saveAll(companyEmails);
        });

        List<Offer> startingOffers = getStartingOffers();
        companies.forEach(company -> {
            List<Offer> companyOffers = startingOffers.stream().peek(offer -> offer.setCompany(company)).toList();
            offerRepository.saveAll(companyOffers);
        });

        List<Investment> startingInvestments = getStartingInvestments(startingOffers);
        investmentsRepository.saveAll(startingInvestments);
    }

    private List<Investment> getStartingInvestments(List<Offer> offers) {
        List<Offer> hotelOffers = offers.stream().filter(offer -> offer.getType().equals("HOTEL")).toList();
        List<Investment> hotelInvestments = hotelOffers.stream().flatMap(
                offer -> Stream.of(
                        new Investment(null, "Łóżeczko dla dziecka", 100.0, 0.1, 0.05, false, false, offer),
                        new Investment(null, "Telewizor plazmowy", 2000.0, 0.3, 0.1, false, false, offer),
                        new Investment(null, "Zakup dekoracji", 200.0, 0.15, 0.00, false, false, offer)
                )
        ).toList();

        return hotelInvestments;
    }

    private List<Email> getEmailsForRound(Integer round) {
        List<Email> emails = new ArrayList<>();
        switch (round) {
            case 1 -> {
                emails.add(new Email(null, "Styczeń", "URZĄD SKARBOWY", "Witamy nową firmę w naszej okolicy!", "Treść wiadomości.", false, null));
                emails.add(new Email(null, "Styczeń", "POWIATORY INSPEKTOR PRACY", "Witamy nową firmę w naszej okolicy!", "Treść wiadomości.", false, null));
                emails.add(new Email(null, "Styczeń", "MENTOR", "Wsparcie biznesu - styczeń", "Treść wiadomości.", false, null));
            }
            case 2 -> {
                emails.add(new Email(null, "Luty", "URZĄD SKARBOWY", "Czy wystawiłeś już PIT pracownikom?", "Treść wiadomości.", false, null));
                emails.add(new Email(null, "Luty", "MENTOR", "Wsparcie biznesu - luty", "Treść wiadomości.", false, null));
                emails.add(new Email(null, "Luty", "SPAM", "Stare, zaniedbane firmy w Twojej okolicy!", "Treść wiadomości.", false, null));
            }
            case 3 ->
                    emails.add(new Email(null, "Marzec", "ZUS", "Składki ZUS do opłacenia", "Treść wiadomości.", false, null));
            default -> {
            }
        }
        return emails;
    }

    private List<Offer> getStartingOffers() {
        List<Offer> offers = new ArrayList<>();

        offers.add(new Offer(null, "HOTEL", "Pokój 1 osobowy standard", 200.0, 20.00, 3.2, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "HOTEL", "Pokój 2 osobowy standard", 250.0, 25.00, 3.2, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "HOTEL", "Pokój 3 osobowy standard", 300.0, 30.00, 3.2, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "HOTEL", "Apartament", 240.0, 24.0, 3.2, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "HOTEL", "Pokój 3 osobowy rodzinny", 320.0, 32.00, 3.2, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "HOTEL", "Pokój 4 osobowy rodzinny", 400.0, 40.00, 3.2, false, null, Collections.emptyList()));

        offers.add(new Offer(null, "PARTY", "Chrzciny", 2000.0, 200.00, 4.0, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "PARTY", "Przyjęcie urodzinowe", 3500.0, 350.00, 8.0, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "PARTY", "Wesele", 30000.0, 3000.00, 12.0, false, null, Collections.emptyList()));

        offers.add(new Offer(null, "FOOD", "Drink bar", 100.0, 10.0, 1.0, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "FOOD", "Kolacja", 30.0, 5.0, 0.5, false, null, Collections.emptyList()));
        offers.add(new Offer(null, "FOOD", "Śniadanie", 25.0, 5.0, 0.5, false, null, Collections.emptyList()));

        return offers;
    }

}
