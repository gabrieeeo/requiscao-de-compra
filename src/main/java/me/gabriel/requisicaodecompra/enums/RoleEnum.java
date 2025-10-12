package me.gabriel.requisicaodecompra.enums;

public enum RoleEnum {

    ADMIN("Administrador"),
    COMPRADOR("Comprador"),
    USUARIO("Usuário");

    private String descricao;

    RoleEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
