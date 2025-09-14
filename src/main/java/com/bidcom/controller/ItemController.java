
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidcom.controller;

/**
 *
 * @author Ramiro
 */

import com.bidcom.model.Item;
import com.bidcom.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public String listarClientes(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("clientes", items);
        return "clientes";
    }
}
