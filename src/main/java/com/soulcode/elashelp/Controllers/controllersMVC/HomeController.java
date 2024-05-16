package com.soulcode.elashelp.Controllers.controllersMVC;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/admin/home")
    public String dash() {
        return "admin/dashboard";
    }

    @GetMapping("/tecnico/home")
    public String homeTec() {
        return "tickets-tecnico";
    }

    @GetMapping("/usuario/home")
    public String homeUser() {
        return "usuario/tickets";
    }





}