package org.social.dto.request;

import lombok.Data;

@Data
public class LikePostRequest {
    private String username;
    private Long postId;
}
