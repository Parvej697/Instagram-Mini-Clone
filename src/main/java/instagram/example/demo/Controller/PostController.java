package instagram.example.demo.Controller;



import instagram.example.demo.Entity.Post;
import instagram.example.demo.ServiceImpl.PostServiceImpl;
import instagram.example.demo.Services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5175")
@RestController
@RequestMapping("/api/posts")

public class PostController {

  private final instagram.example.demo.Services.PostService  postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }


    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }


    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable String userId) {
        return postService.getPostsByUserId(userId);
    }


    @PutMapping("/{postId}/like/{userId}")
    public Post likePost(
            @PathVariable String postId,
            @PathVariable String userId) {
        return postService.likePost(postId, userId);
    }
}

