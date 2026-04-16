package com.project.orcamentofly.controller;

import com.project.orcamentofly.command.CommandInvoker;
import com.project.orcamentofly.command.produto.AtualizarProdutoCommand;
import com.project.orcamentofly.command.produto.DeletarProdutoCommand;
import com.project.orcamentofly.command.produto.InserirProdutoCommand;
import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service = new ProdutoService();
    private final CommandInvoker invoker = new CommandInvoker();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Produto>> consultarTodos() {
        try {
            List<Produto> produtos = service.consultarTodos();
            if (produtos == null || produtos.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum produto encontrado");
            }
            return ResponseEntity.ok().body(produtos);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Produto> consultarByid(@PathVariable int id) {
        try {
            if (id <= 0) {
                throw new BadRequestException("ID do produto inválido");
            }
            Produto produto = service.consultarById(id);
            if (produto == null) {
                throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado");
            }
            return ResponseEntity.ok().body(produto);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Produto produto) {
        try {
            if (produto == null) {
                throw new BadRequestException("Produto é obrigatório");
            }

            invoker.executar(new InserirProdutoCommand(service, produto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Produto produto) {
        try {
            if (produto == null) {
                throw new BadRequestException("Produto é obrigatório");
            }

            invoker.executar(new AtualizarProdutoCommand(service, produto));
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
            invoker.executar(new DeletarProdutoCommand(service, id));
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
