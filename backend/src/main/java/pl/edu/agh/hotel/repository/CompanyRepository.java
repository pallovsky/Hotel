package pl.edu.agh.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.hotel.model.Company;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
