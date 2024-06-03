package com.compstore.controller;

import com.compstore.controller.main.Main;
import com.compstore.model.Orderings;
import com.compstore.model.Powers;
import com.compstore.model.Powers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/powers") // отображение запросов на методы контроллера
public class PowersCont extends Main {
    @GetMapping
    public String powers(Model model) {
        AddAttributes(model);
        model.addAttribute("powers", powersRepo.findAll());
        return "powers";
    }

    @GetMapping("/filter")
    public String powersFilter(Model model, @RequestParam String name, @RequestParam int sort) {
        AddAttributes(model);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        List<Powers> powers = powersRepo.findAllByNameContaining(name);

        switch (sort) {
            case 1 -> powers.sort(Comparator.comparing(Powers::getName));
            case 2 -> {
                powers.sort(Comparator.comparing(Powers::getName));
                Collections.reverse(powers);
            }
            case 3 -> powers.sort(Comparator.comparing(Powers::getPrice));
            case 4 -> {
                powers.sort(Comparator.comparing(Powers::getPrice));
                Collections.reverse(powers);
            }
        }

        model.addAttribute("powers", powers);
        return "powers";
    }
    
    @GetMapping("/add")
    public String powerAdd(Model model) {
        AddAttributes(model);
        return "power_add";
    }

    @GetMapping("/{id}/edit")
    public String powerEdit(Model model, @PathVariable Long id) {
        AddAttributes(model);
        model.addAttribute("power", powersRepo.getReferenceById(id));
        return "power_edit";
    }

    @GetMapping("/{id}/delete")
    public String powerDelete(@PathVariable Long id) {
        powersRepo.deleteById(id);
        return "redirect:/powers";
    }

    @GetMapping("/{id}/buy")
    public String powerBuy(@PathVariable Long id) {
        orderingsRepo.save(new Orderings(getUser(), powersRepo.getReferenceById(id)));
        return "redirect:/powers";
    }

    @PostMapping("/add")
    public String powerAdd(Model model, @RequestParam String name, @RequestParam String description, @RequestParam int power, @RequestParam String appointment, @RequestParam String supply, @RequestParam int kpd, @RequestParam int fans, @RequestParam float price, @RequestParam MultipartFile file) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "powers/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Некорректные данные!");
                AddAttributes(model);
                return "power_add";
            }
        }

        powersRepo.save(new Powers(name, res, description, power, appointment, supply, kpd, fans, price));

        return "redirect:/powers";
    }

    @PostMapping("/{id}/edit")
    public String powerEdit(Model model, @RequestParam String name, @RequestParam String description, @RequestParam int power, @RequestParam String appointment, @RequestParam String supply, @RequestParam int kpd, @RequestParam int fans, @RequestParam float price, @RequestParam MultipartFile file, @PathVariable Long id) {
        Powers powers = powersRepo.getReferenceById(id);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "powers/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
                powers.setPhoto(res);
            } catch (IOException e) {
                model.addAttribute("message", "Некорректные данные!");
                AddAttributes(model);
                model.addAttribute("power", powersRepo.getReferenceById(id));
                return "power_edit";
            }
        }

        powers.set(name, description, power, appointment, supply, kpd, fans, price);

        powersRepo.save(powers);

        return "redirect:/powers";
    }
}
