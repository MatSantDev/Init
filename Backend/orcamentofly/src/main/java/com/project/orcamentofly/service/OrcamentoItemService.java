package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.OrcamentoItemDAO;
import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.enums.TipoOrcamentoItem;

import java.util.List;

public class OrcamentoItemService {

    private final OrcamentoItemDAO dao;
    private final ProdutoService produtoService;
    private final OrcamentoService orcamentoService;

    public OrcamentoItemService() {
        this.dao = new OrcamentoItemDAO();
        this.produtoService = new ProdutoService();
        this.orcamentoService = new OrcamentoService();
    }

    public List<OrcamentoItem> consultarTodosByOrcamento(Orcamento orcamento) {
        return dao.consultarTodosByOrcamento(orcamento);
    }

    public List<OrcamentoItem> consultarTodosByOrcamentoId(int orcamentoId) {
        Orcamento orcamento = obterOrcamentoExistente(orcamentoId);
        return dao.consultarTodosByOrcamentoId(orcamento);
    }

    public OrcamentoItem consultarById(int id) {
        OrcamentoItem item = new OrcamentoItem();
        item.setId(id);

        OrcamentoItem itemEncontrado = dao.consultarById(item);
        if (itemEncontrado == null || itemEncontrado.getId() <= 0) {
            return null;
        }

        return itemEncontrado;
    }

    public void inserir(int orcamentoId, OrcamentoItem item) {
        Orcamento orcamento = obterOrcamentoExistente(orcamentoId);
        item.setOrcamento(orcamento);

        validarOrcamentoItem(item);
        item.calcularSubtotal();
        dao.inserir(item);

        if (item.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO) {
            produtoService.reduzirEstoque(item.getProduto().getId(), item.getQuantidade());
        }

        orcamentoService.atualizarValorTotal(orcamentoId);
    }

    public void atualizar(int orcamentoId, int id, OrcamentoItem item) {
        OrcamentoItem existente = consultarById(id);
        if (existente == null) {
            throw new ResourceNotFoundException("Item com ID " + id + " não encontrado");
        }

        Orcamento orcamento = obterOrcamentoExistente(orcamentoId);
        item.setId(id);
        item.setOrcamento(orcamento);

        validarOrcamentoItem(item);
        reconciliarEstoqueProduto(existente, item);
        item.calcularSubtotal();
        dao.atualizar(item);
        orcamentoService.atualizarValorTotal(orcamentoId);
    }

    public void deletar(OrcamentoItem item) {
        if (item == null || item.getId() <= 0) {
            throw new BadRequestException("Item do orçamento inválido");
        }

        OrcamentoItem existente = consultarById(item.getId());
        if (existente == null) {
            throw new ResourceNotFoundException("Item com ID " + item.getId() + " não encontrado");
        }

        if (existente.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO && existente.getProduto() != null) {
            produtoService.aumentarEstoque(existente.getProduto().getId(), existente.getQuantidade());
        }

        dao.deletar(existente);
        if (existente.getOrcamento() != null && existente.getOrcamento().getId() > 0) {
            orcamentoService.atualizarValorTotal(existente.getOrcamento().getId());
        }
    }

    private void validarOrcamentoItem(OrcamentoItem item) {
        if (item == null) {
            throw new BadRequestException("Item do orçamento é obrigatório");
        }
        if (item.getDescricao() == null || item.getDescricao().isBlank()) {
            throw new BadRequestException("Descrição do item é obrigatória");
        }
        if (item.getQuantidade() <= 0) {
            throw new BadRequestException("A quantidade deve ser maior que zero.");
        }
        if (item.getValorUnitario() < 0) {
            throw new BadRequestException("O valor unitário deve ser maior ou igual a zero.");
        }
        if (item.getTipoOrcamentoItem() == null) {
            throw new BadRequestException("Tipo do item do orçamento é obrigatório.");
        }

        if (item.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO) {
            if (item.getProduto() == null || item.getProduto().getId() <= 0) {
                throw new BadRequestException("Para itens do tipo PRODUTO, um produto válido deve ser informado.");
            }

            Produto produto = produtoService.consultarById(item.getProduto().getId());
            if (produto == null) {
                throw new ResourceNotFoundException("Produto com ID " + item.getProduto().getId() + " não encontrado");
            }
            if (item.getQuantidade() > produto.getEstoque()) {
                throw new BadRequestException("O produto possui menor quantidade do requirida");
            }

            item.setServico(null);
        } else if (item.getTipoOrcamentoItem() == TipoOrcamentoItem.SERVICO) {
            if (item.getServico() == null || item.getServico().getId() <= 0) {
                throw new BadRequestException("Para itens do tipo SERVICO, um serviço válido deve ser informado.");
            }
            item.setProduto(null);
        }
    }

    private Orcamento obterOrcamentoExistente(int orcamentoId) {
        if (orcamentoId <= 0) {
            throw new BadRequestException("ID do orçamento inválido");
        }

        Orcamento orcamento = orcamentoService.consultarById(orcamentoId);
        if (orcamento == null) {
            throw new ResourceNotFoundException("Orçamento com ID " + orcamentoId + " não encontrado");
        }

        return orcamento;
    }

    private void reconciliarEstoqueProduto(OrcamentoItem existente, OrcamentoItem atualizado) {
        boolean existenteEhProduto = existente.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO && existente.getProduto() != null;
        boolean atualizadoEhProduto = atualizado.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO && atualizado.getProduto() != null;

        if (!existenteEhProduto && !atualizadoEhProduto) {
            return;
        }

        if (existenteEhProduto && !atualizadoEhProduto) {
            produtoService.aumentarEstoque(existente.getProduto().getId(), existente.getQuantidade());
            return;
        }

        if (!existenteEhProduto) {
            produtoService.reduzirEstoque(atualizado.getProduto().getId(), atualizado.getQuantidade());
            return;
        }

        int produtoAnteriorId = existente.getProduto().getId();
        int produtoAtualId = atualizado.getProduto().getId();

        if (produtoAnteriorId == produtoAtualId) {
            int diferencaQuantidade = atualizado.getQuantidade() - existente.getQuantidade();
            if (diferencaQuantidade > 0) {
                produtoService.reduzirEstoque(produtoAtualId, diferencaQuantidade);
            } else if (diferencaQuantidade < 0) {
                produtoService.aumentarEstoque(produtoAtualId, Math.abs(diferencaQuantidade));
            }
            return;
        }

        produtoService.aumentarEstoque(produtoAnteriorId, existente.getQuantidade());
        produtoService.reduzirEstoque(produtoAtualId, atualizado.getQuantidade());
    }
}
