package com.example.demo.post.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.user.service.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 100L)
                .uuidHolder(() -> "aaaa")
                .build();
        testContainer.userCreateService.create(UserCreate.builder()
                .email("ambosing_@naver.com")
                .address("Gyeongi")
                .nickname("ambosing")
                .build());
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();


        //when
        ResponseEntity<PostResponse> result = testContainer.postCreateController.createPost(postCreate);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("ambosing");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
    }
}
