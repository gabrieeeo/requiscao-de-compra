package me.gabriel.requisicaodecompra.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import me.gabriel.requisicaodecompra.enums.RoleEnum;

@Entity
@Table(name = "usuarios")
public class UsuarioModel implements UserDetails{

    public UsuarioModel() {}

    public UsuarioModel(String nome, String username, String email, String senha, RoleEnum role) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String username;
    private String email;
    private String senha;

    private RoleEnum role;

    private boolean enabled = true;

    @CreationTimestamp
    private LocalDateTime criadoEm = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public RoleEnum getRole() {
        return role;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    // Implementação do UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
