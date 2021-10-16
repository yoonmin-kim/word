package hello.toy.word.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.toy.word.domain.Site;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.domain.mapper.ObjectMapper;
import hello.toy.word.repository.HomeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class HomeService {

	private final HomeRepository homeRepository;

	public Site save(SiteDto siteDto) {
		Site site = ObjectMapper.INSTANCE.toSite(siteDto);
		return homeRepository.save(site);
	}

	public SiteDto findById(Long id) {
		Optional<Site> findSite = homeRepository.findById(id);
		return ObjectMapper.INSTANCE.toSiteDto(findSite.orElse(new Site()));
	}

	public List<SiteDto> findAll() {
		List<Site> siteList = homeRepository.findAll();
		List<SiteDto> siteDtoList = new ArrayList<>();
		for (Site site : siteList) {
			siteDtoList.add(ObjectMapper.INSTANCE.toSiteDto(site));
		}
		return siteDtoList;
	}

	public void delete(Long id) {
		homeRepository.deleteById(id);
	}
}
