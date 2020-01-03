package com.hyecheon.web;

import com.hyecheon.domain.post.Post;
import com.hyecheon.service.post.PostService;
import com.hyecheon.web.dto.PostResponseDto;
import com.hyecheon.web.dto.PostSaveRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostApiController {
    private final PostService postService;


    @PostMapping()
    public ResponseEntity<?> save(@RequestBody PostSaveRequestDto postSaveRequestDto) {
        final PostResponseDto savePost = postService.save(postSaveRequestDto.toEntity());
        return ResponseEntity.created(URI.create("/api/v1/posts/" + savePost.getId())).body(savePost);
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<?> read(@PathVariable Long id) {
        final PostResponseDto findPost = postService.getById(id);
        return ResponseEntity.ok().body(findPost);
    }
}
