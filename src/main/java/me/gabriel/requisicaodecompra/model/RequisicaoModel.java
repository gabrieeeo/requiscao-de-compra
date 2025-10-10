package me.gabriel.requisicaodecompra.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private LocalDate prazoEntrega;

    private String finalidadeDaCompra;

    private String pedidoDeCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @CreatedBy
    @JoinColumn(name = "criado_por_id")
    private UsuarioModel criadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "departamento_id")
    private DepartamentoEnum departamento;

    @OneToMany(mappedBy = "requisicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemModel> itens = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    public void prePersist() {
        if (this.codigo == null) {
            this.codigo = "REQ-" + String.format("%04d", this.id);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public String getCodigo() {
        return codigo;
    }
    public StatusEnum getStatus() {
        return status;
    }
    public LocalDate getPrazoEntrega() {
        return prazoEntrega;
    }
    public String getFinalidadeDaCompra() {
        return finalidadeDaCompra;
    }
    public String getPedidoDeCompra() {
        return pedidoDeCompra;
    }
    public UsuarioModel getCriadoPor() {
        return criadoPor;
    }
    public DepartamentoEnum getDepartamento() {
        return departamento;
    }
    public List<ItemModel> getItens() {
        return itens;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setStatus(StatusEnum status) {
        this.status = status;
    }
    public void setPrazoEntrega(LocalDate prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }
    public void setFinalidadeDaCompra(String finalidadeDaCompra) {
        this.finalidadeDaCompra = finalidadeDaCompra;
    }
    public void setPedidoDeCompra(String pedidoDeCompra) {
        this.pedidoDeCompra = pedidoDeCompra;
    }
    public void setCriadoPor(UsuarioModel criadoPor) {
        this.criadoPor = criadoPor;
    }
    public void setDepartamento(DepartamentoEnum departamento) {
        this.departamento = departamento;
    }
    public void setItens(List<ItemModel> itens) {
        this.itens = itens;
    }
}
