package hello.toy.word.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import hello.toy.word.domain.Dbsetting;
import hello.toy.word.domain.Site;
import hello.toy.word.domain.SiteSql;
import hello.toy.word.domain.Sql;
import hello.toy.word.domain.dto.DbsettingDto;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.domain.dto.SiteSqlDto;
import hello.toy.word.domain.dto.SqlDto;

@Mapper
public interface ObjectMapper {

	@Mappings(value = {@Mapping(source = "siteName", target = "name"),
		@Mapping(source = "siteId", target = "id")})
	Site toSite(SiteDto siteDto);

	@Mappings(value = {@Mapping(source = "name", target = "siteName"),
		@Mapping(source = "id", target = "siteId")})
	SiteDto toSiteDto(Site site);

	@Mapping(source = "siteDto", target = "site")
	Dbsetting toDbsetting(DbsettingDto dbsettingDto);

	@Mapping(source = "site", target = "siteDto")
	DbsettingDto toDbsettingDto(Dbsetting dbsetting);

	@Mapping(source = "sqlId", target = "id")
	Sql toSql(SqlDto sqlDto);

	@Mapping(source = "id", target = "sqlId")
	SqlDto toSqlDto(Sql sql);

	SiteSql toSiteSql(SiteSqlDto siteSqlDto);
}
