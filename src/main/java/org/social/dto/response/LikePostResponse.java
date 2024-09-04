package org.social.dto.response;

import lombok.Data;
import org.social.entities.Like;

import java.time.LocalDateTime;

@Data
public class LikePostResponse {
    private Long id;
    private String username;
    private Long postId;
    private LocalDateTime createdDate;

    public static LikePostResponse from(Like like) {
        LikePostResponse response = new LikePostResponse();
        response.setId(like.getLikeId());
        response.setUsername(like.getUser().getUsername());
        response.setPostId(like.getPost().getPostId());
        response.setCreatedDate(like.getCreatedDate());
        return response;
    }
}
