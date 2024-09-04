package org.social.dto.response;

import lombok.Data;
import org.social.entities.Like;

import java.time.LocalDateTime;

@Data
public class LikeCommentResponse {
    private Long id;
    private String username;
    private Long commentId;
    private LocalDateTime createdDate;

    public static LikeCommentResponse from(Like like) {
        LikeCommentResponse response = new LikeCommentResponse();
        response.setId(like.getLikeId());
        response.setUsername(like.getUser().getUsername());
        response.setCommentId(like.getComment().getCommentId());
        response.setCreatedDate(like.getCreatedDate());
        return response;
    }
}
