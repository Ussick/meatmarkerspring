package ua.itea.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class MainController {

    @RequestMapping(value = ("/enter"), method = RequestMethod.GET)
    public String getMainView() {
        return "main";
    }
}
