package org.social.repositories;

import org.social.entities.Comment;
import org.social.entities.Post;
import org.social.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findCommentsByPost(Post foundPost);

    List<Comment> findCommentsByUser(User foundUser);
}
