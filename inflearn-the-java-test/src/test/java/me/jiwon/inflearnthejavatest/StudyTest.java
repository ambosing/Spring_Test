package me.jiwon.inflearnthejavatest;

import me.jiwon.inflearnthejavatest.customannotation.FastTest;
import me.jiwon.inflearnthejavatest.customannotation.SlowTest;
import me.jiwon.inflearnthejavatest.domain.Study;
import me.jiwon.inflearnthejavatest.domain.StudyStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

//@ExtendWith(FindSlowTestExtension.class) // 확장팩 : 선언적인 등록 방법
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스를 클래스 당 하나만 만듬
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //어노테이션으로 순서를 정함
class StudyTest {
    int value = 0;

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L); // 확장팩 : 프로그래밍 등록 방법

    @Test
    @Order(1)
    @DisplayName("스터디 만들기")
    void create() {
        System.out.println(value++);
        //given
        Study study = new Study(10);
        //when
        //then
        assertNotNull(study);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.")
        );
    }

    @Test
    @Order(2)
    @DisplayName("limit에 음수를 넣으면 에러가 발생한다")
    void limit에_음수를_넣으면_에러가_발생한다() {
        //given
        System.out.println(value++);
        //when
        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
    }

    @Test
    @DisplayName("테스트 조건")
    @EnabledOnOs(OS.MAC)
    void 테스트_조건() {
        String testEnv = System.getenv("TEST_ENV");
        System.out.println(testEnv);
        assumeTrue("LOCAL".equalsIgnoreCase(testEnv));
    }

    // 태그를 선택해서 특정 태그만 테스트를 실행시킬 수 있음
    @Test
    @DisplayName("fastTag")
    @Tag("fast")
    void fastTag() {
        System.out.println("StudyTest.fastTag");
    }

    @Test
    @DisplayName("slowTag")
    @Tag("slow")
    void slowTag() {
        System.out.println("StudyTest.slowTag");
    }

    @FastTest
    @DisplayName("fastCustomTag")
    void fastCustomTag() {
        System.out.println("StudyTest.fastCustomTag");
    }

    @SlowTest
    @DisplayName("slowFastTag")
    void slowFastTag() {
        System.out.println("StudyTest.slowFastTag");
    }

    @Test
    @DisplayName("create1")
    void create1() {
        //given
        Study study = new Study(0);
        //when
        //then
        assertNotNull(study);
        System.out.println("create1");
    }

    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    @DisplayName("repeatTest")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("StudyTest.repeatTest : " + repetitionInfo.getCurrentRepetition() + " / " + repetitionInfo.getTotalRepetitions());
    }

    // 파라미터로 반복
    @DisplayName("parameterizedTest")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
    @NullAndEmptySource
    void parameterizedTest(String message) {
        System.out.println("message = " + message);
    }

    @DisplayName("parameterizedTest")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study) { // <- 이런 경우 converter 필요
        System.out.println("study.getLimit() = " + study);
    }

    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("parameterizedTest")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest3(Integer limit, String name) { // <- 이런 경우 converter 필요
        Study study = new Study(limit, name);
        System.out.println("study = " + study);
    }

    @DisplayName("parameterizedTest")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest4(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println("study = " + study);
    }

    @DisplayName("parameterizedTest")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest5(@AggregateWith(StudyAggregator.class) Study study) { // <- 이런 경우 converter 필요
        System.out.println("study = " + study);
    }

    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

    @Test
    @DisplayName("FindSlow 확장팩 테스트")
// 확장팩에 있는 Slow테스트를 붙이라고 경고가 출력 됨
    void FindSlow_확장팩_테스트() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println("StudyTest.FindSlow_확장팩_테스트");
    }

    @Test
    @DisplayName("FindSlow 확장팩 테스트")
    @SlowTest
        // SlowTest가 있는 경우에는 출력 안됨
    void FindSlow_확장팩_테스트2() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println("StudyTest.FindSlow_확장팩_테스트");
    }


    @BeforeAll

    static void beforeAll() {
        System.out.println("StudyTest.beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("StudyTest.afterAll");
    }

    @BeforeEach
    void setUp() {
        System.out.println("StudyTest.setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("StudyTest.tearDown");
    }
}