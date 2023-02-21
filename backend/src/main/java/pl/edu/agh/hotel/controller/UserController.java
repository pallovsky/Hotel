package pl.edu.agh.hotel.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.hotel.dto.request.NewUserRequest;
import pl.edu.agh.hotel.dto.response.MessageResponse;
import pl.edu.agh.hotel.exceptions.UnauthorizedException;
import pl.edu.agh.hotel.model.User;
import pl.edu.agh.hotel.dto.response.UserResponse;
import pl.edu.agh.hotel.service.UserService;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@NoArgsConstructor
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getUsers(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent() && currentUser.get().isAdmin()) {
            List<User> users = userService.findAll();
            List<UserResponse> response = users.stream().map(UserResponse::from).toList();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(403).body(new ArrayList<UserResponse>());
        }
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUsers(
            @PathVariable UUID id,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent() && currentUser.get().isAdmin()) {
            Optional<User> user = userService.findById(id);

            if (user.isPresent()) {
                return ResponseEntity.ok(UserResponse.from(user.get()));
            } else {
                return ResponseEntity.status(404).body(new UserResponse());
            }
        } else {
            return ResponseEntity.status(403).body(new UserResponse());
        }
    }

    @PostMapping("/api/users")
    public ResponseEntity<MessageResponse> addUser(
            @RequestBody NewUserRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent() && currentUser.get().isAdmin()) {
            Optional<User> existingUser = userService.findByUsername(request.getUsername());

            if (existingUser.isPresent()) {
                return ResponseEntity.status(400).body(new MessageResponse("Invalid request"));
            }

            User user = User.from(request);
            userService.save(user);

            return ResponseEntity.status(201).body(new MessageResponse("User created"));
        } else {
            return ResponseEntity.status(403).body(new MessageResponse("Unauthorized"));
        }
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<MessageResponse> deleteUser(
            @PathVariable UUID id,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws UnauthorizedException {
        Optional<User> currentUser = userService.findByToken(token);

        if (currentUser.isPresent() && currentUser.get().isAdmin()) {
            Optional<User> existingUser = userService.findById(id);

            if (existingUser.isEmpty()) {
                return ResponseEntity.status(400).body(new MessageResponse("Invalid request"));
            }

            userService.deleteById(id);

            return ResponseEntity.status(204).body(new MessageResponse("User deleted"));
        } else {
            return ResponseEntity.status(403).body(new MessageResponse("Unauthorized"));
        }
    }
}
