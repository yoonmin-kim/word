package hello.toy.word.repository;

import hello.toy.word.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select r from Report r where r.targetWord = :targetWord")
    Report findByTargetWord(@Param("targetWord") String targetWord);
}
