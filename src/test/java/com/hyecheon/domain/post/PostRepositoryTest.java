package com.hyecheon.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @AfterEach
    void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    void 게시글_저장_테스트() {
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author("rainbow880616@gmail.com")
                .build()
        );

        final List<Post> posts = postRepository.findAll();

        final Post post = posts.get(0);

        assertAll("게시글 저장 확인",
                () -> assertEquals(post.getTitle(), title),
                () -> assertEquals(post.getContent(), content)
        );
    }

}