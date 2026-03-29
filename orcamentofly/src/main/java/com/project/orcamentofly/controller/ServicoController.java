package com.project.orcamentofly.controller;

import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.service.ProdutoService;
import com.project.orcamentofly.service.ServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService service = new ServicoService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Servico>> consultarTodos() throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok().body(service.consultarTodos());
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Servico> consultarByid(@PathVariable int id) throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok().body(service.consultarById(id));
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Servico servico) throws SQLException, ClassNotFoundException {
        service.inserir(servico);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Servico servico) throws SQLException, ClassNotFoundException {
        service.atualizar(servico);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@RequestBody Servico servico) throws SQLException, ClassNotFoundException {
        service.deletar(servico);
        return ResponseEntity.ok().build();
    }
}
