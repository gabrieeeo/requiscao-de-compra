package me.gabriel.requisicaodecompra.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import me.gabriel.requisicaodecompra.model.UsuarioModel;
import me.gabriel.requisicaodecompra.service.UsuarioService;

@ControllerAdvice
public class GlobalControllerAdvice {

    private UsuarioService usuarioService;

    public GlobalControllerAdvice(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("usuarioLogado")
    public UsuarioModel getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            return usuarioService.findByUsername(username).stream().findFirst().orElse(null);
        }
        return null;
    }
}
