package hello.toy.word.controller;

import hello.toy.word.service.PropertiesService;
import hello.toy.word.util.PropertiesUtils;
import hello.toy.word.util.properties.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/prop")
public class PropertiesController {

    private final PropertiesService propertiesService;
    private final PropertiesUtils propertiesUtils;
    private final MessageUtils messageUtils;


    @GetMapping
    public String index(Model model) {
        log.info("GetMapping prop/index");
        model.addAttribute("props", propertiesUtils.read());
        return "prop/index";
    }

    @PostMapping
    public String save(@RequestParam String props, Model model) throws IOException {
        log.info("PostMapping prop/index : {}", props);

        boolean isSuccess = propertiesService.save(props);
        Map<String, String> errors = new HashMap<>();
        if(isSuccess){
            model.addAttribute("props", propertiesUtils.read());
        } else {
            errors.put("globalError", messageUtils.getProperty("error.properties.save"));
            model.addAttribute("props", props);
            model.addAttribute("errors", errors);
        }
        return "prop/index";
    }

}
