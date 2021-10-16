package hello.toy.word.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import hello.toy.word.domain.Dbsetting;
import hello.toy.word.domain.Site;
import hello.toy.word.domain.Sql;
import hello.toy.word.domain.dto.DbsettingDto;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.domain.dto.SqlDto;

@Mapper
public interface ObjectMapper {

	ObjectMapper INSTANCE = Mappers.getMapper(ObjectMapper.class);

	@Mapping(source = "siteName", target = "name")
	Site toSite(SiteDto siteDto);

	@Mapping(source = "name", target = "siteName")
	SiteDto toSiteDto(Site site);

	@Mapping(source = "siteDto", target = "site")
	Dbsetting toDbsetting(DbsettingDto dbsettingDto);

	@Mapping(source = "site", target = "siteDto")
	DbsettingDto toDbsettingDto(Dbsetting dbsetting);

	Sql toSql(SqlDto sqlDto);
}
