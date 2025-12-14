package instagram.example.demo.Services;


import instagram.example.demo.Entity.User;

import java.util.List;
import java.util.Optional;

// Just basic user operations
public interface UserService {
    User signup(User user);  // Register new user

    Optional<User> login(String email, String password);  // Login

    User followUser(String userId, String followUserId);  // Follow

    User unfollowUser(String userId, String unfollowUserId); // Unfollow

    Optional<User> getUserById(String id);  // Get user info

    List<User> getAllUsers();  // List all users
}
