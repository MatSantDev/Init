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
            return ResponseEntity.ok().body(service.consultarTodosByOrcamentoId(orcamentoId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarTodosByOrcamento")
    public ResponseEntity<List<OrcamentoItem>> consultarTodosByOrcamento(@RequestBody Orcamento orcamento) {
        try {
            return ResponseEntity.ok().body(service.consultarTodosByOrcamento(orcamento));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<OrcamentoItem> consultarById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(service.consultarById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<?> inserir(@PathVariable int orcamentoId, @RequestBody OrcamentoItem item) {
        try {
            service.inserir(orcamentoId,item);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int orcamentoId, @PathVariable int id, @RequestBody OrcamentoItem item) {
        try {
            service.atualizar(orcamentoId, id, item);
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
