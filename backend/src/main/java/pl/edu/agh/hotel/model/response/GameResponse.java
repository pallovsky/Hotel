package pl.edu.agh.hotel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.hotel.model.Game;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private UUID id;
    private String name;
    private String type;
    private Integer usersCount;
    private Integer globalRound;
    private Integer roundLimit;
    private Integer userRound;

    public static GameResponse from(Game game) {
        return new GameResponse(game.getId(), game.getName(), game.getType(), game.getUsers().size(), game.getGlobalRound(), game.getRoundLimit(), null);
    }
}
