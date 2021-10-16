package hello.toy.word.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.toy.word.domain.Dbsetting;
import hello.toy.word.domain.Site;
import hello.toy.word.domain.dto.DbsettingDto;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.domain.mapper.ObjectMapper;
import hello.toy.word.repository.DbsettingRepository;
import hello.toy.word.repository.HomeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DbsettingService {

	private final DbsettingRepository dbsettingRepository;
	private final HomeRepository homeRepository;

	@Transactional
	public Dbsetting save(DbsettingDto dbsettingDto) {
		return dbsettingRepository.save(
			ObjectMapper.INSTANCE.toDbsetting(dbsettingDto)
		);
	}

	public DbsettingDto findBySiteId(Long siteId) {
		Optional<Dbsetting> findDbSetting = dbsettingRepository.findBySiteId(siteId);
		return findDbSetting.map(dbsetting -> ObjectMapper.INSTANCE.toDbsettingDto(dbsetting))
			.orElse(defaultDbsetting(siteId));

	}

	private DbsettingDto defaultDbsetting(Long siteId) {
		Optional<Site> findSite = homeRepository.findById(siteId);
		DbsettingDto dbsettingDto = new DbsettingDto();
		dbsettingDto.putSiteDto(
			findSite.map(site -> ObjectMapper.INSTANCE.toSiteDto(site))
				.orElse(new SiteDto())
		);
		return dbsettingDto;
	}

	@Transactional
	public void update(DbsettingDto dbsettingDto) {
		Optional<Dbsetting> findDbSetting = dbsettingRepository.findById(dbsettingDto.getId());
		findDbSetting.orElse(null).updateDbSetting(dbsettingDto);
	}
}
