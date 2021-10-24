package hello.toy.word.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hello.toy.word.domain.dto.SiteDto;
import hello.toy.word.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

	private final HomeService homeService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("siteList", homeService.findAll());
		return "home/index";
	}

	@GetMapping("/save")
	public String saveIndex() {
		return "home/save";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute SiteDto siteDto) {
		homeService.save(siteDto);

		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		homeService.delete(id);
		return "redirect:/";
	}

}
