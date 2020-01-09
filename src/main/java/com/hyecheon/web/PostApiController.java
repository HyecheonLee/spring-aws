package com.hyecheon.web;

import com.hyecheon.service.post.PostService;
import com.hyecheon.web.dto.PostResponseDto;
import com.hyecheon.web.dto.PostSaveRequestDto;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostApiController {

  private final PostService postService;

  @GetMapping
  public ResponseEntity<?> readAll(Pageable pageable) {
    final var posts = postService.findAll(pageable);
    return ResponseEntity.ok(posts);
  }

  @PostMapping
  public ResponseEntity<?> save(@RequestBody PostSaveRequestDto postSaveRequestDto) {
    final PostResponseDto savePost = postService.save(postSaveRequestDto.toEntity());
    return ResponseEntity.created(URI.create("/api/v1/posts/" + savePost.getId())).body(savePost);
  }

  @PutMapping("/{id:[0-9]+}")
  public ResponseEntity<?> save(@PathVariable Long id,
      @RequestBody PostSaveRequestDto postSaveRequestDto) {
    final PostResponseDto savePost = postService.save(id, postSaveRequestDto.toEntity());
    return ResponseEntity.ok().body(savePost);
  }

  @GetMapping("/{id:[0-9]+}")
  public ResponseEntity<?> read(@PathVariable Long id) {
    final PostResponseDto findPost = postService.getById(id);
    return ResponseEntity.ok().body(findPost);
  }

  @DeleteMapping("/{id:[0-9]+}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    postService.deleteById(id);
    return ResponseEntity.ok("");
  }
}
