package me.gabriel.requisicaodecompra.enums;

public enum StatusEnum {

    PENDENTE("Pendente"),
    EM_COTACAO("Em Cotação"),
    FINALIZADA("Finalizada"),
    REJEITADA("Rejeitada");

    private final String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
