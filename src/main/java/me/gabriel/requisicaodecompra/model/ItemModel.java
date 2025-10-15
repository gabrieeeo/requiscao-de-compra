package me.gabriel.requisicaodecompra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "itens_requisitados")
public class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do item é obrigatório")
    private String nome;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    private Integer quantidade;
    private String unidadeMedida;

    private String cotacao1;
    private String cotacao2;
    private String cotacao3;

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

    public String getCotacao1() {
        return cotacao1;
    }

    public void setCotacao1(String cotacao1) {
        this.cotacao1 = cotacao1;
    }

    public String getCotacao2() {
        return cotacao2;
    }

    public void setCotacao2(String cotacao2) {
        this.cotacao2 = cotacao2;
    }

    public String getCotacao3() {
        return cotacao3;
    }

    public void setCotacao3(String cotacao3) {
        this.cotacao3 = cotacao3;
    }

}
