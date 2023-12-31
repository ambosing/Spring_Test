package com.example.demo.medium;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.UserCreate;
import com.example.demo.user.service.UserServiceImpl;
import com.example.demo.user.service.UserUpdate;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private JavaMailSender mailSender;


    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() throws Exception {
        //given
        String email = "ambosing_@naver.com";

        //when
        User result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("ambosing");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저를_찾아올_수_없다() throws Exception {
        //given
        String email = "ambosing7@naver.com";

        //when
        //then
        assertThatThrownBy(() -> userService.getByEmail(email))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() throws Exception {
        //given
        //when
        User result = userService.getById(1);

        //then
        assertThat(result.getNickname()).isEqualTo("ambosing");
    }

    @Test
    void getById는_PENDING_상태인_유저를_찾아올_수_없다() throws Exception {
        //given
        //when
        //then
        assertThatThrownBy(() -> userService.getById(2))
                .isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("ambosing_@naver.com")
                .address("Gyeongi")
                .nickname("ambosing")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        //when
        User result = userService.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
//        assertThat(result.getCertificationCode()).isEqualTo("T.T"); 지금은 테스트를 할 수 없어 .. UUID거든..
    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() throws Exception {
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("Incheon")
                .nickname("ambosingUpdated")
                .build();

        //when
        userService.update(1, userUpdate);

        //then
        User userEntity = userService.getById(1);
        assertThat(userEntity.getId()).isNotNull();
        assertThat(userEntity.getAddress()).isEqualTo("Incheon");
        assertThat(userEntity.getNickname()).isEqualTo("ambosingUpdated");
    }


    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() throws Exception {
        //given
        //when
        userService.login(1);

        //then
        User userEntity = userService.getById(1);
        assertThat(userEntity.getLastLoginAt()).isGreaterThan(0L);
        //        assertThat(result.getLastLoginAt()).isEqualTo("T.T"); // FIXME

    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() throws Exception {
        //given
        //when
        userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //then
        User userEntity = userService.getById(2);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() throws Exception {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}