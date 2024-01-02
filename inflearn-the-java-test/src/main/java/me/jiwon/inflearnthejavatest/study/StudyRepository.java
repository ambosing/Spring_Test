package me.jiwon.inflearnthejavatest.study;


import me.jiwon.inflearnthejavatest.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
