package instagram.example.demo.Services;



import instagram.example.demo.Entity.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(Comment comment);

    List<Comment> getCommentsByPostId(String postId);

    void deleteComment(String commentId);
}

