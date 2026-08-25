package com.blibliored.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class Controller {

        @GetMapping
        public String prueba() {
            return "Funciona";
        }
}
