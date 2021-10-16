package hello.toy.word.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hello.toy.word.service.WordService;
import hello.toy.word.util.properties.MessageUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;
    private final MessageUtils messageUtils;

	@ExceptionHandler
	public ModelAndView errorHandler(Exception e) {
		ModelAndView modelAndView = new ModelAndView("word/index");
		Map<String, String> msg = new HashMap<>();
		msg.put("globalMsg", e.getMessage());
		e.printStackTrace();
		log.error(e.toString());
		modelAndView.addObject("msg", msg);
		return modelAndView;
	}


    @GetMapping
    public String index() {
        log.info("GetMapping word/index");
        return "word/index";
    }

    @PostMapping
    public String save(MultipartFile file, Model model) throws IOException, ClassNotFoundException, SQLException {
        //검증 오류 결과를 보관
        Map<String, String> msg = new HashMap<>();
        if (!wordService.validateExtension(file)) {
			throw new IllegalArgumentException(messageUtils.getProperty("error.word.save"));
		}

		wordService.makeReport(file);

        msg.put("globalMsg", messageUtils.getProperty("success.word.save"));
        model.addAttribute("msg", msg);
        return "word/index";
    }

}
