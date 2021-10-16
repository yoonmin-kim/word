package hello.toy.word.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.toy.word.domain.dto.DbsettingDto;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.service.DbsettingService;
import hello.toy.word.service.HomeService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/dbsetting")
@RequiredArgsConstructor
@Controller
public class DbsettingController {

	private final DbsettingService dbsettingService;
	private final HomeService homeService;

	@GetMapping("/save/{id}")
	public String index(Model model, @PathVariable("id") Long siteId) {
		DbsettingDto findDbsetting = dbsettingService.findBySiteId(siteId);
		model.addAttribute("dbsetting", findDbsetting);
		return "dbsetting/index";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute DbsettingDto dbsettingDto, @RequestParam("siteId") Long siteId) {
		if (isUpdate(dbsettingDto)) {
			update(dbsettingDto);
		}

		SiteDto findSiteDto = homeService.findById(siteId);
		dbsettingDto.putSiteDto(findSiteDto);
		dbsettingService.save(dbsettingDto);
		return "redirect:/";
	}

	public String update(@ModelAttribute DbsettingDto dbsettingDto) {
		dbsettingService.update(dbsettingDto);
		return "redirect:/";
	}

	private boolean isUpdate(DbsettingDto dbsettingDto) {
		return !"null".equals(String.valueOf(dbsettingDto.getId())) &&
			StringUtils.hasText(String.valueOf(dbsettingDto.getId()));
	}
}
