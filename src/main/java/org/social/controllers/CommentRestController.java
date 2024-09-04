package org.social.controllers;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.CommentRequest;
import org.social.dto.response.CommentResponse;
import org.social.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/{postId}/all")
    public List<CommentResponse> getAllCommentsByPostId(@PathVariable Long postId){
        return this.commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping("/{username}/comments")
    public List<CommentResponse> getAllCommentsByUsername(@PathVariable String username){
        return this.commentService.getAllCommentsByUsername(username);
    }

    @PostMapping("/{username}/{postId}/create")
    public CommentResponse createComment(@PathVariable String username, @PathVariable Long postId, @RequestBody CommentRequest commentRequest){
        return this.commentService.createComment(username,postId,commentRequest);
    }

    @PutMapping("/{username}/{postId}/{commentId}/update")
    public CommentResponse updateComment(@PathVariable String username, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest){
        return this.commentService.updateComment(username,postId,commentId,commentRequest);
    }

    @DeleteMapping("/{username}/{postId}/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable String username,@PathVariable Long postId,@PathVariable Long commentId){
        if(this.commentService.deleteComment(username,postId,commentId)){
            return ResponseEntity.ok().body("Comment deleted successfully");
        }else{
            return ResponseEntity.badRequest().body("An error occurred, the comment not deleted");
        }
    }
}
