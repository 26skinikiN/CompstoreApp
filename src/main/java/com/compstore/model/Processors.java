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
@NoArgsConstructor // конструктор без аргументов
@Entity // сущность, кот. будет отображена в БД
public class Processors implements Components {
    @Setter(AccessLevel.NONE) // генерация метода сеттера но ур. доступа NONE указывпет что сеттер не будет сгенерирован
    @Id // аннотация устанавливает первичный ключ сущности
    @GeneratedValue(strategy = GenerationType.AUTO) // автоматическая генерация первичного ключа
    @Column(nullable= false, updatable = false) // отображение поля на столбец БД, обязателен для заполнения и не может быть нулевым
    private Long id;

    private String name;
    private String photo;
    private String description;
    private int cores;
    private float frequencyMain;
    private float frequencyMax;
    private float cacheL2;
    private float cacheL3;
    private int technicalProcess;
    private int tdp;
    private float price;

    @OneToMany(mappedBy = "processor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orderings> orderings;
    // отношение один ко многим между сущностями
    // все операции изменения сущности должны быть распространены на связ. сущность
    // указание способа извлечения связанных объектов при доступе к ним, lazy - данные будут загружены при первом обращении к ним
    public Processors(String name, String description, int cores, float frequencyMain, float frequencyMax, float cacheL2, float cacheL3, int technicalProcess, int tdp, float price, String photo) {
        this.name = name;
        this.description = description;
        this.cores = cores;
        this.frequencyMain = frequencyMain;
        this.frequencyMax = frequencyMax;
        this.cacheL2 = cacheL2;
        this.cacheL3 = cacheL3;
        this.technicalProcess = technicalProcess;
        this.tdp = tdp;
        this.price = price;
        this.photo = photo;
    }

    public void set(String name, String description, int cores, float frequencyMain, float frequencyMax, float cacheL2, float cacheL3, int technicalProcess, int tdp, float price) {
        this.name = name;
        this.description = description;
        this.cores = cores;
        this.frequencyMain = frequencyMain;
        this.frequencyMax = frequencyMax;
        this.cacheL2 = cacheL2;
        this.cacheL3 = cacheL3;
        this.technicalProcess = technicalProcess;
        this.tdp = tdp;
        this.price = price;
    }

    @Override
    public List<String> getInfo() {
        List<String> res = new ArrayList<>();
        res.add("Количество ядер: " + cores);
        res.add("Тех. процесс: " + technicalProcess + " нм");
        res.add("Базовая частота: " + frequencyMain + " ГГц");
        res.add("Максимальная частота: " + frequencyMax + " ГГц");
        res.add("Кэш 2 уровня: " + cacheL2 + " МБ");
        res.add("Кэш 3 уровня: " + cacheL3 + " МБ");
        res.add("TDP: " + tdp + " Вт");
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