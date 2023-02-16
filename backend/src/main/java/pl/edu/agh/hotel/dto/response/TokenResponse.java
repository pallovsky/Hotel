package pl.edu.agh.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.agh.hotel.model.Token;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TokenResponse {
    private UUID value;
    private Instant validUntil;

    public static TokenResponse fromToken(Token token) {
        return new TokenResponse(
          token.getValue(),
          token.getValidUntil()
        );
    }
}
