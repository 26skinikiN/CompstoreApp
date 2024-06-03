package com.compstore.controller;

import com.compstore.controller.main.Main;
import com.compstore.model.Orderings;
import com.compstore.model.enums.OrderingStatus;
import com.compstore.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orderings") // отображение запросов на методы контроллера
public class OrderingsCont extends Main {
    @GetMapping
    public String orderings(Model model) {
        AddAttributes(model);
        List<Orderings> orderings = new ArrayList<>();

        if (getUser().getRole() == Role.ADMIN) {
            orderings.addAll(orderingsRepo.findAllByStatus(OrderingStatus.WAITING));
            orderings.addAll(orderingsRepo.findAllByStatus(OrderingStatus.CONFIRMED));
        } else {
            orderings.addAll(orderingsRepo.findAllByStatusAndOwner_Id(OrderingStatus.REGISTRATION, getUser().getId()));
            orderings.addAll(orderingsRepo.findAllByStatusAndOwner_Id(OrderingStatus.WAITING, getUser().getId()));
            orderings.addAll(orderingsRepo.findAllByStatusAndOwner_Id(OrderingStatus.CONFIRMED, getUser().getId()));
            orderings.addAll(orderingsRepo.findAllByStatusAndOwner_Id(OrderingStatus.DELIVERED, getUser().getId()));
            orderings.addAll(orderingsRepo.findAllByStatusAndOwner_Id(OrderingStatus.REJECTED, getUser().getId()));
        }

        model.addAttribute("orderings", orderings);
        return "orderings";
    }

    @GetMapping("/{id}/wait")
    public String orderingWait(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.WAITING);
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }

    @GetMapping("/{id}/conf")
    public String orderingConf(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.CONFIRMED);
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }

    @GetMapping("/{id}/reject")
    public String orderingReject(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.REJECTED);
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }

    @GetMapping("/{id}/del")
    public String orderingDel(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.DELIVERED);
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }
}
