package me.gabriel.requisicaodecompra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.gabriel.requisicaodecompra.model.RequisicaoModel;

public interface RequisicaoRepository extends JpaRepository<RequisicaoModel, Long> {

}
