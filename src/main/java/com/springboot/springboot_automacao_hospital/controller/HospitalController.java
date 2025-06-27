package com.springboot.springboot_automacao_hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HospitalController {

    @GetMapping("/inicio")
    public String formMenu() {
        return "menu-hospital";
    }

}
