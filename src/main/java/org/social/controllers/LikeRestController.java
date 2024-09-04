package org.social.controllers;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.LikeCommentRequest;
import org.social.dto.request.LikePostRequest;
import org.social.dto.response.LikeCommentResponse;
import org.social.dto.response.LikePostResponse;
import org.social.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeRestController {

    private final LikeService likeService;

    @GetMapping("/user/{username}")
    public List<LikePostResponse> getAllUserLikesByUsername(@PathVariable String username){
        return this.likeService.getAllUserLikesByUsername(username);
    }

    @GetMapping("/post/{postId}")
    public List<LikePostResponse> getAllUserLikesByPostId(@PathVariable Long postId){
        return this.likeService.getAllPostLikesByPostId(postId);
    }

    @GetMapping("/comment/{commentId}")
    public List<LikeCommentResponse> getAllUserLikesByCommentId(@PathVariable Long commentId){
        return this.likeService.getAllCommentLikesByCommentId(commentId);
    }

    @PostMapping("/createLikeForPost")
    public ResponseEntity<LikePostResponse> createLikeByPostId(@RequestBody LikePostRequest request) {
        LikePostResponse response = likeService.createLikeByPostId(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/comment")
    public ResponseEntity<LikeCommentResponse> createLikeByCommentId(@RequestBody LikeCommentRequest request) {
        LikeCommentResponse response = likeService.createLikeByCommentId(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/post/{username}/{postId}/{likeId}")
    public ResponseEntity<String> deletePostLikeByLikeId(@PathVariable String username, @PathVariable Long postId, @PathVariable Long likeId){
        if(this.likeService.deletePostLikeByLikeId(username,postId,likeId)){
            return ResponseEntity.ok().body("The like of the post is deleted successfully");
        }else{
            return ResponseEntity.badRequest().body("An error occurred, like not deleted");
        }
    }

    @DeleteMapping("/comment/{username}/{commentId}/{likeId}")
    public ResponseEntity<String> deleteCommentLikeByLikeId(@PathVariable String username, @PathVariable Long commentId, @PathVariable Long likeId){
        if(this.likeService.deleteCommentLikeByLikeId(username,commentId,likeId)){
            return ResponseEntity.ok().body("The like of the comment is deleted successfully");
        }else{
            return ResponseEntity.badRequest().body("An error occurred, the like not deleted");
        }
    }
}
