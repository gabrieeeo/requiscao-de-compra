package me.gabriel.requisicaodecompra.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import me.gabriel.requisicaodecompra.enums.DepartamentoEnum;
import me.gabriel.requisicaodecompra.enums.StatusEnum;

@Entity
@Table(name = "requisicoes")
public class RequisicaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @CreatedBy
    @JoinColumn(name = "criado_por_id")
    private UsuarioModel criadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "departamento_id")
    private DepartamentoEnum departamento = criadoPor.getDepartamento();

    private String finalidadeDaCompra;

    private String pedidoDeCompra;

    @CreationTimestamp
    private LocalDateTime dataCriacao;
}
