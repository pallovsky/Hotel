package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.Company;
import pl.edu.agh.hotel.model.Game;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.repository.CompanyRepository;

@Service
@NoArgsConstructor
public class CompanyService {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public Company getCompanyByUserAndGame(User user, Game game) {
        return companyRepository.getByUserAndGame(user, game);
    }

    @Transactional
    public void save(Company company) {
        this.companyRepository.save(company);
    }
}
