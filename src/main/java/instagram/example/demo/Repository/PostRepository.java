package instagram.example.demo.Repository;

import instagram.example.demo.Entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findByUserId(String userId);
}
