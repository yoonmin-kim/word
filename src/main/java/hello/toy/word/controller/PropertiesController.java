package hello.toy.word.controller;

import hello.toy.word.util.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/prop")
public class PropertiesController {

    @GetMapping
    public String index(Model model) {
        log.info("GetMapping prop/index");
        model.addAttribute("prop", PropertiesUtils.read());
        return "prop/index";
    }

    @PostMapping
    public String save(@RequestParam String prop, Model model) throws IOException {
        log.info("PostMapping prop/index : {}", prop);
        PropertiesUtils.write(prop);
        model.addAttribute("prop", PropertiesUtils.read());
        return "prop/index";
    }

}
