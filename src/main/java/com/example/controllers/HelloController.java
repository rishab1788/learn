package com.example.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class HelloController {

    @Get("/say_hello")
    public String sayHello() {
        return "Hello User";
    }

}
