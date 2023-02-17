package pl.edu.agh.hotel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.hotel.model.Game;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private String name;
    private Integer usersCount;

    public static GameResponse from(Game game) {
        return new GameResponse(game.getName(), game.getUsers().size());
    }
}
