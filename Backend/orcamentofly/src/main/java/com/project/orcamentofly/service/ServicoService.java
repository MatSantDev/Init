package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.ServicoDAO;
import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.model.factory.ServicoFactory;

import java.util.List;

public class ServicoService {

    private final ServicoDAO dao;

    public ServicoService() {
        this.dao = new ServicoDAO();
    }

    public List<Servico> consultarTodos() {
        return dao.consultarTodos();
    }

    public Servico consultarById(int id) {
        Servico servico = new Servico();
        servico.setId(id);

        Servico servicoEncontrado = dao.consultarById(servico);
        if (servicoEncontrado == null || servicoEncontrado.getId() <= 0) {
            return null;
        }

        return servicoEncontrado;
    }

    public void inserir(Servico servico) {
        validarServico(servico, false);

        Servico novoServico = ServicoFactory.criarServico(
                servico.getNome(),
                servico.getDescricao(),
                servico.getValorUnitario()
        );
        dao.inserir(novoServico);
    }

    public void atualizar(Servico servico) {
        validarServico(servico, true);

        if (consultarById(servico.getId()) == null) {
            throw new ResourceNotFoundException("Serviço com ID " + servico.getId() + " não encontrado");
        }

        Servico servicoAtualizado = ServicoFactory.criarServico(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getValorUnitario()
        );
        dao.atualizar(servicoAtualizado);
    }

    public void deletar(int id) {
        if (id <= 0) {
            throw new BadRequestException("ID do serviço inválido");
        }

        Servico servico = consultarById(id);
        if (servico == null) {
            throw new ResourceNotFoundException("Serviço com ID " + id + " não encontrado");
        }

        dao.deletar(servico);
    }

    private void validarServico(Servico servico, boolean validarId) {
        if (servico == null) {
            throw new BadRequestException("Serviço é obrigatório");
        }
        if (validarId && servico.getId() <= 0) {
            throw new BadRequestException("ID do serviço inválido");
        }
        if (servico.getNome() == null || servico.getNome().isBlank()) {
            throw new BadRequestException("Nome do serviço é obrigatório");
        }
        if (servico.getValorUnitario() <= 0) {
            throw new BadRequestException("Valor unitário do serviço deve ser maior que zero");
        }
    }
}
