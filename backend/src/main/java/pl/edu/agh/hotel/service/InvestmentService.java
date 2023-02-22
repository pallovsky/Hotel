package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.Investment;
import pl.edu.agh.hotel.repository.InvestmentsRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class InvestmentService {

    private InvestmentsRepository investmentsRepository;

    @Autowired
    public InvestmentService(InvestmentsRepository investmentsRepository) {
        this.investmentsRepository = investmentsRepository;
    }

    @Transactional
    public Optional<Investment> findById(UUID investmentId) {
        return this.investmentsRepository.findById(investmentId);
    }

    @Transactional
    public void save(Investment investment) {
        this.investmentsRepository.save(investment);
    }
}
