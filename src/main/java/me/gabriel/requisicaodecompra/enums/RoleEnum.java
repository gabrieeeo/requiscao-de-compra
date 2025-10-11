package me.gabriel.requisicaodecompra.enums;

public enum RoleEnum {

    ADMIN("Administrador"),
    COMPRADOR("Comprador"),
    FUNCIONARIO("Funcionário");

    private String descricao;

    RoleEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
