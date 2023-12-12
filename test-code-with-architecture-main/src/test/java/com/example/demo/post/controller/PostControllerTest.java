package com.example.demo.post.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaa")
                .build();
        User user = User.builder()
                .id(1L)
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(1678530673958L)
                .writer(user)
                .build());
        //when
        ResponseEntity<PostResponse> result = testContainer.postController.getPostById(1);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
    }

    @Test
    void 사용자는_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        //when
        //then
        assertThatThrownBy(() -> {
            testContainer.postController.getPostById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void 사용자는_게시물을_수정할_수_있다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaa")
                .clockHolder(() -> 200L)
                .build();
        User user = User.builder()
                .id(1L)
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(1678530673958L)
                .writer(user)
                .build());
        //when
        ResponseEntity<PostResponse> result = testContainer.postController.updatePost(1, PostUpdate.builder()
                .content(":)")
                .build());
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo(":)");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
    }
}
