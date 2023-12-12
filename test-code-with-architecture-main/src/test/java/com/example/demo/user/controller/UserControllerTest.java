package com.example.demo.user.controller;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.MyProfileResponse;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.UserUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class UserControllerTest {
    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaab")
                .lastLoginAt(100L)
                .build());
        //when
        ResponseEntity<UserResponse> result = testContainer.userController.getUserById(1);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("ambosing");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        //when
        //then
        assertThatThrownBy(() -> {
            testContainer.userController.getUserById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaab")
                .lastLoginAt(100L)
                .build());
        //when
        //then
        assertThatThrownBy(() -> {
            ResponseEntity<Void> result = testContainer.userController.verifyEmail(1, "ambosing_@naver.com");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaab")
                .lastLoginAt(100L)
                .build());
        //when
        //then

        assertThatThrownBy(() -> {
            testContainer.userController.verifyEmail(1, "asdsad");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaab")
                .address("Seoul")
                .lastLoginAt(100L)
                .build());
        //when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("ambosing_@naver.com");
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("ambosing");
        assertThat(result.getBody().getAddress()).isEqualTo("Seoul");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaab")
                .lastLoginAt(100L)
                .build());
        System.out.println(testContainer.userRepository.findByEmailAndStatus("ambosing_@naver.com", UserStatus.ACTIVE).get().getEmail());
        //when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("ambosing_@naver.com", UserUpdate.builder()
                .address("Pangyo")
                .nickname("ambosing-n")
                .build()
        );
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("ambosing-n");
        assertThat(result.getBody().getAddress()).isEqualTo("Pangyo");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

}
