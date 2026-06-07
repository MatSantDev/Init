package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.dto.MicroServicoDTO;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.model.interfaces.MicroServicoInterface;
import com.project.orcamentofly.model.decorator.concretos.MicroServicoGarantia;
import com.project.orcamentofly.model.decorator.concretos.MicroServicoNoturno;
import com.project.orcamentofly.model.decorator.concretos.MicroServicoUrgente;

import java.util.List;

public class MicroServicoDecoratorFactory {

    public static MicroServicoInterface aplicarDecorators(Servico servicoBase, List<MicroServicoDTO> microServicos) {
        MicroServicoInterface corrente = servicoBase;

            if (microServicos == null || microServicos.isEmpty()) {
            return corrente;
        }

        for (MicroServicoDTO dto : microServicos) {
            if (dto == null || dto.getTipo() == null) continue;
            String tipo = dto.getTipo().toUpperCase().trim();
            switch (tipo) {
                case "URGENTE":
                    corrente = new MicroServicoUrgente(corrente);
                    break;
                case "NOTURNO":
                    corrente = new MicroServicoNoturno(corrente);
                    break;
                case "GARANTIA":
                    corrente = new MicroServicoGarantia(corrente);
                    break;
                default:
                    break;
            }
        }
        return corrente;
    }
}

