package com.compstore.controller;

import com.compstore.controller.main.Main;
import com.compstore.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile") // отображение запросов на методы контроллера
public class ProfileCont extends Main {
    @GetMapping
    public String profile(Model model) {
        AddAttributes(model);
        return "profile";
    }

    @PostMapping
    public String profile(@RequestParam String fio) {
        Users user = getUser();
        user.setFio(fio);
        usersRepo.save(user);
        return "redirect:/profile";
    }
}
