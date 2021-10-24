package hello.toy.word.init;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import hello.toy.word.domain.Site;
import hello.toy.word.domain.Sql;
import hello.toy.word.domain.dto.DbsettingDto;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.domain.dto.SiteSqlDto;
import hello.toy.word.domain.dto.SqlDto;
import hello.toy.word.service.DbsettingService;
import hello.toy.word.service.HomeService;
import hello.toy.word.service.SiteSqlService;
import hello.toy.word.service.SqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitialize {

	private final HomeService homeService;
	private final SqlService sqlService;
	private final SiteSqlService siteSqlService;
	private final DbsettingService dbsettingService;

	@PostConstruct
	public void init() {
		log.info("<== DataInitialize START ==>");

		// 사이트 추가
		SiteDto siteDto = new SiteDto();
		siteDto.setSiteName("test1");
		Site site = homeService.save(siteDto);
		siteDto.setSiteId(site.getId());

		// SQL 추가
		SqlDto sqlDto = new SqlDto();
		sqlDto.setTargetName("test");
		sqlDto.setQuery("select count(*) test from t_acl_user");
		Sql sql = sqlService.save(sqlDto);
		sqlDto.setSqlId(sql.getId());

		// DB 설정
		DbsettingDto dbsettingDto = new DbsettingDto();
		dbsettingDto.setClassForName("com.mysql.cj.jdbc.Driver");
		dbsettingDto.setUrl("jdbc:mysql://localhost/mydb?serverTimezone=UTC");
		dbsettingDto.setUser("root");
		dbsettingDto.setPassword("1234");
		dbsettingDto.setSiteDto(siteDto);
		dbsettingService.save(dbsettingDto);

		// SQL 설정
		SiteSqlDto siteSqlDto = new SiteSqlDto();
		siteSqlDto.setSite(siteDto);
		siteSqlDto.setSql(sqlDto);
		siteSqlService.save(siteSqlDto);

		log.info("<== DataInitialize END ==>");
	}
}
