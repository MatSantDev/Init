package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.decorator.concretos.MicroProdutoCustomizacao;
import com.project.orcamentofly.model.decorator.concretos.MicroProdutoEmbalagem;
import com.project.orcamentofly.model.decorator.concretos.MicroProdutoSeguro;
import com.project.orcamentofly.model.dto.MicroProdutoDTO;
import com.project.orcamentofly.model.interfaces.MicroProdutoInterface;

import java.util.List;

public class MicroProdutoDecoratorFactory {

    public static MicroProdutoInterface aplicarDecorators(Produto produtoBase, List<MicroProdutoDTO> microProdutos) {
        MicroProdutoInterface corrente = produtoBase;

        if (microProdutos == null || microProdutos.isEmpty()) {
            return corrente;
        }

        for (MicroProdutoDTO dto : microProdutos) {
            if (dto == null || dto.getTipo() == null) continue;
            String tipo = dto.getTipo().toUpperCase().trim();
            switch (tipo) {
                case "CUSTOMIZACAO":
                    corrente = new MicroProdutoCustomizacao(corrente);
                    break;
                case "SEGURO":
                    corrente = new MicroProdutoSeguro(corrente);
                    break;
                case "EMBALAGEM":
                    corrente = new MicroProdutoEmbalagem(corrente);
                    break;
                default:
                    break;
            }
        }

        return corrente;
    }
}
