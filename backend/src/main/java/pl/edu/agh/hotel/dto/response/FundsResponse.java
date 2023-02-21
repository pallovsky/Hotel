package pl.edu.agh.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.hotel.model.Company;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundsResponse {
    private Double initialFunds;
    private Double funds;
    private Double estimatedProfits;
    private Double estimatedExpenses;

    public static FundsResponse from(Company company, Double estimatedProfits, Double estimatedExpenses) {
        return new FundsResponse(
                company.getInitialFunds(),
                company.getFunds(),
                estimatedProfits,
                estimatedExpenses
        );
    }
}
