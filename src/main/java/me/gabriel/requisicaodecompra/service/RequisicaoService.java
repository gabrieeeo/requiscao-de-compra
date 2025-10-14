package me.gabriel.requisicaodecompra.service;

import java.time.Year;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.gabriel.requisicaodecompra.model.RequisicaoModel;
import me.gabriel.requisicaodecompra.repository.RequisicaoRepository;

@Service
public class RequisicaoService {

    private final RequisicaoRepository requisicaoRepository;

    public RequisicaoService(RequisicaoRepository requisicaoRepository) {
        this.requisicaoRepository = requisicaoRepository;
    }

    public String gerarCodigoSequencial() {
        int ano = Year.now().getValue();
        long qtdNoAno = requisicaoRepository.countByAno(ano) + 1; // próximo número
        return String.format("REQ-%d-%04d", ano, qtdNoAno);
    }

    @Transactional
    public RequisicaoModel salvarNova(RequisicaoModel requisicao) {
        if (requisicao.getCodigo() == null || requisicao.getCodigo().isBlank()) {
            requisicao.setCodigo(gerarCodigoSequencial());
        }
        // status inicial se nulo
        if (requisicao.getStatus() == null) {
            // evitar depender de enum default ausente; pode ajustar depois
        }
        return requisicaoRepository.save(requisicao);
    }
}
