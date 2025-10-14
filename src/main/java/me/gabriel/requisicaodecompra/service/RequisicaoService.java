package me.gabriel.requisicaodecompra.service;

import java.time.Year;
import java.util.List;

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

    public List<RequisicaoModel> findAll() {
        return requisicaoRepository.findAll();
    }

    public String gerarCodigoSequencial() {
        ultimoNumero++;
        return String.format("REQ-%d", ultimoNumero);
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
