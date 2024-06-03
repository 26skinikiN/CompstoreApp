package com.compstore.controller;

import com.compstore.controller.main.Main;
import com.compstore.model.Components;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller // определение класса в качестве контроллера, определение компонентов, которые будут обрабатывать запрос
public class IndexCont extends Main {

    @GetMapping // обработка запросов, передаваемое значение явл. частью url или адреса
    public String index1(Model model) {
        AddAttributes(model);
        List<Components> components = new ArrayList<>();
        components.addAll(motherboardsRepo.findAll());
        components.addAll(powersRepo.findAll());
        components.addAll(processorsRepo.findAll());
        components.sort(Comparator.comparing(Components::getOrderingDeliveredCount));
        Collections.reverse(components);
        List<Components> res = new ArrayList<>(); // ниже приведен механизм вывода компонентов на главную
        for (int i = 0; i < components.size(); i++) {
            if (i == 3) break;
            res.add(components.get(i));
        }
        model.addAttribute("components", res);
        return "main";
    }

    @GetMapping("/index")
    public String index2(Model model) {
        AddAttributes(model);
        List<Components> components = new ArrayList<>();
        components.addAll(motherboardsRepo.findAll());
        components.addAll(powersRepo.findAll());
        components.addAll(processorsRepo.findAll());
        components.sort(Comparator.comparing(Components::getOrderingDeliveredCount));
        Collections.reverse(components);
        List<Components> res = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            if (i == 3) break;
            res.add(components.get(i));
        }
        model.addAttribute("components", res);
        return "main";
    }
}
