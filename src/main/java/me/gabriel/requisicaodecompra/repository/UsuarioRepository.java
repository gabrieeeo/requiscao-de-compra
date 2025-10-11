package me.gabriel.requisicaodecompra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.gabriel.requisicaodecompra.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

    public Optional<UsuarioModel> findByUsername(String username);

}
