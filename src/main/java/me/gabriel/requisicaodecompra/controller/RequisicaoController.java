package me.gabriel.requisicaodecompra.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import me.gabriel.requisicaodecompra.enums.DepartamentoEnum;
import me.gabriel.requisicaodecompra.model.ItemModel;
import me.gabriel.requisicaodecompra.model.RequisicaoModel;
import me.gabriel.requisicaodecompra.service.RequisicaoService;

@Controller
public class RequisicaoController {

    private final RequisicaoService requisicaoService;

    public RequisicaoController(RequisicaoService requisicaoService) {
        this.requisicaoService = requisicaoService;
    }

    @GetMapping("/requisicoes")
    public String requisicoes(Model model) {
        model.addAttribute("requisicoes", requisicaoService.findAll());
        return "requisicoes/requisicoes";
    }

    @GetMapping("/nova-requisicao")
    public String novaRequisicao(Model model) {
        RequisicaoModel requisicao = new RequisicaoModel();
        // Inicializa com um item vazio para binding
        requisicao.getItens().add(new ItemModel());
        model.addAttribute("requisicao", requisicao);
        model.addAttribute("departamentos", DepartamentoEnum.values());
        return "requisicoes/nova-requisicao";
    }

    @PostMapping("/requisicoes")
    public String salvar(@ModelAttribute("requisicao") @Valid RequisicaoModel requisicao, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("departamentos", DepartamentoEnum.values());
            return "requisicoes/nova-requisicao";
        }
        List<ItemModel> itens = requisicao.getItens();
        if (itens != null) {
            itens.removeIf(i -> (i.getNome() == null || i.getNome().isBlank()));
            itens.forEach(i -> i.setRequisicao(requisicao));
        }
        
        if (authentication != null && authentication.getPrincipal() instanceof me.gabriel.requisicaodecompra.model.UsuarioModel usuario) {
            requisicao.setCriadoPor(usuario);
        }
        requisicaoService.salvarNova(requisicao);
        return "redirect:/requisicoes";
    }

    @PostMapping("/requisicoes/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        RequisicaoModel requisicao = requisicaoService.findById(id);
        if (requisicao != null) {
            requisicaoService.delete(requisicao);
        }
        return "redirect:/requisicoes";
    }

    @GetMapping("/requisicoes/{id}")
    public String detalhesRequisicao(@PathVariable Long id, Model model) {
        RequisicaoModel requisicao = requisicaoService.findById(id);
        model.addAttribute("requisicao", requisicao);
        return "requisicoes/detalhes-requisicao";
    }
}
