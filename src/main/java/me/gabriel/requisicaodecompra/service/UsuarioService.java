package me.gabriel.requisicaodecompra.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import me.gabriel.requisicaodecompra.enums.RoleEnum;
import me.gabriel.requisicaodecompra.model.UsuarioModel;
import me.gabriel.requisicaodecompra.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    public void criarUsuarioAdmin() {
        if (usuarioRepository.findByUsername("glima").isEmpty()) {
            UsuarioModel usuario = new UsuarioModel("Gabriel Lima", 
            "glima", "email@email.com", passwordEncoder.encode("teste"), RoleEnum.ADMIN);
            usuarioRepository.save(usuario);
        }
    }

    public Optional<UsuarioModel> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
