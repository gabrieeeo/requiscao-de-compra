package me.gabriel.requisicaodecompra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.gabriel.requisicaodecompra.model.RequisicaoModel;

@Repository
public interface RequisicaoRepository extends JpaRepository<RequisicaoModel, Long> {

	@Query("SELECT COUNT(r) FROM RequisicaoModel r WHERE YEAR(r.dataCriacao) = :ano")
	long countByAno(@Param("ano") int ano);
}
