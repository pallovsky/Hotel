package pl.edu.agh.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.hotel.model.Offer;

import java.util.UUID;

public interface OfferRepository extends JpaRepository<Offer, UUID> {
}
