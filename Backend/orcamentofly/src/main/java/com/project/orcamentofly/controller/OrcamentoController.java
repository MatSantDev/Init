package com.project.orcamentofly.controller;

import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.service.OrcamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService service = new OrcamentoService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Orcamento>> consultarTodos() {
        try {
            List<Orcamento> orcamentos = service.consultarTodos();
            if (orcamentos == null || orcamentos.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum orçamento encontrado");
            }
            return ResponseEntity.ok().body(orcamentos);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Orcamento> consultarById(@PathVariable int id) {
        try {
            if (id <= 0) {
                throw new BadRequestException("ID do orçamento inválido");
            }
            Orcamento orcamento = service.consultarById(id);
            if (orcamento == null) {
                throw new ResourceNotFoundException("Orçamento com ID " + id + " não encontrado");
            }
            return ResponseEntity.ok().body(orcamento);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Orcamento orcamento) {
        try {
            if (orcamento == null || orcamento.getCliente() == null) {
                throw new BadRequestException("Cliente do orçamento é obrigatório");
            }
            if (orcamento.getDataOrcamento() == null) {
                throw new BadRequestException("Data do orçamento é obrigatória");
            }
            service.inserir(orcamento);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Orcamento orcamento) {
        try {
            if (orcamento == null || orcamento.getId() <= 0) {
                throw new BadRequestException("ID do orçamento inválido");
            }
            if (orcamento.getCliente() == null) {
                throw new BadRequestException("Cliente do orçamento é obrigatório");
            }
            Orcamento existente = service.consultarById(orcamento.getId());
            if (existente == null) {
                throw new ResourceNotFoundException("Orçamento com ID " + orcamento.getId() + " não encontrado");
            }
            service.atualizar(orcamento);
            return ResponseEntity.ok().build();
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        try {
            if (id <= 0) {
                throw new BadRequestException("ID do orçamento inválido");
            }
            Orcamento existente = service.consultarById(id);
            if (existente == null) {
                throw new ResourceNotFoundException("Orçamento com ID " + id + " não encontrado");
            }
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
