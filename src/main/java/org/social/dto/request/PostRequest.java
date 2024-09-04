package org.social.dto.request;

import org.social.entities.Post;

import java.time.LocalDateTime;

public record PostRequest(String postText) {

    public static Post convertPostRequestToPost(PostRequest postRequest){
        Post post = new Post();
        post.setPostText(postRequest.postText);
        post.setCreatedDate(LocalDateTime.now());

        return post;
    }
}
