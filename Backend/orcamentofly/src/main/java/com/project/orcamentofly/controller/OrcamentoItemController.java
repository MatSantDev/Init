package com.project.orcamentofly.controller;

import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.service.OrcamentoItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
            List<OrcamentoItem> itens = service.consultarTodosByOrcamentoId(orcamentoId);
            return ResponseEntity.ok(itens);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarTodosByOrcamento")
    public ResponseEntity<List<OrcamentoItem>> consultarTodosByOrcamento(@RequestBody Orcamento orcamento) {
        try {
            List<OrcamentoItem> itens = service.consultarTodosByOrcamento(orcamento);
            return ResponseEntity.ok(itens);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<OrcamentoItem> consultarById(@PathVariable int id) {
        try {
            OrcamentoItem item = service.consultarById(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<?> inserir(@PathVariable int orcamentoId, @RequestBody OrcamentoItem item) {
        try {
            Orcamento orcamento = new Orcamento();
            orcamento.setId(orcamentoId);
            item.setOrcamento(orcamento);

            service.inserir(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int orcamentoId, @PathVariable int id, @RequestBody OrcamentoItem item) {
        try {
            item.setId(id);
            Orcamento orcamento = new Orcamento();
            orcamento.setId(orcamentoId);
            item.setOrcamento(orcamento);

            service.atualizar(item);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable int orcamentoId, @PathVariable int id, @RequestBody OrcamentoItem item) {
        try {
            service.deletar(item);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
