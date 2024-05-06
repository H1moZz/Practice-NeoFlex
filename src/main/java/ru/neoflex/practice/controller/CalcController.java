package ru.neoflex.practice.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition (
        info = @Info(
                title = "Клиент серверное приложение калькулятора",
                version = "1.0",
                description = "Эта программа созданна для прохождения Производствнной практики в NeoFlex",
                contact = @Contact(
                        name = "Даниил",
                        email = "shabanovdan11l@mail.ru"
                )
        )
)

@RestController
public class CalcController {

    @GetMapping("/plus/{a}/{b}")
    public int Addition(@PathVariable int a, @PathVariable int b) {
        return a + b;
    }

    @GetMapping("/minus/{a}/{b}")
    public int Subtraction(@PathVariable int a, @PathVariable int b) {
        return a - b;
    }
}
