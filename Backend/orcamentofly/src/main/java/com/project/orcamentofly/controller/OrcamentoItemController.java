package com.project.orcamentofly.controller;

import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.service.OrcamentoItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcamentos/{orcamentoId}/itens")
public class OrcamentoItemController {

    private OrcamentoItemService service;

    public OrcamentoItemController() {
        this.service = new OrcamentoItemService();
    }

    @GetMapping("/consultarTodosByOrcamentoId")
    public ResponseEntity<List<OrcamentoItem>> consultarTodosByOrcamentoId(@PathVariable int orcamentoId) {
        try {
            if (orcamentoId <= 0) {
                throw new BadRequestException("ID do orçamento inválido");
            }
            List<OrcamentoItem> itens = service.consultarTodosByOrcamentoId(orcamentoId);
            if (itens == null || itens.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum item encontrado para o orçamento " + orcamentoId);
            }
            return ResponseEntity.ok().body(itens);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarTodosByOrcamento")
    public ResponseEntity<List<OrcamentoItem>> consultarTodosByOrcamento(@RequestBody Orcamento orcamento) {
        try {
            if (orcamento == null || orcamento.getId() <= 0) {
                throw new BadRequestException("Orçamento inválido");
            }
            List<OrcamentoItem> itens = service.consultarTodosByOrcamento(orcamento);
            if (itens == null || itens.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum item encontrado para o orçamento");
            }
            return ResponseEntity.ok().body(itens);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<OrcamentoItem> consultarById(@PathVariable int id) {
        try {
            if (id <= 0) {
                throw new BadRequestException("ID do item inválido");
            }
            OrcamentoItem item = service.consultarById(id);
            if (item == null) {
                throw new ResourceNotFoundException("Item com ID " + id + " não encontrado");
            }
            return ResponseEntity.ok().body(item);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<?> inserir(@PathVariable int orcamentoId, @RequestBody OrcamentoItem item) {
        try {
            if (orcamentoId <= 0) {
                throw new BadRequestException("ID do orçamento inválido");
            }
            if (item == null || item.getDescricao() == null || item.getDescricao().isEmpty()) {
                throw new BadRequestException("Descrição do item é obrigatória");
            }
            if (item.getQuantidade() <= 0) {
                throw new BadRequestException("Quantidade do item deve ser maior que zero");
            }
            if (item.getValorUnitario() <= 0) {
                throw new BadRequestException("Valor unitário do item deve ser maior que zero");
            }
            service.inserir(orcamentoId, item);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int orcamentoId, @PathVariable int id, @RequestBody OrcamentoItem item) {
        try {
            if (orcamentoId <= 0) {
                throw new BadRequestException("ID do orçamento inválido");
            }
            if (id <= 0) {
                throw new BadRequestException("ID do item inválido");
            }
            if (item == null || item.getDescricao() == null || item.getDescricao().isEmpty()) {
                throw new BadRequestException("Descrição do item é obrigatória");
            }
            OrcamentoItem existente = service.consultarById(id);
            if (existente == null) {
                throw new ResourceNotFoundException("Item com ID " + id + " não encontrado");
            }
            service.atualizar(orcamentoId, id, item);
            return ResponseEntity.ok(item);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable int orcamentoId, @PathVariable int id, @RequestBody OrcamentoItem item) {
        try {
            if (orcamentoId <= 0) {
                throw new BadRequestException("ID do orçamento inválido");
            }
            if (id <= 0) {
                throw new BadRequestException("ID do item inválido");
            }
            OrcamentoItem existente = service.consultarById(id);
            if (existente == null) {
                throw new ResourceNotFoundException("Item com ID " + id + " não encontrado");
            }
            service.deletar(item);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
