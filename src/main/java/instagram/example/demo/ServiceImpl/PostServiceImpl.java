package instagram.example.demo.ServiceImpl;


import instagram.example.demo.Entity.Post;
import instagram.example.demo.Repository.PostRepository;
import instagram.example.demo.Services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements instagram.example.demo.Services.PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Post likePost(String postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (post.getLikes().contains(userId)) {
            post.getLikes().remove(userId);
        } else {
            post.getLikes().add(userId);
        }

        return postRepository.save(post);
    }
}

