package pl.edu.agh.hotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.hotel.model.User;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String password;
    private String role;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}
