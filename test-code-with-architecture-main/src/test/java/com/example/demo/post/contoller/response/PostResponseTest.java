package com.example.demo.post.contoller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {

    @Test
    void Post로_응답을_생성할_수_있다() throws Exception {
        //given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .email("ambosing_@naver.com")
                        .nickname("ambosing")
                        .address("Seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                        .build())
                .build();
        //when
        PostResponse postResponse = PostResponse.from(post);

        //then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("ambosing");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

}