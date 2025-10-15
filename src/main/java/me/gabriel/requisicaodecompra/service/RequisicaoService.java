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
}
