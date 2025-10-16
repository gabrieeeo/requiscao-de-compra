package me.gabriel.requisicaodecompra.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.gabriel.requisicaodecompra.enums.StatusEnum;
import me.gabriel.requisicaodecompra.model.RequisicaoModel;
import me.gabriel.requisicaodecompra.repository.RequisicaoRepository;

@Service
public class RequisicaoService {

    private final RequisicaoRepository requisicaoRepository;

    public RequisicaoService(RequisicaoRepository requisicaoRepository) {
        this.requisicaoRepository = requisicaoRepository;
    }

    public List<RequisicaoModel> findAll() {
        return requisicaoRepository.findAll();
    }

    @Transactional
    public RequisicaoModel save(RequisicaoModel requisicao) {
        if (requisicao.getStatus() == null || requisicao.getStatus().name().isBlank()) {
            requisicao.setStatus(StatusEnum.PENDENTE);
        }
        RequisicaoModel salvo = requisicaoRepository.save(requisicao);
        if (salvo.getCodigo() == null || salvo.getCodigo().isBlank()) {
            salvo.setCodigo(String.format("REQ-%d", salvo.getId()));
            salvo = requisicaoRepository.save(salvo);
        }
        return salvo;
    }

    public void delete(RequisicaoModel requisicao) {
        requisicaoRepository.delete(requisicao);
    }

    public RequisicaoModel findById(Long id) {
        return requisicaoRepository.findById(id).orElse(null);
    }

    public long countByStatus(StatusEnum status) {
        return requisicaoRepository.countByStatus(status);
    }

    public AgendamentoStats getStats() {
        long total = requisicaoRepository.count();
        long pendente = countByStatus(StatusEnum.PENDENTE);
        long emCotacao = countByStatus(StatusEnum.EM_COTACAO);
        long finalizada = countByStatus(StatusEnum.FINALIZADA);
        long rejeitada = countByStatus(StatusEnum.REJEITADA);
        return new AgendamentoStats(total, pendente, emCotacao, finalizada, rejeitada);
    }

    public static class AgendamentoStats {
        private long total;
        private long pendente;
        private long emCotacao;
        private long finalizada;
        private long rejeitada;

        public AgendamentoStats(long total, long pendente, long emCotacao, long finalizada, long rejeitada) {
            this.total = total;
            this.pendente = pendente;
            this.emCotacao = emCotacao;
            this.finalizada = finalizada;
            this.rejeitada = rejeitada;
        }

        public long getTotal() {
            return total;
        }

        public long getPendente() {
            return pendente;
        }

        public long getEmCotacao() {
            return emCotacao;
        }

        public long getFinalizada() {
            return finalizada;
        }

        public long getRejeitada() {
            return rejeitada;
        }
    }
}
