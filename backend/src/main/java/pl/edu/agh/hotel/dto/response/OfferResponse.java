package pl.edu.agh.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.agh.hotel.model.Offer;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OfferResponse {
    private UUID id;
    private String type;
    private String name;
    private Double price;
    private Double costs;
    private Double workHours;
    private Boolean active;

    public static OfferResponse from(Offer offer) {
        return new OfferResponse(
                offer.getId(),
                offer.getType(),
                offer.getName(),
                offer.getPrice(),
                offer.getCosts(),
                offer.getWorkHours(),
                offer.getActive()
        );
    }
}
