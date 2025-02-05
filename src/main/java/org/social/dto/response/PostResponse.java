package org.social.dto.response;

import org.social.entities.Post;

import java.time.LocalDateTime;

public record PostResponse(String username,
                           Long postId,
                           String postText,
                           LocalDateTime createDate) {

    public static PostResponse convertPostToPostResponse(Post post){
        return new PostResponse(post.getUser().getUsername(),post.getPostId(),post.getPostText(),post.getCreatedDate());
    }
}
