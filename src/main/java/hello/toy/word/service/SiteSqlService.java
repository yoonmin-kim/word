package hello.toy.word.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.toy.word.controller.exception.AlreadyUsedSqlException;
import hello.toy.word.domain.SiteSql;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.domain.dto.SiteSqlDto;
import hello.toy.word.domain.dto.SqlDto;
import hello.toy.word.domain.mapper.ObjectMapper;
import hello.toy.word.repository.SiteSqlRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SiteSqlService {

	private final SiteSqlRepository siteSqlRepository;
	private final ObjectMapper objectMapper;
	private final MessageSource messageSource;

	public SiteSql save(SiteSqlDto siteSqlDto) {
		return siteSqlRepository.save(objectMapper.toSiteSql(siteSqlDto));
	}

	public void delete(SiteSqlDto siteSqlDto) {
		SiteSql siteSql = siteSqlRepository.findBySiteIdAndSqlId(siteSqlDto.getSite().getSiteId(),
			siteSqlDto.getSql().getSqlId());

		siteSqlRepository.delete(siteSql);
	}

	public int deleteBySiteId(Long siteId) {
		return siteSqlRepository.deleteBySiteId(siteId);
	}

	public List<SqlDto> findSqlBySiteId(Long siteId) {
		List<SiteSql> siteSqlList = siteSqlRepository.findSqlBySiteId(siteId);
		List<SqlDto> sqlDtoList = new ArrayList<>();
		siteSqlList.forEach(sql -> sqlDtoList.add(objectMapper.toSqlDto(sql.getSql())));
		return sqlDtoList;
	}

	public void validateAlreadyUsedSql(SiteDto siteDto, SqlDto sqlDto) throws AlreadyUsedSqlException {
		List<SqlDto> findSqlDtoList = findSqlBySiteId(siteDto.getSiteId());
		List<SqlDto> alreadyUsedSqlList = findSqlDtoList.stream()
			.filter(findSqlDto -> sqlDto.getSqlId().equals(findSqlDto.getSqlId()))
			.collect(Collectors.toList());

		if (alreadyUsedSqlList.size() > 0) {
			throw new AlreadyUsedSqlException(messageSource.getMessage("error.sitesql.save", null, null));
		}
	}
}
