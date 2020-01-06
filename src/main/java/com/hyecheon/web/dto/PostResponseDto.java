package com.hyecheon.web.dto;

import com.hyecheon.domain.post.Post;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"createdData", "lastModifiedDate"})
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdData;
    private LocalDateTime lastModifiedDate;

    @Builder
    public PostResponseDto(Long id, String title, String content, String author, LocalDateTime createdData, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdData = createdData;
        this.lastModifiedDate = lastModifiedDate;
    }

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.createdData = post.getCreatedDate();
        this.lastModifiedDate = post.getLastModifiedDate();
    }
}
