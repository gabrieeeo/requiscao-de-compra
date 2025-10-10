package me.gabriel.requisicaodecompra.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import me.gabriel.requisicaodecompra.enums.DepartamentoEnum;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String username;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private DepartamentoEnum departamento;

    @CreationTimestamp
    private LocalDateTime criadoEm = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime atualizadoEm = LocalDateTime.now();

}
