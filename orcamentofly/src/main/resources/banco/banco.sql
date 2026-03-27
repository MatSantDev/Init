create database banco;

use banco;

CREATE TABLE produtos (
                          id INT PRIMARY KEY,
                          descricao VARCHAR(100),
                          preco DECIMAL(10,2)
);

    INSERT INTO produtos (id, descricao, preco) VALUES
(1, 'Teclado', 120.00),
(2, 'Mouse', 50.00);
