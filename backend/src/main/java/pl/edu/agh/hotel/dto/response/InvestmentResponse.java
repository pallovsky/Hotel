package pl.edu.agh.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.agh.hotel.model.Investment;

import java.util.UUID;

@Data
@AllArgsConstructor
public class InvestmentResponse {
    private UUID id;
    private String name;
    private Double cost;
    private Double priceChange;
    private Double costsChange;
    private Boolean active;
    private Boolean finished;
    private String offerName;

    public static InvestmentResponse from(Investment investment) {
        return new InvestmentResponse(
                investment.getId(),
                investment.getName(),
                investment.getCost(),
                investment.getPriceChange(),
                investment.getCostsChange(),
                investment.getActive(),
                investment.getFinished(),
                investment.getOffer().getName()
        );
    }
}
