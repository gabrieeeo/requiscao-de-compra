package me.gabriel.requisicaodecompra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controllers {

    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/requisicoes")
    public String requisicoes() {
        return "requisicoes";
    }

    @GetMapping("/nova-requisicao")
    public String novaRequisicao() {
        return "nova-requisicao";
    }

    @GetMapping("/detalhes-requisicao/")
    public String detalhesRequisicao() {
        return "detalhes-requisicao";
    }
}
