package com.project.orcamentofly.controller;

import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service = new ProdutoService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Produto>> consultarTodos() throws SQLException, ClassNotFoundException {
        return ResponseEntity.ok().body(service.consultarTodos());
    }

}
