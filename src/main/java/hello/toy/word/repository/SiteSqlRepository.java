package hello.toy.word.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hello.toy.word.domain.SiteSql;

public interface SiteSqlRepository extends JpaRepository<SiteSql, Long> {

	@Query(value = "select ss from SiteSql ss join fetch ss.sql where ss.site.id = :siteId")
	List<SiteSql> findSqlBySiteId(@Param("siteId") Long siteId);

	@Query(value = "select ss from SiteSql ss where ss.site.id = :siteId and ss.sql.id = :sqlId")
	SiteSql findBySiteIdAndSqlId(@Param("siteId") Long siteId, @Param("sqlId") Long sqlId);

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from SiteSql ss where ss.site.id = :siteId")
	int deleteBySiteId(@Param("siteId") Long siteId);
}
