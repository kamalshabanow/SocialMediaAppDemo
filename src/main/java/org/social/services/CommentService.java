package org.social.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.social.dto.request.CommentRequest;
import org.social.dto.response.CommentResponse;
import org.social.entities.Comment;
import org.social.entities.Post;
import org.social.entities.User;
import org.social.repositories.CommentRepository;
import org.social.repositories.PostRepository;
import org.social.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<CommentResponse> getAllCommentsByPostId(Long postId) {
        Post foundPost = this.postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."The post with the \{postId} postId not found"));
        List<Comment> comments = this.commentRepository.findCommentsByPost(foundPost);
        return comments.stream().map(CommentResponse::convertCommentToCommentResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<CommentResponse> getAllCommentsByUsername(String username) {
        User foundUser = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."The user with the \{username} username not found"));
        List<Comment> comments = this.commentRepository.findCommentsByUser(foundUser);
        return comments.stream().map(CommentResponse::convertCommentToCommentResponse).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse createComment(String username, Long postId, CommentRequest commentRequest) {
        User foundUser = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."The username with the \{username} username not found"));
        Post foundPost = this.postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."The post with the \{postId} postId not found"));
        Comment comment = CommentRequest.convertCommentRequestToComment(commentRequest);
        comment.setUser(foundUser);
        comment.setPost(foundPost);
        Comment savedComment = this.commentRepository.save(comment);
        return CommentResponse.convertCommentToCommentResponse(savedComment);
    }

    @Transactional
    public CommentResponse updateComment(String username, Long postId, Long commentId, CommentRequest commentRequest) {
        User foundUser = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."The username with the \{username} username not found"));
        Post foundPost = this.postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."The post with the \{postId} postId not found"));
        Comment foundComment = this.commentRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."The commment with the \{commentId} commentId not found"));
        if(foundComment.getUser().equals(foundUser) && foundComment.getPost().equals(foundPost)){
            Optional.ofNullable(commentRequest.commentText()).ifPresent(foundComment::setCommentText);
            Comment savedComment = this.commentRepository.save(foundComment);
            return CommentResponse.convertCommentToCommentResponse(savedComment);
        }

        return null;
    }

    public Boolean deleteComment(String username, Long postId, Long commentId) {
        User foundUser = this.userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."The username with the \{username} username not found"));
        Post foundPost = this.postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."The post with the \{postId} postId not found"));
        Comment foundComment = this.commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(STR."The commment with the \{commentId} commentId not found"));

        if(foundComment.getUser().equals(foundUser) && foundComment.getPost().equals(foundPost)){
            this.commentRepository.delete(foundComment);
            return true;
        }else{
            return false;
        }
    }
}
