package org.social.controllers;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.PostRequest;
import org.social.dto.response.PostResponse;
import org.social.entities.Post;
import org.social.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping("/allPosts")
    public List<PostResponse> getAllPosts(){
        return this.postService.getAllPosts();
    }

    @GetMapping("/{username}/all")
    public List<PostResponse> getAllPostsByUsername(@PathVariable String username){
        return this.postService.getAllPostsByUsername(username);
    }

    @PostMapping("/{username}/create")
    public PostResponse createPostByUser(@PathVariable String username,@RequestBody PostRequest postRequest){
        return this.postService.createPostByUser(username,postRequest);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(this.postService.updatePost(postId,postRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
       if(postService.deletePostByPostId(postId)){
           return ResponseEntity.ok().body("Post deleted");
       }else{
           return ResponseEntity.badRequest().body("An error occurred, post not deleted");
       }
    }
}
