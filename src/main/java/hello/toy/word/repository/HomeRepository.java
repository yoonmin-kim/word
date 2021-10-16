package hello.toy.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.toy.word.domain.Site;

public interface HomeRepository extends JpaRepository<Site, Long> {
}
