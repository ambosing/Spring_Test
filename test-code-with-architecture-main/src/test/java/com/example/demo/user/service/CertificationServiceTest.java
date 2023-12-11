package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() throws Exception {
        //given
        FakeMailSender fakemailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakemailSender);
        //when
        certificationService.send("ambosing_@naver.com", 1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        //then
        assertThat(fakemailSender.email).isEqualTo("ambosing_@naver.com");
        assertThat(fakemailSender.title).isEqualTo("Please certify your email address");
        assertThat(fakemailSender.content).isEqualTo(
                "Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");


    }
}