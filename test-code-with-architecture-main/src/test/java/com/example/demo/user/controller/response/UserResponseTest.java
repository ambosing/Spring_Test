package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseTest {

    @Test
    void User로_응답을_생성할_수_있다() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        //when
        UserResponse myProfileResponse = UserResponse.from(user);

        //then
        assertThat(myProfileResponse.getId()).isEqualTo(1);
        assertThat(myProfileResponse.getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }
}