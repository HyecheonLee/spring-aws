package com.hyecheon.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.hyecheon.domain.post.Post;
import com.hyecheon.domain.post.PostRepository;
import com.hyecheon.web.dto.PostResponseDto;
import com.hyecheon.web.dto.PostSaveRequestDto;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private PostRepository postRepository;
  private String apiUrl;

  @BeforeEach
  void setUp() {
    apiUrl = "http://localhost:" + port + "/api/v1/posts";
  }

  @AfterEach
  void tearDown() {
    postRepository.deleteAll();
  }

  @DisplayName("posts를 등록하면 201 코드를 받는다")
  @Test
  void post_save() throws Exception {
    final PostSaveRequestDto requestDto = getPostSaveRequestDto();
    //when
    final var responseEntity = restTemplate.postForEntity(apiUrl, requestDto, Object.class);
    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @DisplayName("posts를 등록하면 location URI가 null 이 아니다.")
  @Test
  void post_save2() throws Exception {
    final PostSaveRequestDto requestDto = getPostSaveRequestDto();
    //when
    final var responseEntity = restTemplate.postForEntity(apiUrl, requestDto, Object.class);
    //then
    assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
  }

  @DisplayName("posts를 등록하면 body를 확인한다.")
  @Test
  void post_save3() throws Exception {
    final PostSaveRequestDto requestDto = getPostSaveRequestDto();
    //when
    final var responseEntity = restTemplate
        .postForEntity(apiUrl, requestDto, PostResponseDto.class);
    //then
    assertThat(responseEntity.getBody().getContent()).isEqualTo(requestDto.getContent());
    assertThat(responseEntity.getBody().getTitle()).isEqualTo(requestDto.getTitle());
  }

  @DisplayName("posts를 등록하면 db에 저장된 갯수가 1개이다.")
  @Test
  void post_save4() throws Exception {
    final PostSaveRequestDto requestDto = getPostSaveRequestDto();
    //when
    restTemplate.postForEntity(apiUrl, requestDto, PostResponseDto.class);

    //then
    final var posts = postRepository.findAll();
    assertThat(posts.size()).isEqualTo(1);
  }

  @DisplayName("posts를 등록하면 db에 저장된 데이터가 같다.")
  @Test
  void post_save5() throws Exception {
    final PostSaveRequestDto requestDto = getPostSaveRequestDto();
    //when
    restTemplate.postForEntity(apiUrl, requestDto, PostResponseDto.class);

    //then
    final var posts = postRepository.findAll();
    final var post = posts.get(0);
    assertThat(post.getTitle()).isEqualTo(requestDto.getTitle());
    assertThat(post.getContent()).isEqualTo(requestDto.getContent());
  }

  @DisplayName("posts를 업데이트하면 200 코드를 받는다.")
  @Test
  void post_update1() {
    //given
    final var post = getPost();
    final var savedPost = postRepository.save(post);
    final var postSaveRequestDto = getPostSaveRequestDto();

    //when
    final var responseEntity = restTemplate
        .exchange(apiUrl + "/" + savedPost.getId(), HttpMethod.PUT,
            new HttpEntity<>(postSaveRequestDto), PostResponseDto.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @DisplayName("posts id가 숫자 아닌 값은 404 코드를 받는다.")
  @Test
  void post_update2() {
    //when
    final var responseEntity = restTemplate
        .exchange(apiUrl + "/aa", HttpMethod.PUT, new HttpEntity<>(""), PostResponseDto.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @DisplayName("posts를 업데이트 하면 db 값이 업데이트 된다.")
  @Test
  void post_update3() {
    //given
    final var post = getPost();
    final var savedPost = postRepository.save(post);
    final var postSaveRequestDto = getPostSaveRequestDto();

    //when
    restTemplate.exchange(apiUrl + "/" + savedPost.getId(), HttpMethod.PUT,
        new HttpEntity<>(postSaveRequestDto), PostResponseDto.class);

    //then
    final var updatedPost = postRepository.findById(savedPost.getId()).get();
    assertThat(updatedPost.getContent()).isEqualTo(postSaveRequestDto.getContent());
    assertThat(updatedPost.getTitle()).isEqualTo(postSaveRequestDto.getTitle());
    assertThat(updatedPost.getAuthor()).isEqualTo(postSaveRequestDto.getAuthor());
  }

  @DisplayName("posts를 조회하면 200 코드를 받는다")
  @Test
  void post_get() {
    //given
    final var post = getPost();
    final var savedPost = postRepository.save(post);

    //when
    final var responseEntity = restTemplate
        .getForEntity(apiUrl + "/" + savedPost.getId(), PostResponseDto.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @DisplayName("posts를 조회하면 db값과 동일한 값을 받는다.")
  @Test
  void post_get2() {
    //given
    final var post = getPost();
    final var savedPost = postRepository.save(post);

    //when
    final var responseEntity = restTemplate
        .getForEntity(apiUrl + "/" + savedPost.getId(), PostResponseDto.class);

    //then
    assertThat(responseEntity.getBody()).isEqualTo(new PostResponseDto(savedPost));
  }

  @DisplayName("posts를 삭제하면 db값을 제거한다..")
  @Test
  void post_delete() {
    //given
    final var post = getPost();
    final var savedPost = postRepository.save(post);

    //when
    final var responseEntity = restTemplate
        .exchange(apiUrl + "/" + savedPost.getId(), HttpMethod.DELETE, HttpEntity.EMPTY,
            String.class);

    //then
    final var posts = postRepository.findAll();
    assertThat(posts.size()).isZero();
  }

  @DisplayName("posts 목록 구하기 테스트")
  @Test
  void post_readAll() {
    //given
    final var post = getPost();
    postRepository.save(post);
    //when
    final var responseEntity = restTemplate.getForEntity(apiUrl, Object.class);

    //then
    final Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
    assertThat(body.get("totalElements")).isEqualTo(1);
  }

  private PostSaveRequestDto getPostSaveRequestDto() {
    return PostSaveRequestDto.builder()
        .title("title-post-request")
        .content("content-post-request")
        .author("author-post-request")
        .build();
  }

  private Post getPost() {
    return Post.builder()
        .title("title")
        .content("content")
        .author("author")
        .build();
  }
}