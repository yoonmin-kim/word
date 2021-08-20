package hello.toy.word.controller;

import hello.toy.word.util.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/sql")
public class SqlController {

    @GetMapping
    public String index(Model model) throws IOException {
        log.info("GetMapping sql/index");
        model.addAttribute("sql", SqlUtils.read());
        return "sql/index";
    }

    @PostMapping
    public String save(@RequestParam String sql, Model model) throws IOException, ParserConfigurationException, SAXException {
        log.info("PostMapping sql/index : {}", sql);
        SqlUtils.write(sql);
        model.addAttribute("sql", sql);
        return "sql/index";
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public String test() {
//
//    }
}
