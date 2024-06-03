package com.compstore.controller;

import com.compstore.controller.main.Main;
import com.compstore.model.Motherboards;
import com.compstore.model.Orderings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/motherboards") // отображение запросов на методы контроллера
public class MotherboardsCont extends Main {
    @GetMapping
    public String motherboards(Model model) {
        AddAttributes(model);
        model.addAttribute("motherboards", motherboardsRepo.findAll());
        return "motherboards";
    }

    @GetMapping("/filter")
    public String motherboardsFilter(Model model, @RequestParam String name, @RequestParam int sort) {
        AddAttributes(model);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        List<Motherboards> motherboards = motherboardsRepo.findAllByNameContaining(name);

        switch (sort) {
            case 1 -> motherboards.sort(Comparator.comparing(Motherboards::getName));
            case 2 -> {
                motherboards.sort(Comparator.comparing(Motherboards::getName));
                Collections.reverse(motherboards);
            }
            case 3 -> motherboards.sort(Comparator.comparing(Motherboards::getPrice));
            case 4 -> {
                motherboards.sort(Comparator.comparing(Motherboards::getPrice));
                Collections.reverse(motherboards);
            }
        }

        model.addAttribute("motherboards", motherboards);
        return "motherboards";
    }

    @GetMapping("/add")
    public String motherboardAdd(Model model) {
        AddAttributes(model);
        return "motherboard_add";
    }

    @GetMapping("/{id}/edit")
    public String motherboardEdit(Model model, @PathVariable Long id) {
        AddAttributes(model);
        model.addAttribute("motherboard", motherboardsRepo.getReferenceById(id));
        return "motherboard_edit";
    }

    @GetMapping("/{id}/delete")
    public String motherboardDelete(@PathVariable Long id) {
        motherboardsRepo.deleteById(id);
        return "redirect:/motherboards";
    }

    @GetMapping("/{id}/buy")
    public String motherboardBuy(@PathVariable Long id) {
        orderingsRepo.save(new Orderings(getUser(), motherboardsRepo.getReferenceById(id)));
        return "redirect:/motherboards";
    }

    @PostMapping("/add")
    public String motherboardAdd(Model model, @RequestParam String name, @RequestParam MultipartFile file, @RequestParam String description, @RequestParam String socket, @RequestParam String chipset, @RequestParam String phases, @RequestParam int memoryType, @RequestParam int memoryMax, @RequestParam float price) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "motherboards/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Некорректные данные!");
                AddAttributes(model);
                return "motherboard_add";
            }
        }

        motherboardsRepo.save(new Motherboards(name, res, description, socket, chipset, phases, memoryType, memoryMax, price));

        return "redirect:/motherboards";
    }

    @PostMapping("/{id}/edit")
    public String motherboardEdit(Model model, @RequestParam String name, @RequestParam MultipartFile file, @RequestParam String description, @RequestParam String socket, @RequestParam String chipset, @RequestParam String phases, @RequestParam int memoryType, @RequestParam int memoryMax, @RequestParam float price, @PathVariable Long id) {
        Motherboards motherboard = motherboardsRepo.getReferenceById(id);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "motherboards/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
                motherboard.setPhoto(res);
            } catch (IOException e) {
                model.addAttribute("message", "Некорректные данные!");
                AddAttributes(model);
                model.addAttribute("motherboard", motherboardsRepo.getReferenceById(id));
                return "motherboard_edit";
            }
        }

        motherboard.set(name, description, socket, chipset, phases, memoryType, memoryMax, price);

        motherboardsRepo.save(motherboard);

        return "redirect:/motherboards";
    }
}
