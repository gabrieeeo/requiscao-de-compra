package me.gabriel.requisicaodecompra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.gabriel.requisicaodecompra.enums.DepartamentoEnum;
import me.gabriel.requisicaodecompra.enums.StatusEnum;
import me.gabriel.requisicaodecompra.model.RequisicaoModel;

@Repository
public interface RequisicaoRepository extends JpaRepository<RequisicaoModel, Long> {

    long countByStatus(StatusEnum status);

    @Query(value = """
              SELECT DISTINCT r
              FROM RequisicaoModel r
              LEFT JOIN r.itens i
              LEFT JOIN r.criadoPor u
              WHERE (:status IS NULL OR r.status = :status)
                AND (:departamento IS NULL OR r.departamento = :departamento)
                AND (
                     :search IS NULL
                     OR LOWER(r.codigo) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(r.finalidadeDaCompra) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(i.nome) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :search, '%'))
                )
            """, countQuery = """
              SELECT COUNT(DISTINCT r)
              FROM RequisicaoModel r
              LEFT JOIN r.itens i
              LEFT JOIN r.criadoPor u
              WHERE (:status IS NULL OR r.status = :status)
                AND (:departamento IS NULL OR r.departamento = :departamento)
                AND (
                     :search IS NULL
                     OR LOWER(r.codigo) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(r.finalidadeDaCompra) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(i.nome) LIKE LOWER(CONCAT('%', :search, '%'))
                     OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :search, '%'))
                )
            """)
    Page<RequisicaoModel> findWithFilters(
            @Param("status") StatusEnum status,
            @Param("departamento") DepartamentoEnum departamento,
            @Param("search") String search,
            org.springframework.data.domain.Pageable pageable);

}
