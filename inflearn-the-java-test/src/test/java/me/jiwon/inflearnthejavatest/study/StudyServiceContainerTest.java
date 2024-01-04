package me.jiwon.inflearnthejavatest.study;

import lombok.extern.slf4j.Slf4j;
import me.jiwon.inflearnthejavatest.domain.Study;
import me.jiwon.inflearnthejavatest.domain.StudyStatus;
import me.jiwon.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@Slf4j
@ContextConfiguration(initializers = StudyServiceContainerTest.ContainerPropertyInitializer.class)
public class StudyServiceContainerTest {

    @Mock
    MemberService memberService;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    Environment environment;
    @Value("${container.port}")
    int port;

    //    @Container
//    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
//            .withDatabaseName("studytest");
    @Container
    static GenericContainer genericContainer = new GenericContainer("postgres")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "studytest")
            .withEnv("POSTGRES_PASSWORD", "STUDY");
//            .waitingFor(Wait.forHttp("/hello")) // 특정한 어플리케이션 동작할 때까지 기다림


    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        genericContainer.followOutput(logConsumer);
    }

    @BeforeEach
    void setUp() {
        System.out.println("==================");
        System.out.println(environment.getProperty("container.port"));
        System.out.println(port);
//        System.out.println(genericContainer.getMappedPort(5432));
//        System.out.println(genericContainer.getLogs()); // 컨테이너 안의 Log 정보들을 출력
        studyRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("스터디 만들기")
    void create() {
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

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of("container.port=" + genericContainer.getMappedPort(5432))
                    .applyTo(context.getEnvironment());
        }
    }
}
