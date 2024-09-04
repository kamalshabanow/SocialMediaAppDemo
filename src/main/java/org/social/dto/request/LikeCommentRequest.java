package org.social.dto.request;



import lombok.Data;

@Data
public class LikeCommentRequest {
    private String username;
    private Long commentId;
}
