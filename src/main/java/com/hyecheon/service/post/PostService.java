package com.hyecheon.service.post;

import com.hyecheon.domain.exception.NotFoundException;
import com.hyecheon.domain.post.Post;
import com.hyecheon.domain.post.PostRepository;
import com.hyecheon.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hyecheon
 */
@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;

  public PostResponseDto save(Post post) {
    return new PostResponseDto(postRepository.save(post));
  }

  public Page<PostResponseDto> findAll(Pageable pageable) {
    final var posts = postRepository.findAll(pageable);
    return posts.map(PostResponseDto::new);
  }

  public PostResponseDto save(Long id, Post post) {
    final var foundPost = postRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("[ id : " + id + "] 를 찾을수 없습니다."));
    foundPost.update(post);
    return new PostResponseDto(foundPost);
  }

  public PostResponseDto getById(Long id) {
    return new PostResponseDto(postRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("[ id : " + id + "] 를 찾을수 없습니다.")));
  }

  public void deleteById(Long id) {
    postRepository.deleteById(id);
  }
}
