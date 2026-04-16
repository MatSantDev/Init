package com.project.orcamentofly.controller;

import com.project.orcamentofly.command.CommandInvoker;
import com.project.orcamentofly.command.cliente.AtualizarClienteCommand;
import com.project.orcamentofly.command.cliente.InserirClienteCommand;
import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
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
    private final CommandInvoker invoker = new CommandInvoker();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Cliente>> consultarTodos() {
        try {
            List<Cliente> clientes = service.consultarTodos();
            if (clientes == null || clientes.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum cliente encontrado");
            }
            return ResponseEntity.ok().body(clientes);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Cliente> consultarById(@PathVariable int id) {
        try {
            if (id <= 0) {
                throw new BadRequestException("ID do cliente inválido");
            }
            Cliente cliente = service.consultarById(id);
            if (cliente == null) {
                throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado");
            }
            return ResponseEntity.ok().body(cliente);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Cliente cliente) {
        try {
            if (cliente == null || cliente.getNome() == null || cliente.getNome().isEmpty()) {
                throw new BadRequestException("Nome do cliente é obrigatório");
            }
            if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
                throw new BadRequestException("Email do cliente é obrigatório");
            }
            if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
                throw new BadRequestException("CPF do cliente é obrigatório");
            }

            InserirClienteCommand comando = new InserirClienteCommand(service, cliente);
            invoker.executar(comando);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Cliente cliente) {
        try {
            if (cliente == null || cliente.getId() <= 0) {
                throw new BadRequestException("ID do cliente inválido");
            }
            if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
                throw new BadRequestException("Nome do cliente é obrigatório");
            }
            Cliente existente = service.consultarById(cliente.getId());
            if (existente == null) {
                throw new ResourceNotFoundException("Cliente com ID " + cliente.getId() + " não encontrado");
            }

            AtualizarClienteCommand comando = new AtualizarClienteCommand(service, cliente);
            invoker.executar(comando);

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
                throw new BadRequestException("ID do cliente inválido");
            }
            Cliente existente = service.consultarById(id);
            if (existente == null) {
                throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado");
            }
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
