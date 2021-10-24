package hello.toy.word.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.toy.word.domain.dto.SqlDto;
import hello.toy.word.service.SqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sql")
@Controller
public class SqlController {

	private final SqlService sqlService;

	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("sqlList", sqlService.findAll());
		return "sql/index";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute SqlDto sqlDto) {
		sqlService.save(sqlDto);
		return "redirect:/sql/index";
	}
}
