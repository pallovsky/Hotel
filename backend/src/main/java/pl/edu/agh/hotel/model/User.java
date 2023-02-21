package pl.edu.agh.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.hotel.dto.request.NewUserRequest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy="user")
    private List<Round> rounds;

    @OneToMany(mappedBy="user")
    private List<Company> companies;

    @ManyToMany
    @JoinTable(
            name = "User_Games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

    public static User from(NewUserRequest request) {
        return new User(
                null,
                request.getUsername(),
                request.getPassword(),
                request.getRole(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public Boolean isAdmin() {
        return role.equals("ADMIN");
    }
}
