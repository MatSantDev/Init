create database banco;

use banco;

CREATE TABLE produtos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          valorUnitario DECIMAL(10,2) NOT NULL,
                          estoque INT
);

    INSERT INTO produtos (id, nome, descricao, valorUnitario, estoque) VALUES
(1, 'Teclado', Teclado mecânico RGB switch azul, 120.00, 50),
(2, 'Mouse', Mouse gamer com sensor óptico 16000 DPI, 70.00, 30);

CREATE TABLE servicos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          valorUnitario DECIMAL(10,2) NOT NULL
);

INSERT INTO servicos (nome, descricao, valorUnitario) VALUES
                                                          ('Formatação de Computador', 'Instalação do sistema operacional e drivers', 150.00),
                                                          ('Troca de Tela de Celular', 'Substituição de display danificado', 300.00);

