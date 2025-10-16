package me.gabriel.requisicaodecompra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import me.gabriel.requisicaodecompra.service.RequisicaoService;

@Controller
public class DashboardController {

    private final RequisicaoService requisicaoService;

    public DashboardController(RequisicaoService requisicaoService) {
        this.requisicaoService = requisicaoService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("stats", requisicaoService.getStats());
        return "inicio/dashboard";
    }
}
