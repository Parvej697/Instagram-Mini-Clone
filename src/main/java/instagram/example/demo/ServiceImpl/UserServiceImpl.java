package instagram.example.demo.ServiceImpl;


import instagram.example.demo.Entity.User;
import instagram.example.demo.Repository.UserRepository;
import instagram.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User signup(User user) {
        // Check if email exists
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already taken!");
        }

        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            throw new RuntimeException("No user found with this email!");
        }

        User user = userOpt.get();
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

        me.getFollowing().add(them.getId());
        them.getFollowers().add(me.getId());

        userRepository.save(them);
        return userRepository.save(me);
    }

    @Override
    public User unfollowUser(String userId, String unfollowUserId) {
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        User them = userRepository.findById(unfollowUserId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found!"));
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
