package instagram.example.demo.Services;

import instagram.example.demo.Entity.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post);

    List<Post> getAllPosts();

    List<Post> getPostsByUserId(String userId);

    Post likePost(String postId, String userId);
}