package org.social.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "post text must not be empty")
    @Column(columnDefinition = "TEXT")
    private String postText;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Like> likes;
}
