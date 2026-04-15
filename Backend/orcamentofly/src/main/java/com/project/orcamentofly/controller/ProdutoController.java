package com.project.orcamentofly.controller;

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
            if (produto == null || produto.getNome() == null || produto.getNome().isEmpty()) {
                throw new BadRequestException("Nome do produto é obrigatório");
            }
            if (produto.getValorUnitario() <= 0) {
                throw new BadRequestException("Valor unitário do produto deve ser maior que zero");
            }
            if (produto.getEstoque() < 0) {
                throw new BadRequestException("Estoque do produto não pode ser negativo");
            }
            service.inserir(produto);
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
            if (produto == null || produto.getId() <= 0) {
                throw new BadRequestException("ID do produto inválido");
            }
            if (produto.getNome() == null || produto.getNome().isEmpty()) {
                throw new BadRequestException("Nome do produto é obrigatório");
            }
            if (produto.getValorUnitario() <= 0) {
                throw new BadRequestException("Valor unitário do produto deve ser maior que zero");
            }
            Produto existente = service.consultarById(produto.getId());
            if (existente == null) {
                throw new ResourceNotFoundException("Produto com ID " + produto.getId() + " não encontrado");
            }
            service.atualizar(produto);
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
                throw new BadRequestException("ID do produto inválido");
            }
            Produto existente = service.consultarById(id);
            if (existente == null) {
                throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado");
            }
            service.deletar(existente);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
