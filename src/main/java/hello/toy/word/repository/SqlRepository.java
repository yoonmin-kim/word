package hello.toy.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.toy.word.domain.Sql;

public interface SqlRepository extends JpaRepository<Sql, Long> {
}
