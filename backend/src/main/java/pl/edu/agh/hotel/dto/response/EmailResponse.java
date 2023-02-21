package pl.edu.agh.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.hotel.model.Email;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponse {

    private UUID id;
    private String month;
    private String from;
    private String topic;
    private String message;
    private Boolean opened;

    public static EmailResponse from(Email email) {
        return new EmailResponse(
                email.getId(),
                email.getMonth(),
                email.getFrom(),
                email.getTopic(),
                email.getMessage(),
                email.getOpened()
        );
    }
}
