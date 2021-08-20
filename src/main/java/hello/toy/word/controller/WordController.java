package hello.toy.word.controller;

import hello.toy.word.service.WordService;
import hello.toy.word.util.WordUtils;
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

    @GetMapping
    public String index() {
        log.info("GetMapping word/index");
        return "word/index";
    }

    @PostMapping
    public String save(@ModelAttribute FileSaveForm fileSaveForm, Model model) throws IOException, ClassNotFoundException {

        MultipartFile file = fileSaveForm.getFile();
        //String filePath = fileSaveForm.getFilePath();
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();
        if (!"docx".equals(extension)) {
            errors.put("globalMsg", "파일의 확장자가 docx 가 아닙니다.");
            model.addAttribute("errors", errors);
            return "word/index";
        }

        wordService.makeReport(file.getInputStream(), fileName);
        errors.put("globalMsg", "파일 저장이 성공적으로 완료되었습니다.");
        model.addAttribute("errors", errors);
        return "word/index";
    }

    @Data
    static class FileSaveForm {
        private MultipartFile file;
        private String filePath;
    }

}
