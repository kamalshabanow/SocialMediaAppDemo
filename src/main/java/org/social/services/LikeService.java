package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.LikeCommentRequest;
import org.social.dto.request.LikePostRequest;
import org.social.dto.response.LikeCommentResponse;
import org.social.dto.response.LikePostResponse;
import org.social.entities.Comment;
import org.social.entities.Like;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repositories.CommentRepository;
import org.social.repositories.LikeRepository;
import org.social.repositories.PostRepository;
import org.social.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public List<LikePostResponse> getAllUserLikesByUsername(String username) {
        List<Like> likes = this.likeRepository.findByUserUsername(username);

        return likes.stream()
                .map(LikePostResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LikePostResponse> getAllPostLikesByPostId(Long postId) {
        List<Like> likes = this.likeRepository.findByPostPostId(postId);

        return likes.stream()
                .map(LikePostResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LikeCommentResponse> getAllCommentLikesByCommentId(Long commentId) {
        List<Like> likes = this.likeRepository.findByCommentCommentId(commentId);

        return likes.stream()
                .map(LikeCommentResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public LikePostResponse createLikeByPostId(LikePostRequest request) {
        User foundUser = this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("The user with the username - " + request.getUsername() + " not found"));
        Post foundPost = this.postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("The post with the postId - " + request.getPostId() + " not found"));
        Like like = new Like();
        like.setUser(foundUser);
        like.setPost(foundPost);
        like.setCreatedDate(LocalDateTime.now());
        Like savedLike = this.likeRepository.save(like);

        return LikePostResponse.from(savedLike);
    }

    @Transactional
    public LikeCommentResponse createLikeByCommentId(LikeCommentRequest request) {
        User foundUser = this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("The user with the username - " + request.getUsername() + " not found"));
        Comment foundComment = this.commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("The comment with the commentId - " + request.getCommentId() + " not found"));
        Like like = new Like();
        like.setUser(foundUser);
        like.setComment(foundComment);
        like.setCreatedDate(LocalDateTime.now());
        Like savedLike = this.likeRepository.save(like);

        return LikeCommentResponse.from(savedLike);
    }

    @Transactional
    public Boolean deletePostLikeByLikeId(String username, Long postId, Long likeId) {
        User foundUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("The user with the username - " + username + " not found"));
        Post foundPost = this.postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("The post with the postId - " + postId + " not found"));
        Like foundLike = this.likeRepository.findById(likeId)
                .orElseThrow(() -> new IllegalArgumentException("The like with the likeId - " + likeId + " not found"));
        if (username.equals(foundUser.getUsername()) && postId.equals(foundPost.getPostId())) {
            this.likeRepository.delete(foundLike);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean deleteCommentLikeByLikeId(String username, Long commentId, Long likeId) {
        User foundUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("The user with the username - " + username + " not found"));
        Comment foundComment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("The comment with the commentId - " + commentId + " not found"));
        Like foundLike = this.likeRepository.findById(likeId)
                .orElseThrow(() -> new IllegalArgumentException("The like with the likeId - " + likeId + " not found"));
        if (username.equals(foundUser.getUsername()) && commentId.equals(foundComment.getCommentId())) {
            this.likeRepository.delete(foundLike);
            return true;
        }
        return false;
    }
}
