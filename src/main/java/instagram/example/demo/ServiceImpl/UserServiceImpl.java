package instagram.example.demo.ServiceImpl;


import instagram.example.demo.Entity.User;
import instagram.example.demo.Repository.UserRepository;
import instagram.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Human-style service code with inline comments
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Simple password encoder
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User signup(User user) {
        // Check if email exists
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already taken!");
        }

        // Check if username exists
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            throw new RuntimeException("No user found with this email!");
        }

        User user = userOpt.get();

        // Check password
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }

        return Optional.of(user);
    }

    @Override
    public User followUser(String userId, String followUserId) {
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        User them = userRepository.findById(followUserId)
                .orElseThrow(() -> new RuntimeException("User to follow not found!"));

        // Add to following/followers sets
        me.getFollowing().add(them.getId());
        them.getFollowers().add(me.getId());

        // Save both users
        userRepository.save(them);
        return userRepository.save(me);
    }

    @Override
    public User unfollowUser(String userId, String unfollowUserId) {
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        User them = userRepository.findById(unfollowUserId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found!"));

        // Remove from following/followers sets
        me.getFollowing().remove(them.getId());
        them.getFollowers().remove(me.getId());

        userRepository.save(them);
        return userRepository.save(me);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
