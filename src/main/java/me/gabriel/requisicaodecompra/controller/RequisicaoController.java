package me.gabriel.requisicaodecompra.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import me.gabriel.requisicaodecompra.enums.StatusEnum;
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
    public String salvar(@ModelAttribute("requisicao") @Valid RequisicaoModel requisicao, BindingResult result,
            Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("departamentos", DepartamentoEnum.values());
            return "requisicoes/nova-requisicao";
        }
        List<ItemModel> itens = requisicao.getItens();
        if (itens != null) {
            itens.removeIf(i -> (i.getNome() == null || i.getNome().isBlank()));
            itens.forEach(i -> i.setRequisicao(requisicao));
        }

        if (authentication != null
                && authentication.getPrincipal() instanceof me.gabriel.requisicaodecompra.model.UsuarioModel usuario) {
            requisicao.setCriadoPor(usuario);
        }
        requisicaoService.save(requisicao);
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
        requisicao.getStatus().name();
        model.addAttribute("requisicao", requisicao);
        return "requisicoes/detalhes-requisicao";
    }

    @PostMapping("/requisicoes/{id}/rejeitar")
    public String rejeitar(@PathVariable Long id) {
        RequisicaoModel r = requisicaoService.findById(id);
        if (r != null && r.getStatus() != StatusEnum.FINALIZADA) {
            r.setStatus(StatusEnum.REJEITADA);
            requisicaoService.save(r);
        }
        return "redirect:/requisicoes/{id}";
    }

    @PostMapping("/requisicoes/{id}/iniciar-cotacao")
    public String iniciarCotacao(@PathVariable Long id) {
        RequisicaoModel r = requisicaoService.findById(id);
        if (r != null) {
            r.setStatus(StatusEnum.EM_COTACAO);
            requisicaoService.save(r);
        }
        return "redirect:/requisicoes/{id}";
    }

    @PostMapping("/requisicoes/{id}/finalizar")
    public String finalizar(@PathVariable Long id, @ModelAttribute("requisicao") RequisicaoModel form) {
        RequisicaoModel r = requisicaoService.findById(id);
        if (r != null && r.getStatus() == StatusEnum.EM_COTACAO) {
            r.setPedido1(form.getPedido1());
            r.setPedido2(form.getPedido2());
            r.setPedido3(form.getPedido3());
            Map<Long, ItemModel> itensPorId = r.getItens().stream()
            .filter(i -> i.getId() != null)
            .collect(Collectors.toMap(ItemModel::getId, 
            Function.identity()));

            if (form.getItens() != null) {
                for (ItemModel itForm : form.getItens()) {
                    if (itForm.getId() == null) continue;
                    ItemModel itDestino = itensPorId.get(itForm.getId());
                    if (itDestino != null) {
                        itDestino.setCotacao1(itForm.getCotacao1());
                        itDestino.setCotacao2(itForm.getCotacao2());
                        itDestino.setCotacao3(itForm.getCotacao3());
                    }
                }
            }
            r.setStatus(StatusEnum.FINALIZADA);
            requisicaoService.save(r);
        }
        return "redirect:/requisicoes/{id}";
    }
}
