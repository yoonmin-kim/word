package hello.toy.word.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.toy.word.controller.exception.AlreadyUsedSqlException;
import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.domain.dto.SiteSqlDto;
import hello.toy.word.domain.dto.SqlDto;
import hello.toy.word.service.SiteSqlService;
import hello.toy.word.service.SqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("siteSql")
@Controller
public class SiteSqlController {

	private final SiteSqlService siteSqlService;
	private final SqlService sqlService;

	@ExceptionHandler(AlreadyUsedSqlException.class)
	public String errorHandler(AlreadyUsedSqlException ex, HttpServletRequest request, Model model) {
		Map<String, String> msg = new HashMap<>();
		msg.put("globalMsg", ex.getMessage());
		model.addAttribute("msg", msg);
		ex.printStackTrace();
		log.info(ex.getMessage());
		String siteId = request.getParameter("siteId");
		return index(model, Long.parseLong(siteId));
	}

	@GetMapping("/index/{id}")
	public String index(Model model, @PathVariable("id") Long siteId) {
		model.addAttribute("siteId", siteId);
		model.addAttribute("sqlSiteList", siteSqlService.findSqlBySiteId(siteId));
		model.addAttribute("sqlList", sqlService.findAll());
		return "sitesql/index";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute SiteDto siteDto, @ModelAttribute SqlDto sqlDto) throws AlreadyUsedSqlException {

		siteSqlService.validateAlreadyUsedSql(siteDto, sqlDto);

		SiteSqlDto siteSqlDto = new SiteSqlDto();
		siteSqlDto.setSite(siteDto);
		siteSqlDto.setSql(sqlDto);

		siteSqlService.save(siteSqlDto);
		return "redirect:/siteSql/index/" + siteDto.getSiteId();
	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute SiteDto siteDto, @ModelAttribute SqlDto sqlDto) {
		SiteSqlDto siteSqlDto = new SiteSqlDto();
		siteSqlDto.setSite(siteDto);
		siteSqlDto.setSql(sqlDto);

		siteSqlService.delete(siteSqlDto);
		return "redirect:/siteSql/index/" + siteDto.getSiteId();
	}
}
