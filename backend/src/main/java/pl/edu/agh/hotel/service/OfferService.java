package pl.edu.agh.hotel.service;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hotel.model.Offer;
import pl.edu.agh.hotel.repository.OfferRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class OfferService {

    private OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Transactional
    public Optional<Offer> findById(UUID offerId) {
        return offerRepository.findById(offerId);
    }

    @Transactional
    public void save(Offer newOffer) {
        offerRepository.save(newOffer);
    }
}
