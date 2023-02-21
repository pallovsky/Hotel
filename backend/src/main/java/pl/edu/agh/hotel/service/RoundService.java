package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.Company;
import pl.edu.agh.hotel.model.Email;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.model.Round;
import pl.edu.agh.hotel.repository.CompanyRepository;
import pl.edu.agh.hotel.repository.EmailRepository;
import pl.edu.agh.hotel.repository.RoundRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class RoundService {

    private RoundRepository roundRepository;
    private CompanyRepository companyRepository;
    private EmailRepository emailRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository, CompanyRepository companyRepository, EmailRepository emailRepository) {
        this.roundRepository = roundRepository;
        this.companyRepository = companyRepository;
        this.emailRepository = emailRepository;
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

        List<Company> companies = game.getUsers().stream().map(user -> new Company(null, null, null, user, game, Collections.emptyList())).toList();
        companyRepository.saveAll(companies);

        List<Email> startingEmails = this.getEmailsForRound(1);
        companies.forEach(company -> {
            List<Email> companyEmails = startingEmails.stream().peek(email -> email.setCompany(company)).toList();
            emailRepository.saveAll(companyEmails);
        });
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
            default -> {}
        }
        return emails;
    }

}
