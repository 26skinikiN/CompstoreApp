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
public class Motherboards implements Components {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String photo;
    private String description;
    private String socket;
    private String chipset;
    private String phases;
    private int memoryType;
    private int memoryMax;
    private float price;

    @OneToMany(mappedBy = "motherboard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orderings> orderings;

    public Motherboards(String name, String photo, String description, String socket, String chipset, String phases, int memoryType, int memoryMax, float price) {
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.socket = socket;
        this.chipset = chipset;
        this.phases = phases;
        this.memoryType = memoryType;
        this.memoryMax = memoryMax;
        this.price = price;
    }

    public void set(String name, String description, String socket, String chipset, String phases, int memoryType, int memoryMax, float price) {
        this.name = name;
        this.description = description;
        this.socket = socket;
        this.chipset = chipset;
        this.phases = phases;
        this.memoryType = memoryType;
        this.memoryMax = memoryMax;
        this.price = price;
    }

    @Override
    public List<String> getInfo() {
        List<String> res = new ArrayList<>();
        res.add("Сокет: " + socket);
        res.add("Чипсет: " + chipset);
        res.add("Фаз питания: " + phases);
        res.add("Тип памяти: DDR" + memoryType);
        res.add("Максимум памяти: " + memoryMax + " ГБ");
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