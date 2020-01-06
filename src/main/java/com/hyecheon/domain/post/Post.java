package com.hyecheon.domain.post;

import com.hyecheon.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String author;

    @Builder
    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(Post post) {
        if (StringUtils.hasText(post.getContent())) {
            this.content = post.getContent();
        }
        if (StringUtils.hasText(post.getTitle())) {
            this.title = post.getTitle();
        }
        if (StringUtils.hasText(post.getAuthor())) {
            this.author = post.getAuthor();
        }
    }
}
