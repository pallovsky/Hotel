package pl.edu.agh.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.hotel.dto.request.NewGameRequest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "global_round")
    private Integer globalRound;

    @Column(name = "round_limit")
    private Integer roundLimit;

    @OneToMany(mappedBy = "game")
    private List<Round> rounds;

    @OneToMany(mappedBy="game")
    private List<Company> companies;

    @ManyToMany
    @JoinTable(
            name = "User_Games",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public static Game from(NewGameRequest request, List<User> users) {
        return new Game(
                null,
                request.getName(),
                request.getType(),
                1,
                request.getRoundLimit(),
                Collections.emptyList(),
                Collections.emptyList(),
                users
        );
    }
}
