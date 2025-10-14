package me.gabriel.requisicaodecompra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controllers {

    @GetMapping("/")
    public String dashboard() {
        return "inicio/dashboard";
    }

    @GetMapping("/requisicoes")
    public String requisicoes() {
        return "requisicoes/requisicoes";
    }

    @GetMapping("/nova-requisicao")
    public String novaRequisicao() {
        return "requisicoes/nova-requisicao";
    }

    @GetMapping("/detalhes-requisicao/")
    public String detalhesRequisicao() {
        return "requisicoes/detalhes-requisicao";
    }
}
