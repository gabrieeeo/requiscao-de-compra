package me.gabriel.requisicaodecompra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_requisitados")
public class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer quantidade;
    private String unidadeMedida;

    @ManyToOne
    @JoinColumn(name = "requisicao_id")
    private RequisicaoModel requisicao;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public RequisicaoModel getRequisicao() {
        return requisicao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public void setRequisicao(RequisicaoModel requisicao) {
        this.requisicao = requisicao;
    }

}
