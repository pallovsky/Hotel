package pl.edu.agh.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.hotel.model.Email;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<Email, UUID> {
}
