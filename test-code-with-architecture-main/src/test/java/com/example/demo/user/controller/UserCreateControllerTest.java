package com.example.demo.user.controller;

public class UserCreateControllerTest {
//
//    @Test
//    void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
//        //given
//        UserCreate userCreate = UserCreate.builder()
//                .email("zziwon@kakao.com")
//                .nickname("zziwon")
//                .address("Pangyo")
//                .build();
//        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));
//        //when
//        //then
//        mockMvc.perform(post("/api/users")
//                        .header("EMAIL", "ambosing_@naver.com")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userCreate)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath(("$.email")).value("zziwon@kakao.com"))
//                .andExpect(jsonPath(("$.nickname")).value("zziwon"))
//                .andExpect(jsonPath("$.status").value("PENDING"));
//    }
}
