package com.compstore.controller;

import com.compstore.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login") // отображение запросов на методы контроллера
public class LoginCont extends Main {

    @GetMapping
    public String login(Model model) {
        AddAttributes(model);
        return "login";
    }
}
