package hello.toy.word.controller;

import hello.toy.word.util.SqlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/sql")
public class SqlController {

    private final SqlUtils sqlUtils;

    @GetMapping
    public String index(Model model) throws IOException {
        log.info("GetMapping sql/index");
        model.addAttribute("sql", sqlUtils.read());
        return "sql/index";
    }

    @PostMapping
    public String save(@RequestParam String sql, Model model) throws IOException, ParserConfigurationException, SAXException {
        log.info("PostMapping sql/index : {}", sql);
        sqlUtils.write(sql);
        model.addAttribute("sql", sql);
        return "sql/index";
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public String test() {
//
//    }
}
