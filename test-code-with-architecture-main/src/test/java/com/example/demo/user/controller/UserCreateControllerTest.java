package com.example.demo.user.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.UserCreate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaa")
                .build();
        UserCreate userCreate = UserCreate.builder()
                .email("ambosing_@naver.com")
                .address("Gyeongi")
                .nickname("ambosing")
                .build();
        //when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.createUser(userCreate);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("ambosing");
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaa");


    }
}
