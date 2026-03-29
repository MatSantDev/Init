package com.project.orcamentofly.controller;

import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.service.OrcamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService service = new OrcamentoService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Orcamento>> consultarTodos() throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok().body(service.consultarTodos());
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Orcamento> consultarByid(@PathVariable int id) throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok().body(service.consultarById(id));
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Orcamento orcamento) throws SQLException, ClassNotFoundException {
        service.inserir(orcamento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Orcamento orcamento) throws SQLException, ClassNotFoundException {
        service.atualizar(orcamento);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@RequestBody Orcamento orcamento) throws SQLException, ClassNotFoundException {
        service.deletar(orcamento);
        return ResponseEntity.ok().build();
    }
}
