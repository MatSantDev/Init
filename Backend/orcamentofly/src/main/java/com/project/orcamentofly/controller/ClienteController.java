package com.project.orcamentofly.controller;

import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service = new ClienteService();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Cliente>> consultarTodos() {
        try {
            return ResponseEntity.ok(service.consultarTodos());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Cliente> consultarById(@PathVariable int id) {
        try {
            Cliente cliente = service.consultarById(id);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Cliente cliente) {
        try {
            service.inserir(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Cliente cliente) {
        try {
            service.atualizar(cliente);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        try {
            Cliente cliente = new Cliente();
            cliente.setId(id);
            service.deletar(cliente);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
