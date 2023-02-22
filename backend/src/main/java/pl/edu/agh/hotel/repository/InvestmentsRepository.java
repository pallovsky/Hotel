package pl.edu.agh.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.hotel.model.Investment;

import java.util.UUID;

public interface InvestmentsRepository extends JpaRepository<Investment, UUID> {
}
