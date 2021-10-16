package hello.toy.word.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hello.toy.word.domain.Dbsetting;

public interface DbsettingRepository extends JpaRepository<Dbsetting, Long> {

	@Query("select db from Dbsetting db where db.site.id = :siteId")
	Optional<Dbsetting> findBySiteId(@Param("siteId") Long siteId);
}
