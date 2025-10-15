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

    public RequisicaoModel save(RequisicaoModel requisicao) {
        requisicao.setStatus(StatusEnum.PENDENTE);
        return requisicaoRepository.save(requisicao);
    }

    public void delete(RequisicaoModel requisicao) {
        requisicaoRepository.delete(requisicao);
    }

    public RequisicaoModel findById(Long id) {
        return requisicaoRepository.findById(id).orElse(null);
    }

    @Transactional
    public RequisicaoModel salvarNova(RequisicaoModel requisicao) {
        requisicao = save(requisicao);
        if (requisicao.getCodigo() == null || requisicao.getCodigo().isBlank()) {
            requisicao.setCodigo(String.format("REQ-%d", requisicao.getId()));
        }
        // status inicial se nulo
        if (requisicao.getStatus() == null) {
            // evitar depender de enum default ausente; pode ajustar depois
        }
        return save(requisicao);
    }
}
