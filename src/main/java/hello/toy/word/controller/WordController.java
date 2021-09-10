package hello.toy.word.controller;

import hello.toy.word.service.WordService;
import hello.toy.word.util.WordUtils;
import hello.toy.word.util.properties.MessageUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;
    private final MessageUtils messageUtils;

    @GetMapping
    public String index() {
        log.info("GetMapping word/index");
        return "word/index";
    }

    @PostMapping
    public String save(@ModelAttribute FileSaveForm fileSaveForm, Model model) throws IOException, ClassNotFoundException {

        MultipartFile file = fileSaveForm.getFile();

        //검증 오류 결과를 보관
        Map<String, String> msg = new HashMap<>();
        if (!wordService.validateExtension(file)) {
            msg.put("globalMsg", messageUtils.getProperty("error.word.save"));
            model.addAttribute("msg", msg);
            return "word/index";
        }

        wordService.makeReport(file);
        msg.put("globalMsg", messageUtils.getProperty("success.word.save"));
        model.addAttribute("msg", msg);
        return "word/index";
    }

    @Data
    static class FileSaveForm {
        private MultipartFile file;
        private String filePath;
    }

}
