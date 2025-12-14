package instagram.example.demo.Services;


import instagram.example.demo.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User signup(User user);

    Optional<User> login(String email, String password);
    User followUser(String userId, String followUserId);
    User unfollowUser(String userId, String unfollowUserId);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();  }
