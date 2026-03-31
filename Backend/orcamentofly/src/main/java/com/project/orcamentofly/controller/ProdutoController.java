package com.project.orcamentofly.controller;

import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service = new ProdutoService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Produto>> consultarTodos() {
        return ResponseEntity.ok().body(service.consultarTodos());
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Produto> consultarByid(@PathVariable int id) {
        return ResponseEntity.ok().body(service.consultarById(id));
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Produto produto) {
        service.inserir(produto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Produto produto) {
        service.atualizar(produto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@RequestBody Produto produto) {
        service.deletar(produto);
        return ResponseEntity.ok().build();
    }
}
