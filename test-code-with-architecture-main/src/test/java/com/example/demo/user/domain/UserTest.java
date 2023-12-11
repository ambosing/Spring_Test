package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.service.UserCreate;
import com.example.demo.user.service.UserUpdate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    void UserCreate_객체로_생성할_수_있다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("zziwon@kakao.com")
                .nickname("zziwon")
                .address("Pangyo")
                .build();
        //when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(user.getNickname()).isEqualTo("ambosing");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void UserUpdate_객체로_데이터를_업데이트할_수_있다() throws Exception {
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
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("zziwon-n")
                .address("Pangyo")
                .build();
        //when
        user = user.update(userUpdate);

        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("ambosing_@naver.com");
        assertThat(user.getNickname()).isEqualTo("zziwon-n");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void 유효한_인증_코드로_계정을_활성화_할_수_있다() throws Exception {

        //given
        User user = User.builder()
                .id(1L)
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        //when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        //then

        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);

    }

    @Test
    void 잘못된_인증_코드로_계정을_활성화_시도하면_에러를_던진다() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .email("ambosing_@naver.com")
                .nickname("ambosing")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        //when
        //then
        assertThatThrownBy(() -> {
            user.certificate("aaaaaaaaa");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() throws Exception {
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
        user = user.login(new TestClockHolder(1678530673958L));
        //then

        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);

    }
}
