package hello.toy.word.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hello.toy.word.controller.exception.WordSaveExtensionException;
import hello.toy.word.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {

	private final WordService wordService;
	private final MessageSource messageSource;

	@ExceptionHandler(WordSaveExtensionException.class)
	public ModelAndView errorHandler(WordSaveExtensionException ex, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("word/index");
		Map<String, String> msg = new HashMap<>();
		msg.put("globalMsg", ex.getMessage());
		ex.printStackTrace();
		log.info(ex.toString());
		modelAndView.addObject("siteId", request.getParameter("siteId"));
		modelAndView.addObject("msg", msg);
		return modelAndView;
	}

	@GetMapping("/index/{siteId}")
	public String index(@PathVariable("siteId") String siteId, Model model) {
		log.info("GetMapping word/index");
		model.addAttribute("siteId", siteId);
		return "word/index";
	}

	@PostMapping("/save")
	public String save(MultipartFile file, @RequestParam Long siteId, Model model) throws
		IOException, ClassNotFoundException, SQLException, WordSaveExtensionException {

		//검증 오류 결과를 보관
		Map<String, String> msg = new HashMap<>();
		wordService.validateSave(file);
		wordService.makeReport(file, siteId);

		msg.put("globalMsg", messageSource.getMessage("success.word.save", null, null));
		model.addAttribute("msg", msg);
		model.addAttribute("siteId", siteId);
		return "word/index";
	}

}
