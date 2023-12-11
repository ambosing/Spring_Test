package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    void PostCreate로_게시물을_만들_수_있다() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();
        User writer = User.builder()
                .id(1L)
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        //when
        Post post = Post.from(writer, postCreate);
        //then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getWriter().getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("ambosing");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }
}