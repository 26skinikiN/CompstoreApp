package com.compstore.model;

import com.compstore.model.enums.OrderingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Powers implements Components {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String photo;
    private String description;
    private int power;
    private String appointment;
    private String supply;
    private int kpd;
    private int fans;
    private float price;

    @OneToMany(mappedBy = "power", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orderings> orderings;

    public Powers(String name, String photo, String description, int power, String appointment, String supply, int kpd, int fans, float price) {
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.power = power;
        this.appointment = appointment;
        this.supply = supply;
        this.kpd = kpd;
        this.fans = fans;
        this.price = price;
    }

    public void set(String name, String description, int power, String appointment, String supply, int kpd, int fans, float price) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.appointment = appointment;
        this.supply = supply;
        this.kpd = kpd;
        this.fans = fans;
        this.price = price;
    }

    @Override
    public List<String> getInfo() {
        List<String> res = new ArrayList<>();
        res.add("Мощность: " + power + " Вт");
        res.add("Назначение: " + appointment);
        res.add("Питание: " + supply);
        res.add("КПД: " + kpd + "%");
        res.add("Количество вентиляторов: " + fans);
        res.add("Цена: " + price + " р");
        return res;
    }

    @Override
    public int getOrderingDeliveredCount() {
        return orderings.stream().reduce(0, (i, ordering) -> {
            if (ordering.getStatus() == OrderingStatus.DELIVERED) return i + 1;
            return i;
        }, Integer::sum);
    }
}