package me.gabriel.requisicaodecompra.enums;

public enum DepartamentoEnum {

    MANUTENCAO("Manutenção"),
    LOGISTICA("Logística"),
    QUALIDADE("Qualidade"),
    PROJETO_E_DESENVOLVIMENTO("P&D"),
    PRODUCAO("Produção"),
    JURIDICO("Jurídico"),
    TI("TI");

    private final String descricao;

    DepartamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
