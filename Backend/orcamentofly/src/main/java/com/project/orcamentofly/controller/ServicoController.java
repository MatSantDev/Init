package com.project.orcamentofly.controller;

import com.project.orcamentofly.command.CommandInvoker;
import com.project.orcamentofly.command.servico.AtualizarServicoCommand;
import com.project.orcamentofly.command.servico.DeletarServicoCommand;
import com.project.orcamentofly.command.servico.InserirServicoCommand;
import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.service.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService service = new ServicoService();
    private final CommandInvoker invoker = new CommandInvoker();

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Servico>> consultarTodos() {
        try {
            List<Servico> servicos = service.consultarTodos();
            if (servicos == null || servicos.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum serviço encontrado");
            }
            return ResponseEntity.ok().body(servicos);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultarById/{id}")
    public ResponseEntity<Servico> consultarByid(@PathVariable int id) {
        try {
            if (id <= 0) {
                throw new BadRequestException("ID do serviço inválido");
            }
            Servico servico = service.consultarById(id);
            if (servico == null) {
                throw new ResourceNotFoundException("Serviço com ID " + id + " não encontrado");
            }
            return ResponseEntity.ok().body(servico);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/inserir")
    public ResponseEntity<Void> inserir(@RequestBody Servico servico) {
        try {
            if (servico == null) {
                throw new BadRequestException("Serviço é obrigatório");
            }

            invoker.executar(new InserirServicoCommand(service, servico));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Servico servico) {
        try {
            if (servico == null) {
                throw new BadRequestException("Serviço é obrigatório");
            }

            invoker.executar(new AtualizarServicoCommand(service, servico));
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
            invoker.executar(new DeletarServicoCommand(service, id));
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
