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
public class Orderings {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Processors processor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Motherboards motherboard;
    @ManyToOne(fetch = FetchType.LAZY)
    private Powers power;

    public Orderings(Users owner, Processors processor) {
        this.status = OrderingStatus.REGISTRATION;
        this.owner = owner;
        this.processor = processor;
    }

    public Orderings(Users owner, Motherboards motherboard) {
        this.status = OrderingStatus.REGISTRATION;
        this.owner = owner;
        this.motherboard = motherboard;
    }

    public Orderings(Users owner, Powers power) {
        this.status = OrderingStatus.REGISTRATION;
        this.owner = owner;
        this.power = power;
    }

    public String getName() {
        if (processor != null) return processor.getName();
        if (motherboard != null) return motherboard.getName();
        if (power != null) return power.getName();
        return "";
    }

    public String getDescription() {
        if (processor != null) return processor.getDescription();
        if (motherboard != null) return motherboard.getDescription();
        if (power != null) return power.getDescription();
        return "";
    }

    public String getPhoto() {
        if (processor != null) return processor.getPhoto();
        if (motherboard != null) return motherboard.getPhoto();
        if (power != null) return power.getPhoto();
        return "";
    }

    public List<String> getInfo() {
        if (processor != null) return processor.getInfo();
        if (motherboard != null) return motherboard.getInfo();
        if (power != null) return power.getInfo();
        return new ArrayList<>();
    }
}