package com.bidcom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ramiro
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index"; // se conecta a index.html
    }
}
