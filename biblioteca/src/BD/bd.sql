CREATE DATABASE orcamento_db;
USE orcamento_db;

-- =========================
-- Tabela model.Produto
-- =========================
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valorUnitario DECIMAL(10,2) NOT NULL,
    estoque INT DEFAULT 0
);

-- =========================
-- Tabela model.Serviço
-- =========================
CREATE TABLE servico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valorUnitario DECIMAL(10,2) NOT NULL
);

-- =========================
-- Tabela Orçamento
-- =========================
CREATE TABLE orcamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL,
    dataOrcamento DATE NOT NULL,
    observacao TEXT,
    valorTotal DECIMAL(10,2) DEFAULT 0
);

-- =========================
-- Tabela Item do Orçamento
-- =========================
CREATE TABLE orcamentoItem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orcamento_id INT NOT NULL,
    tipoOrcamentoItem ENUM('produto', 'servico') NOT NULL,
    produto_id INT NULL,
    servico_id INT NULL,
    quantidade INT NOT NULL,
    valorUnitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (id_orcamento) REFERENCES orcamento(id_orcamento) ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    FOREIGN KEY (id_servico) REFERENCES servico(id_servico)
);

-- =========================
-- Dados de exemplo (opcional)
-- =========================
INSERT INTO produto (nome, descricao, valor_unitario, estoque) VALUES
('Mouse Gamer', 'Mouse RGB', 80.00, 10),
('Teclado Mecânico', 'Teclado com switch azul', 250.00, 5);

INSERT INTO servico (nome, descricao, valor_unitario) VALUES
('Formatação', 'Formatação de computador', 120.00),
('Instalação de Software', 'Instalação de programas', 50.00);