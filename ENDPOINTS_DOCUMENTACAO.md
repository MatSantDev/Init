# 📋 Documentação de Endpoints - OrcamentoFly API

**Base URL:** `http://localhost:8080`

---

## 📑 Índice
1. [Clientes](#clientes)
2. [Serviços](#serviços)
3. [Produtos](#produtos)
4. [Orçamentos](#orçamentos)
5. [Items do Orçamento](#items-do-orçamento)
6. [Códigos de Status HTTP](#códigos-de-status-http)
7. [Enums](#enums)

---

## 🧑 CLIENTES

### 1. Consultar Todos os Clientes
**Método:** `GET`  
**URL:** `http://localhost:8080/clientes/consultarTodos`  
**Body:** (vazio)

**Response (200 - OK):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "11987654321",
    "cpf": "12345678901",
    "cep": "01310100",
    "endereco": "Rua Augusta, 1000",
    "sexo": "M",
    "dataNascimento": "1990-05-15",
    "criadoEm": "2024-01-10"
  }
]
```

**Códigos de Status:**
- `200 OK` - Clientes retornados com sucesso
- `404 Not Found` - Nenhum cliente encontrado

---

### 2. Consultar Cliente por ID
**Método:** `GET`  
**URL:** `http://localhost:8080/clientes/consultarById/{id}`  
**Parâmetro:** `id` (integer) - ID do cliente  
**Body:** (vazio)

**Response (200 - OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "telefone": "11987654321",
  "cpf": "12345678901",
  "cep": "01310100",
  "endereco": "Rua Augusta, 1000",
  "sexo": "M",
  "dataNascimento": "1990-05-15",
  "criadoEm": "2024-01-10"
}
```

**Códigos de Status:**
- `200 OK` - Cliente retornado com sucesso
- `400 Bad Request` - ID do cliente inválido (≤ 0)
- `404 Not Found` - Cliente com ID informado não encontrado

---

### 3. Inserir Novo Cliente
**Método:** `POST`  
**URL:** `http://localhost:8080/clientes/inserir`  
**Body:**
```json
{
  "nome": "Maria Santos",
  "email": "maria@example.com",
  "telefone": "11999999999",
  "cpf": "98765432109",
  "cep": "01310101",
  "endereco": "Rua Flores, 500",
  "sexo": "F",
  "dataNascimento": "1988-03-20"
}
```

**Response (201 - CREATED):**
```
(vazio)
```

**Códigos de Status:**
- `201 Created` - Cliente inserido com sucesso
- `400 Bad Request` - Nome, email ou CPF obrigatórios não informados

---

### 4. Atualizar Cliente
**Método:** `PUT`  
**URL:** `http://localhost:8080/clientes/atualizar`  
**Body:**
```json
{
  "id": 1,
  "nome": "João Silva Atualizado",
  "email": "joao.atualizado@example.com",
  "telefone": "11987654321",
  "cpf": "12345678901",
  "cep": "01310100",
  "endereco": "Rua Augusta, 2000",
  "sexo": "M",
  "dataNascimento": "1990-05-15"
}
```

**Response (200 - OK):**
```
(vazio)
```

**Códigos de Status:**
- `200 OK` - Cliente atualizado com sucesso
- `400 Bad Request` - ID do cliente inválido ou nome obrigatório não informado
- `404 Not Found` - Cliente com ID informado não encontrado

---

### 5. Deletar Cliente
**Método:** `DELETE`  
**URL:** `http://localhost:8080/clientes/deletar/{id}`  
**Parâmetro:** `id` (integer) - ID do cliente  
**Body:** (vazio)

**Response (204 - NO CONTENT):**
```
(vazio)
```

**Códigos de Status:**
- `204 No Content` - Cliente deletado com sucesso
- `400 Bad Request` - ID do cliente inválido (≤ 0)
- `404 Not Found` - Cliente com ID informado não encontrado

---

## 🔧 SERVIÇOS

### 1. Consultar Todos os Serviços
**Método:** `GET`  
**URL:** `http://localhost:8080/servicos/consultarTodos`  
**Body:** (vazio)

**Response (200 - OK):**
```json
[
  {
    "id": 1,
    "nome": "Serviço de Consultoria",
    "descricao": "Consultoria profissional em negócios",
    "valorUnitario": 150.00
  }
]
```

**Códigos de Status:**
- `200 OK` - Serviços retornados com sucesso
- `404 Not Found` - Nenhum serviço encontrado

---

### 2. Consultar Serviço por ID
**Método:** `GET`  
**URL:** `http://localhost:8080/servicos/consultarById/{id}`  
**Parâmetro:** `id` (integer) - ID do serviço  
**Body:** (vazio)

**Response (200 - OK):**
```json
{
  "id": 1,
  "nome": "Serviço de Consultoria",
  "descricao": "Consultoria profissional em negócios",
  "valorUnitario": 150.00
}
```

**Códigos de Status:**
- `200 OK` - Serviço retornado com sucesso
- `400 Bad Request` - ID do serviço inválido (≤ 0)
- `404 Not Found` - Serviço com ID informado não encontrado

---

### 3. Inserir Novo Serviço
**Método:** `POST`  
**URL:** `http://localhost:8080/servicos/inserir`  
**Body:**
```json
{
  "nome": "Serviço de Install",
  "descricao": "Serviço de instalação de software",
  "valorUnitario": 200.00
}
```

**Response (201 - CREATED):**
```
(vazio)
```

**Códigos de Status:**
- `201 Created` - Serviço inserido com sucesso
- `400 Bad Request` - Serviço obrigatório não informado

---

### 4. Atualizar Serviço
**Método:** `PUT`  
**URL:** `http://localhost:8080/servicos/atualizar`  
**Body:**
```json
{
  "id": 1,
  "nome": "Serviço de Consultoria Atualizado",
  "descricao": "Consultoria profissional em negócios - Versão 2",
  "valorUnitario": 180.00
}
```

**Response (200 - OK):**
```
(vazio)
```

**Códigos de Status:**
- `200 OK` - Serviço atualizado com sucesso
- `400 Bad Request` - Serviço obrigatório não informado
- `404 Not Found` - Serviço com ID informado não encontrado

---

### 5. Deletar Serviço
**Método:** `DELETE`  
**URL:** `http://localhost:8080/servicos/deletar/{id}`  
**Parâmetro:** `id` (integer) - ID do serviço  
**Body:** (vazio)

**Response (204 - NO CONTENT):**
```
(vazio)
```

**Códigos de Status:**
- `204 No Content` - Serviço deletado com sucesso
- `400 Bad Request` - ID do serviço inválido
- `404 Not Found` - Serviço com ID informado não encontrado

---

## 📦 PRODUTOS

### 1. Consultar Todos os Produtos
**Método:** `GET`  
**URL:** `http://localhost:8080/produtos/consultarTodos`  
**Body:** (vazio)

**Response (200 - OK):**
```json
[
  {
    "id": 1,
    "nome": "Notebook Dell",
    "descricao": "Notebook 15 polegadas",
    "valorUnitario": 3500.00,
    "estoque": 10
  }
]
```

**Códigos de Status:**
- `200 OK` - Produtos retornados com sucesso
- `404 Not Found` - Nenhum produto encontrado

---

### 2. Consultar Produto por ID
**Método:** `GET`  
**URL:** `http://localhost:8080/produtos/consultarById/{id}`  
**Parâmetro:** `id` (integer) - ID do produto  
**Body:** (vazio)

**Response (200 - OK):**
```json
{
  "id": 1,
  "nome": "Notebook Dell",
  "descricao": "Notebook 15 polegadas",
  "valorUnitario": 3500.00,
  "estoque": 10
}
```

**Códigos de Status:**
- `200 OK` - Produto retornado com sucesso
- `400 Bad Request` - ID do produto inválido (≤ 0)
- `404 Not Found` - Produto com ID informado não encontrado

---

### 3. Inserir Novo Produto
**Método:** `POST`  
**URL:** `http://localhost:8080/produtos/inserir`  
**Body:**
```json
{
  "nome": "Mouse Logitech",
  "descricao": "Mouse sem fio",
  "valorUnitario": 89.90,
  "estoque": 50
}
```

**Response (201 - CREATED):**
```
(vazio)
```

**Códigos de Status:**
- `201 Created` - Produto inserido com sucesso
- `400 Bad Request` - Produto obrigatório não informado

---

### 4. Atualizar Produto
**Método:** `PUT`  
**URL:** `http://localhost:8080/produtos/atualizar`  
**Body:**
```json
{
  "id": 1,
  "nome": "Notebook Dell Atualizado",
  "descricao": "Notebook 17 polegadas",
  "valorUnitario": 3999.90,
  "estoque": 15
}
```

**Response (200 - OK):**
```
(vazio)
```

**Códigos de Status:**
- `200 OK` - Produto atualizado com sucesso
- `400 Bad Request` - Produto obrigatório não informado
- `404 Not Found` - Produto com ID informado não encontrado

---

### 5. Deletar Produto
**Método:** `DELETE`  
**URL:** `http://localhost:8080/produtos/deletar/{id}`  
**Parâmetro:** `id` (integer) - ID do produto  
**Body:** (vazio)

**Response (204 - NO CONTENT):**
```
(vazio)
```

**Códigos de Status:**
- `204 No Content` - Produto deletado com sucesso
- `400 Bad Request` - ID do produto inválido
- `404 Not Found` - Produto com ID informado não encontrado

---

## 📊 ORÇAMENTOS

### 1. Consultar Todos os Orçamentos
**Método:** `GET`  
**URL:** `http://localhost:8080/orcamentos/consultarTodos`  
**Body:** (vazio)

**Response (200 - OK):**
```json
[
  {
    "id": 1,
    "dataOrcamento": "2024-06-09",
    "observacao": "Orçamento para novo cliente",
    "valorTotal": 5000.00,
    "status": "PENDENTE",
    "cliente": {
      "id": 1,
      "nome": "João Silva",
      "email": "joao@example.com",
      "telefone": "11987654321",
      "cpf": "12345678901",
      "cep": "01310100",
      "endereco": "Rua Augusta, 1000",
      "sexo": "M",
      "dataNascimento": "1990-05-15",
      "criadoEm": "2024-01-10"
    },
    "itens": []
  }
]
```

**Códigos de Status:**
- `200 OK` - Orçamentos retornados com sucesso
- `404 Not Found` - Nenhum orçamento encontrado

---

### 2. Consultar Orçamento por ID
**Método:** `GET`  
**URL:** `http://localhost:8080/orcamentos/consultarById/{id}`  
**Parâmetro:** `id` (integer) - ID do orçamento  
**Body:** (vazio)

**Response (200 - OK):**
```json
{
  "id": 1,
  "dataOrcamento": "2024-06-09",
  "observacao": "Orçamento para novo cliente",
  "valorTotal": 5000.00,
  "status": "PENDENTE",
  "cliente": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "11987654321",
    "cpf": "12345678901",
    "cep": "01310100",
    "endereco": "Rua Augusta, 1000",
    "sexo": "M",
    "dataNascimento": "1990-05-15",
    "criadoEm": "2024-01-10"
  },
  "itens": []
}
```

**Códigos de Status:**
- `200 OK` - Orçamento retornado com sucesso
- `400 Bad Request` - ID do orçamento inválido (≤ 0)
- `404 Not Found` - Orçamento com ID informado não encontrado

---

### 3. Inserir Novo Orçamento
**Método:** `POST`  
**URL:** `http://localhost:8080/orcamentos/inserir`  
**Body:**
```json
{
  "dataOrcamento": "2024-06-09",
  "observacao": "Novo orçamento de cliente",
  "valorTotal": 7500.00,
  "status": "PENDENTE",
  "cliente": {
    "id": 1
  }
}
```

**Response (201 - CREATED):**
```
(vazio)
```

**Códigos de Status:**
- `201 Created` - Orçamento inserido com sucesso
- `400 Bad Request` - Orçamento obrigatório não informado

---

### 4. Atualizar Orçamento
**Método:** `PUT`  
**URL:** `http://localhost:8080/orcamentos/atualizar`  
**Body:**
```json
{
  "id": 1,
  "dataOrcamento": "2024-06-09",
  "observacao": "Orçamento atualizado",
  "valorTotal": 8000.00,
  "status": "APROVADO",
  "cliente": {
    "id": 1
  }
}
```

**Response (200 - OK):**
```
(vazio)
```

**Códigos de Status:**
- `200 OK` - Orçamento atualizado com sucesso
- `400 Bad Request` - Orçamento obrigatório não informado
- `404 Not Found` - Orçamento com ID informado não encontrado

---

### 5. Deletar Orçamento
**Método:** `DELETE`  
**URL:** `http://localhost:8080/orcamentos/deletar/{id}`  
**Parâmetro:** `id` (integer) - ID do orçamento  
**Body:** (vazio)

**Response (204 - NO CONTENT):**
```
(vazio)
```

**Códigos de Status:**
- `204 No Content` - Orçamento deletado com sucesso
- `400 Bad Request` - ID do orçamento inválido
- `404 Not Found` - Orçamento com ID informado não encontrado

---

## 🎁 ITEMS DO ORÇAMENTO

### 1. Consultar Todos os Items do Orçamento por ID
**Método:** `GET`  
**URL:** `http://localhost:8080/orcamentos/{orcamentoId}/itens/consultarTodosByOrcamentoId`  
**Parâmetro:** `orcamentoId` (integer) - ID do orçamento  
**Body:** (vazio)

**Response (200 - OK):**
```json
[
  {
    "id": 1,
    "descricao": "Item de orçamento",
    "tipoOrcamentoItem": "PRODUTO",
    "quantidade": 2,
    "valorUnitario": 1500.00,
    "subtotal": 3000.00,
    "orcamento": {
      "id": 1
    },
    "produto": {
      "id": 1,
      "nome": "Notebook Dell",
      "descricao": "Notebook 15 polegadas",
      "valorUnitario": 1500.00,
      "estoque": 10
    },
    "servico": null
  }
]
```

**Códigos de Status:**
- `200 OK` - Items retornados com sucesso
- `400 Bad Request` - ID do orçamento inválido (≤ 0)
- `404 Not Found` - Nenhum item encontrado para o orçamento

---

### 2. Consultar Items do Orçamento por Objeto Orçamento
**Método:** `GET`  
**URL:** `http://localhost:8080/orcamentos/{orcamentoId}/itens/consultarTodosByOrcamento`  
**Parâmetro:** `orcamentoId` (integer) - ID do orçamento  
**Body:**
```json
{
  "id": 1
}
```

**Response (200 - OK):**
```json
[
  {
    "id": 1,
    "descricao": "Item de orçamento",
    "tipoOrcamentoItem": "PRODUTO",
    "quantidade": 2,
    "valorUnitario": 1500.00,
    "subtotal": 3000.00,
    "orcamento": {
      "id": 1
    },
    "produto": {
      "id": 1,
      "nome": "Notebook Dell",
      "descricao": "Notebook 15 polegadas",
      "valorUnitario": 1500.00,
      "estoque": 10
    },
    "servico": null
  }
]
```

**Códigos de Status:**
- `200 OK` - Items retornados com sucesso
- `400 Bad Request` - Orçamento inválido
- `404 Not Found` - Nenhum item encontrado para o orçamento

---

### 3. Consultar Item do Orçamento por ID
**Método:** `GET`  
**URL:** `http://localhost:8080/orcamentos/{orcamentoId}/itens/consultarById/{id}`  
**Parâmetros:** 
- `orcamentoId` (integer) - ID do orçamento
- `id` (integer) - ID do item  
**Body:** (vazio)

**Response (200 - OK):**
```json
{
  "id": 1,
  "descricao": "Item de orçamento",
  "tipoOrcamentoItem": "PRODUTO",
  "quantidade": 2,
  "valorUnitario": 1500.00,
  "subtotal": 3000.00,
  "orcamento": {
    "id": 1
  },
  "produto": {
    "id": 1,
    "nome": "Notebook Dell",
    "descricao": "Notebook 15 polegadas",
    "valorUnitario": 1500.00,
    "estoque": 10
  },
  "servico": null
}
```

**Códigos de Status:**
- `200 OK` - Item retornado com sucesso
- `400 Bad Request` - ID do item inválido (≤ 0)
- `404 Not Found` - Item com ID informado não encontrado

---

### 4. Inserir Item no Orçamento
**Método:** `POST`  
**URL:** `http://localhost:8080/orcamentos/{orcamentoId}/itens/inserir`  
**Parâmetro:** `orcamentoId` (integer) - ID do orçamento  
**Body:**
```json
{
  "descricao": "Novo item",
  "tipoOrcamentoItem": "PRODUTO",
  "quantidade": 3,
  "valorUnitario": 2000.00,
  "subtotal": 6000.00,
  "orcamento": {
    "id": 1
  },
  "produto": {
    "id": 1
  }
}
```

**Response (201 - CREATED):**
```json
{
  "id": 1,
  "descricao": "Novo item",
  "tipoOrcamentoItem": "PRODUTO",
  "quantidade": 3,
  "valorUnitario": 2000.00,
  "subtotal": 6000.00,
  "orcamento": {
    "id": 1
  },
  "produto": {
    "id": 1
  }
}
```

**Códigos de Status:**
- `201 Created` - Item inserido com sucesso
- `400 Bad Request` - Item do orçamento obrigatório não informado

---

### 5. Atualizar Item do Orçamento
**Método:** `PUT`  
**URL:** `http://localhost:8080/orcamentos/{orcamentoId}/itens/atualizar/{id}`  
**Parâmetros:** 
- `orcamentoId` (integer) - ID do orçamento
- `id` (integer) - ID do item  
**Body:**
```json
{
  "descricao": "Item atualizado",
  "tipoOrcamentoItem": "PRODUTO",
  "quantidade": 5,
  "valorUnitario": 2500.00,
  "subtotal": 12500.00,
  "orcamento": {
    "id": 1
  },
  "produto": {
    "id": 1
  }
}
```

**Response (200 - OK):**
```json
{
  "descricao": "Item atualizado",
  "tipoOrcamentoItem": "PRODUTO",
  "quantidade": 5,
  "valorUnitario": 2500.00,
  "subtotal": 12500.00,
  "orcamento": {
    "id": 1
  },
  "produto": {
    "id": 1
  }
}
```

**Códigos de Status:**
- `200 OK` - Item atualizado com sucesso
- `400 Bad Request` - Item do orçamento obrigatório não informado
- `404 Not Found` - Item não encontrado

---

### 6. Deletar Item do Orçamento
**Método:** `DELETE`  
**URL:** `http://localhost:8080/orcamentos/{orcamentoId}/itens/deletar/{id}`  
**Parâmetros:** 
- `orcamentoId` (integer) - ID do orçamento
- `id` (integer) - ID do item  
**Body:** (vazio)

**Response (204 - NO CONTENT):**
```
(vazio)
```

**Códigos de Status:**
- `204 No Content` - Item deletado com sucesso
- `400 Bad Request` - Item inválido
- `404 Not Found` - Item não encontrado

---

## 🔢 Códigos de Status HTTP

| Código | Descrição | Significado |
|--------|-----------|-------------|
| **200** | OK | Requisição bem sucedida |
| **201** | Created | Recurso criado com sucesso |
| **204** | No Content | Requisição bem sucedida sem corpo na resposta |
| **400** | Bad Request | Erro na requisição (dados inválidos ou obrigatórios faltando) |
| **404** | Not Found | Recurso não encontrado |
| **500** | Internal Server Error | Erro interno do servidor |

---

## 📚 Enums

### StatusOrcamento
Possíveis valores para o status de um orçamento:
- `PENDENTE` - Orçamento aguardando análise
- `APROVADO` - Orçamento aprovado pelo cliente
- `REJEITADO` - Orçamento rejeitado
- `CANCELADO` - Orçamento cancelado
- `CONCLUIDO` - Orçamento concluído

### TipoOrcamentoItem
Possíveis valores para o tipo de item do orçamento:
- `PRODUTO` - Item é um produto
- `SERVICO` - Item é um serviço

---

## 💡 Exemplos de Uso com cURL

### Consultar todos os clientes
```bash
curl -X GET http://localhost:8080/clientes/consultarTodos
```

### Criar um novo cliente
```bash
curl -X POST http://localhost:8080/clientes/inserir \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "email": "maria@example.com",
    "telefone": "11999999999",
    "cpf": "98765432109",
    "cep": "01310101",
    "endereco": "Rua Flores, 500",
    "sexo": "F",
    "dataNascimento": "1988-03-20"
  }'
```

### Atualizar um cliente
```bash
curl -X PUT http://localhost:8080/clientes/atualizar \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nome": "João Silva Atualizado",
    "email": "joao.atualizado@example.com",
    "telefone": "11987654321",
    "cpf": "12345678901",
    "cep": "01310100",
    "endereco": "Rua Augusta, 2000",
    "sexo": "M",
    "dataNascimento": "1990-05-15"
  }'
```

### Deletar um cliente
```bash
curl -X DELETE http://localhost:8080/clientes/deletar/1
```

---

## ⚠️ Observações Importantes

1. **Validações obrigatórias:**
   - Cliente: nome, email e CPF são obrigatórios na criação
   - Serviço: não pode ser nulo
   - Produto: não pode ser nulo
   - Orçamento: não pode ser nulo
   - Item do Orçamento: não pode ser nulo

2. **IDs:**
   - Todos os IDs devem ser maior que 0 (> 0)
   - IDs negativos ou zero retornam erro `400 Bad Request`

3. **Formatos de Data:**
   - Datas devem estar no formato: `YYYY-MM-DD`
   - Exemplo: `2024-06-09`

4. **Content-Type:**
   - Todas as requisições POST, PUT e DELETE devem ter header: `Content-Type: application/json`

5. **Tratamento de Erros:**
   - Quando ocorre um erro, a API retorna um código HTTP apropriado
   - Verifique o código de status HTTP para identificar o tipo de erro

---

**Última atualização:** 09/06/2024  
**Versão:** 1.0

