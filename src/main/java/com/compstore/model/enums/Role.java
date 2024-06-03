package com.compstore.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor // генерация параметризованного конструктора, кот. принимает один параметр для каждого поля
@Getter // предоставление геттера для поля
public enum Role implements GrantedAuthority {
    ADMIN("Администратор"),
    USER("Пользователь"),
    ;

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}

