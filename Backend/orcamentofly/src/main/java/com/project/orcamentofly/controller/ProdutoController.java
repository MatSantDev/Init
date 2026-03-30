package com.project.orcamentofly.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service = new ProdutoService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Produto>> consultarTodos() throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok().body(service.consultarTodos());
    }

    @GetMapping("/consultarPrimeiro")
    public ResponseEntity<Produto> consultarPrimeiro() throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok().body(service.consultarPrimeiro());
    }

}
