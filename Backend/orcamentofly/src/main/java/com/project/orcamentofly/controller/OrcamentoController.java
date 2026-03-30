package com.project.orcamentofly.controller;

import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.service.OrcamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService service = new OrcamentoService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Orcamento>> consultarTodos() {
        try {
            return ResponseEntity.ok(service.consultarTodos());
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Orcamento> consultarById(@PathVariable int id) {
        try {
            Orcamento orcamento = service.consultarById(id);
            if (orcamento != null) {
                return ResponseEntity.ok(orcamento);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Orcamento orcamento) {
        try {
            service.inserir(orcamento);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Orcamento orcamento) {
        try {
            service.atualizar(orcamento);
            return ResponseEntity.ok().build();
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        try {
            Orcamento orcamento = new Orcamento();
            orcamento.setId(id);
            service.deletar(orcamento);
            return ResponseEntity.noContent().build();
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
