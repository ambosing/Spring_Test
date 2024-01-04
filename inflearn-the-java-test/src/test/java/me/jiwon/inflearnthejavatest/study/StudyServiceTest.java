package me.jiwon.inflearnthejavatest.study;

import me.jiwon.inflearnthejavatest.domain.Member;
import me.jiwon.inflearnthejavatest.domain.Study;
import me.jiwon.inflearnthejavatest.domain.StudyStatus;
import me.jiwon.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {
    @Mock
    MemberService memberService; // 어노테이션을 쓸 때는 @ExtendWith(MockitoExtension.class) 추가해야 함

    @Mock
    StudyRepository studyRepository;


    @Test
    @DisplayName("모킹 써보기")
    void 모킹_써보기() {
        //given
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);
        //when
        StudyService studyService = new StudyService(memberService, studyRepository);
        //then
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("모킹 어노테이션 사용하기")
    void 모킹_어노테이션_사용하기() {
        //given

        //when
        StudyService studyService = new StudyService(memberService, studyRepository);

        //then
        assertNotNull(studyService);
    }


    @Test
    @DisplayName("모킹 파라미터로 사용하기")
    void 모킹_파라미터로_사용하기(@Mock MemberService memberService,
                       @Mock StudyRepository studyRepository) {
        //given

        //when
        StudyService studyService = new StudyService(memberService, studyRepository);

        //then
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("모킹으로 Stubbing 하기")
    void 모킹으로_Stubbing_하기(@Mock MemberService memberService,
                          @Mock StudyRepository studyRepository) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("ambosing@gmail.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("ambosing@gmail.com", byId.get().getEmail());

        Study study = new Study(10, "java");
        studyService.createNewStudy(1L, study);

        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });
    }

    @Test
    @DisplayName("호출마다 다른 Stubbing")
    void 호출마다_다른_Stubbing(@Mock MemberService memberService,
                          @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("ambosing@gmail.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("ambosing@gmail.com", byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    @DisplayName("Mock 객체 Stubbing 연습문제")
    void Mock_객체_Stubbing_연습문제(@Mock MemberService memberService,
                               @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("ambosing@gmail.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getName());
        assertEquals(member.getId(), study.getOwnerId());

        verify(memberService, times(1)).notify(study);
        verify(memberService, never()).validate(any());
    }

    @Test
    @DisplayName("호출 횟수 검증")
    void 호출_횟수_검증(@Mock MemberService memberService,
                  @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("ambosing@gmail.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        verify(memberService, times(1)).notify(study);
        verify(memberService, never()).validate(any());
    }


    @Test
    @DisplayName("호출 순서 검증")
    void 호출_순서_검증(@Mock MemberService memberService,
                  @Mock StudyRepository studyRepository) {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("ambosing@gmail.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        verifyNoMoreInteractions(memberService);
    }

    @Test
    @DisplayName("Stubbing과 Verifying 검증")
    void Stubbing과_Verifying_검증() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "테스트");
        when(studyRepository.save(any())).thenReturn(study);
        //when
        studyService.openStudy(study);

        //then
        assertEquals(study.getStatus(), StudyStatus.OPENED);
        assertNotNull(study.getOpenedDateTime());
        verify(memberService).notify(study);

    }
}
