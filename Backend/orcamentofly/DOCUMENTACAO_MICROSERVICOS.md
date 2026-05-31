Alterações realizadas para implementar corretamente o Design Pattern Decorator para MicroServiços

Resumo das mudanças:

1) Correções nos decorators concretos
- `MicroServicoUrgente`:
  - Antes: multiplicador *1.20
  - Agora: multiplicador *1.30 (adiciona 30%)
- `MicroServicoNoturno`:
  - Antes: multiplicador *1.10
  - Agora: soma fixa +150.0 (adiciona R$150)
- `MicroServicoGarantia`:
  - Antes: multiplicador *1.15
  - Agora: multiplicador *1.10 (adiciona 10%)

2) Ajuste em `MicroServico`
- `getValor()` agora retorna o campo `valor` da instância em vez de sempre retornar 0.0.

3) Novo DTO
- `model/MicroServicoDTO.java` criado para representar o microserviço enviado pelo frontend com o campo `tipo` (ex.: "URGENTE", "NOTURNO", "GARANTIA").

4) `OrcamentoItem`:
- Adicionado campo `private List<MicroServicoDTO> microServicos;` com getters/setters para receber do frontend a lista de microserviços aplicados ao item.
- Importações adicionadas: `MicroServicoDTO` e `java.util.List`.

5) Fábrica de decorators
- `service/MicroServicoDecoratorFactory.java` criada. Implementa:
  - `public static MicroServicoInterface aplicarDecorators(Servico servicoBase, List<MicroServicoDTO> microServicos)`
  - Mapeia os tipos recebidos para os decorators concretos e aplica em sequência.
  - Tipos desconhecidos são ignorados (pode-se alterar para lançar exceção se desejar validação mais estrita).

6) Aplicação na camada de serviço
- `service/OrcamentoItemService.java` atualizado nas operações `inserir` e `atualizar`:
  - Quando o `OrcamentoItem` é do tipo `SERVICO`, a fábrica é usada para construir a cadeia de decorators baseada em `item.getMicroServicos()`.
  - O `valorUnitario` do item é atualizado com `msFinal.getValor()` antes de chamar `calcularSubtotal()` e persistir.

Observações e recomendações adicionais (não implementadas automaticamente):
- Persistência: sugiro criar a tabela `orcamento_item_microservico` para gravar cada microserviço aplicado a um `orcamento_item`. A abordagem simples é: ao inserir/atualizar um item, gravar as linhas correspondentes; ao atualizar, remover as anteriores e inserir as novas.
- Validação: atualmente tipos desconhecidos são ignorados; se preferir, valide e retorne BadRequest para tipos inválidos.
- Ordem dos decorators: a ordem como o frontend envia os microserviços será a ordem de aplicação. Documente isso na API.
- Classe `MicroServico` pode ser mantida ou removida conforme uso; agora `getValor()` está funcional caso precise ser usada.

Como testar rapidamente (manual):
1) Inserir um item do tipo SERVICO com `microServicos` = [ {"tipo":"URGENTE"}, {"tipo":"GARANTIA"} ] para um serviço de R$100. Esperado:
   - Aplicar URGENTE (+30%) -> 130
   - Aplicar GARANTIA (+10%) -> 130 * 1.10 = 143
   - `valorUnitario` gravado no item deve ser 143 e `subtotal` calculado multiplicando por `quantidade`.

2) Testar NOTURNO (adiciona R$150) combinado com percentuais — observe que a ordem pode mudar o resultado; documente a ordem esperada no frontend.

Se quiser, eu posso:
- Implementar a tabela `orcamento_item_microservico` e os métodos no `OrcamentoItemDAO` para persistência automática.
- Alterar o comportamento para validar tipos inválidos e retornar erro 400.
- Criar enum `TipoMicroServico` e usar no DTO e DB.

Arquivos modificados/criados:
- Modificados:
  - src/main/java/.../model/decorator/concretos/MicroServicoUrgente.java
  - src/main/java/.../model/decorator/concretos/MicroServicoNoturno.java
  - src/main/java/.../model/decorator/concretos/MicroServicoGarantia.java
  - src/main/java/.../model/MicroServico.java
  - src/main/java/.../model/OrcamentoItem.java
  - src/main/java/.../service/OrcamentoItemService.java

- Criados:
  - src/main/java/.../model/MicroServicoDTO.java
  - src/main/java/.../service/MicroServicoDecoratorFactory.java
  - DOCUMENTACAO_MICROSERVICOS.md (este arquivo)

Persistência implementada:
- Foi adicionada a tabela `orcamento_item_microservico` em `src/main/resources/banco/banco.sql`.
- O DAO `src/main/java/.../dao/OrcamentoItemDAO.java` foi atualizado para:
  - carregar os microserviços ao consultar itens (método `carregarMicroServicos`).
  - persistir microserviços ao inserir (`inserirMicroServicosAssociados`).
  - remover e reinserir microserviços ao atualizar o item.
  - remover microserviços associados ao deletar o item (antes de deletar o item).

Exemplos de seed adicionados ao SQL:
- Para o item 2 (Formatação de Computador) foi inserido o microserviço `NOTURNO`.
- Para o item 3 (Troca de Tela de Celular) foram inseridos `GARANTIA` e `URGENTE`.

Arquivos adicionais modificados:
- src/main/resources/banco/banco.sql (nova tabela e inserts de exemplo)
- src/main/java/.../dao/OrcamentoItemDAO.java (persistência de microserviços)

Recomendações finais:
- Rodar a seed SQL em um banco MySQL local para popular as tabelas e verificar o comportamento.
- Ajustar validação na fábrica se quiser rejeitar tipos inválidos em vez de ignorar.


Fim da documentação.


