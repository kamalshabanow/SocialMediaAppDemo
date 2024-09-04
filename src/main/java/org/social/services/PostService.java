package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.PostRequest;
import org.social.dto.response.PostResponse;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repositories.PostRepository;
import org.social.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<PostResponse> getAllPosts() {
        List<Post> posts = this.postRepository.findAll(Sort.by(Sort.Direction.DESC,"createdDate"));
        return posts.stream().map(PostResponse::convertPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getAllPostsByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        List<Post> posts = this.postRepository.findPostByUser(user);
        return posts.stream().map(PostResponse::convertPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse createPostByUser(String username, PostRequest postRequest) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} not found"));
        Post post = PostRequest.convertPostRequestToPost(postRequest);
        post.setUser(user);
        Post savedPost = this.postRepository.save(post);
        return PostResponse.convertPostToPostResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post foundPost = this.postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."Post with \{postId} postId not found"));
        Optional.ofNullable(postRequest.postText()).ifPresent(foundPost::setPostText);
        Post savedPost = this.postRepository.save(foundPost);
        return PostResponse.convertPostToPostResponse(savedPost);
    }

    @Transactional
    public Boolean deletePostByPostId(Long postId) {
        try {
            Post foundPost = this.postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."Post with \{postId} postId not found"));
            this.postRepository.delete(foundPost);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
