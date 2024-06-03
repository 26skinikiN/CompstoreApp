package com.compstore.controller;

import com.compstore.controller.main.Main;
import com.compstore.model.Orderings;
import com.compstore.model.Processors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/processors")
public class ProcessorsCont extends Main {
    @GetMapping
    public String processors(Model model) {
        AddAttributes(model);
        model.addAttribute("processors", processorsRepo.findAll());
        return "processors";
    }

    @GetMapping("/filter")
    public String processorsFilter(Model model, @RequestParam String name, @RequestParam int sort) { // извлечение
        AddAttributes(model);                                                                       // параметров запроса
        model.addAttribute("name", name);                                              // в методе обработчике
        model.addAttribute("sort", sort);
        List<Processors> processors = processorsRepo.findAllByNameContaining(name);

        switch (sort) {
            case 1 -> processors.sort(Comparator.comparing(Processors::getName));
            case 2 -> {
                processors.sort(Comparator.comparing(Processors::getName));
                Collections.reverse(processors);
            }
            case 3 -> processors.sort(Comparator.comparing(Processors::getPrice));
            case 4 -> {
                processors.sort(Comparator.comparing(Processors::getPrice));
                Collections.reverse(processors);
            }
        }

        model.addAttribute("processors", processors);
        return "processors";
    }


    @GetMapping("/add")
    public String processorAdd(Model model) {
        AddAttributes(model);
        return "processor_add";
    }

    @GetMapping("/{id}/edit")
    public String processorEdit(Model model, @PathVariable Long id) {
        AddAttributes(model);
        model.addAttribute("processor", processorsRepo.getReferenceById(id));
        return "processor_edit";
    }

    @GetMapping("/{id}/delete")
    public String processorDelete(@PathVariable Long id) { // связывание значений из url с параметрами метода
        processorsRepo.deleteById(id);
        return "redirect:/processors";
    }

    @GetMapping("/{id}/buy")
    public String processorBuy(@PathVariable Long id) { // связывание значений из url с параметрами метода
        orderingsRepo.save(new Orderings(getUser(), processorsRepo.getReferenceById(id)));
        return "redirect:/processors";
    }

    @PostMapping("/add")
    public String processorAdd(Model model, @RequestParam String name, @RequestParam String description, @RequestParam int cores, @RequestParam float frequencyMain, @RequestParam float frequencyMax, @RequestParam float cacheL2, @RequestParam float cacheL3, @RequestParam int technicalProcess, @RequestParam int tdp, @RequestParam float price, @RequestParam MultipartFile file) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "processors/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Некорректные данные!");
                AddAttributes(model);
                return "processor_add";
            }
        }

        processorsRepo.save(new Processors(name, description, cores, frequencyMain, frequencyMax, cacheL2, cacheL3, technicalProcess, tdp, price, res));

        return "redirect:/processors";
    }

    @PostMapping("/{id}/edit")
    public String processorEdit(Model model, @RequestParam String name, @RequestParam String description, @RequestParam int cores, @RequestParam float frequencyMain, @RequestParam float frequencyMax, @RequestParam float cacheL2, @RequestParam float cacheL3, @RequestParam int technicalProcess, @RequestParam int tdp, @RequestParam float price, @RequestParam MultipartFile file, @PathVariable Long id) {
        Processors processor = processorsRepo.getReferenceById(id);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "processors/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
                processor.setPhoto(res);
            } catch (IOException e) {
                model.addAttribute("message", "Некорректные данные!");
                AddAttributes(model);
                model.addAttribute("processor", processorsRepo.getReferenceById(id));
                return "processor_edit";
            }
        }

        processor.set(name, description, cores, frequencyMain, frequencyMax, cacheL2, cacheL3, technicalProcess, tdp, price);

        processorsRepo.save(processor);

        return "redirect:/processors";
    }
}
