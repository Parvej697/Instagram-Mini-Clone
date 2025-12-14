package instagram.example.demo.Controller;



import instagram.example.demo.Entity.User;
import instagram.example.demo.Security.JwtUtil;
import instagram.example.demo.ServiceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            User savedUser = userService.signup(user);
            return ResponseEntity.ok(savedUser); // return JSON
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User data) {
        try {
            User user = userService.login(data.getEmail(), data.getPassword()).orElseThrow();
            String token = jwtUtil.generateToken(user.getId());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/follow/{followUserId}")
    public User follow(HttpServletRequest request,
                       @PathVariable String followUserId) {

        String userId = (String) request.getAttribute("userId");
        return userService.followUser(userId, followUserId);
    }


    @PostMapping("/unfollow/{unfollowUserId}")
    public User unfollow(HttpServletRequest request,
                         @PathVariable String unfollowUserId) {

        String userId = (String) request.getAttribute("userId");
        return userService.unfollowUser(userId, unfollowUserId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(id).orElseThrow();
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
