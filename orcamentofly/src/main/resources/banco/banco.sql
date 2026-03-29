drop database banco;

CREATE DATABASE IF NOT EXISTS banco;

USE banco;

CREATE TABLE produtos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          valorUnitario DECIMAL(10,2) NOT NULL,
                          estoque INT
);

INSERT INTO produtos (id, nome, descricao, valorUnitario, estoque) VALUES
                                                                       (1, 'Teclado', "Teclado mecânico RGB switch azul", 120.00, 50),
                                                                       (2, 'Mouse', "Mouse gamer com sensor óptico 16000 DPI", 70.00, 30);

CREATE TABLE servicos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          valorUnitario DECIMAL(10,2) NOT NULL
);

INSERT INTO servicos (nome, descricao, valorUnitario) VALUES
                                                          ('Formatação de Computador', 'Instalação do sistema operacional e drivers', 150.00),
                                                          ('Troca de Tela de Celular', 'Substituição de display danificado', 300.00);

CREATE TABLE orcamentos (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            cliente VARCHAR(255) NOT NULL,
                            dataOrcamento DATE NOT NULL,
                            observacao TEXT,
                            valorTotal DECIMAL(10,2) NOT NULL
);

CREATE TABLE orcamento_item (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                orcamento_id INT NOT NULL,
                                descricao VARCHAR(255),
                                tipoOrcamentoItem VARCHAR(50) NOT NULL,
                                quantidade INT NOT NULL,
                                valorUnitario DECIMAL(10, 2) NOT NULL,
                                subtotal DECIMAL(10, 2) NOT NULL,
                                produto_id INT,
                                servico_id INT,
                                FOREIGN KEY (orcamento_id) REFERENCES orcamentos(id) ON DELETE CASCADE,
                                FOREIGN KEY (produto_id) REFERENCES produtos(id) ON DELETE SET NULL,
                                FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE SET NULL
);

INSERT INTO orcamentos (cliente, dataOrcamento, observacao, valorTotal) VALUES
                                                                            ('Mateus', '2026-03-29', 'Orçamento para manutenção de PC', 450.00),
                                                                            ('João', '2026-03-30', 'Orçamento para conserto de celular', 300.00);

INSERT INTO orcamento_item
(descricao, tipoOrcamentoItem, quantidade, valorUnitario, subtotal, orcamento_id, produto_id, servico_id)
VALUES
    ('Mouse Gamer RGB', 'PRODUTO', 1, 89.90, 89.90, 1, 1, NULL),
    ('Formatação de Computador', 'SERVICO', 1, 150.00, 150.00, 1, NULL, 1),
    ('Troca de Tela de Celular', 'SERVICO', 1, 300.00, 300.00, 2, NULL, 2);
