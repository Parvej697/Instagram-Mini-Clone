package instagram.example.demo.Controller;



import instagram.example.demo.Entity.Comment;
import instagram.example.demo.Services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:5175")
public class CommentController {

    private final instagram.example.demo.Services.CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }


    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable String postId) {
        return commentService.getCommentsByPostId(postId);
    }


    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
    }
}
