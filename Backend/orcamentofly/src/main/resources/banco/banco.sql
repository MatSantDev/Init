create database banco;

use banco;

CREATE TABLE produtos (
  id INT PRIMARY KEY,
  nome VARCHAR(100),
  descricao VARCHAR(100),
  preco DECIMAL(10,2)
);

INSERT INTO produtos (id, nome, descricao, preco) VALUES
	( 1, 'Teclado Gamer', 'Teclado muito top', 120.00 ),
	( 2, 'Mouse RGB', 'Mouse de qualidade', 50.00 ),
	( 3, 'Monitor 24" IPS', 'Monitor Full HD com taxa de atualização de 144Hz e bordas finas', 850.00 ),
	( 4, 'Headset Gamer 7.1', 'Fone de ouvido com som surround e microfone com cancelamento de ruído', 250.00 ),
	( 5, 'Mousepad Gigante', 'Mousepad emborrachado com bordas costuradas, tamanho 90x40cm', 75.00 ),
	( 6, 'Cadeira Ergonômica', 'Cadeira de escritório com suporte lombar, encosto de cabeça e ajuste de altura', 950.00 ),
	( 7, 'Webcam Full HD', 'Câmera 1080p com microfone embutido ideal para videoconferências e streams', 180.00 ),
	( 8, 'Microfone Condensador', 'Microfone de estúdio USB com braço articulado e pop filter', 320.00 ),
	( 9, 'Placa de Vídeo RTX 3060', 'GPU de alta performance com 12GB de VRAM GDDR6 e Ray Tracing', 1850.00 ),
	( 10, 'Processador Octa-Core', 'CPU de última geração com clock base de 3.8GHz', 1200.00 ),
	( 11, 'SSD NVMe 1TB', 'Armazenamento ultrarrápido com taxa de leitura de 3500MB/s', 450.00 ),
	( 12, 'Memória RAM 16GB', 'Módulo DDR4 de 3200MHz com dissipador de calor em alumínio', 280.00 ),
	( 13, 'Placa Mãe B550', 'Placa-mãe compatível com processadores AM4 e suporte a PCIe 4.0', 750.00 ),
	( 14, 'Fonte 650W 80 Plus', 'Fonte de alimentação com certificação Bronze e PFC ativo', 350.00 ),
	( 15, 'Gabinete Mid Tower', 'Gabinete com lateral em vidro temperado e 3 fans RGB inclusos', 420.00 ),
	( 16, 'Roteador Wi-Fi 6', 'Roteador dual-band gigabit com suporte a dezenas de conexões simultâneas', 310.00 ),
	( 17, 'Filtro de Linha 8 Tomadas', 'Protetor eletrônico com fusível de segurança e cabo de 2 metros', 65.00 ),
	( 18, 'Suporte Articulado para Monitor', 'Braço de mesa com pistão a gás para telas de 17 a 32 polegadas', 210.00 ),
	( 19, 'Hub USB-C 7 em 1', 'Adaptador com portas HDMI, USB 3.0, leitor de cartões SD e MicroSD', 140.00 ),
	( 20, 'Cabo HDMI 2.1', 'Cabo trançado em nylon de 2 metros com suporte a resolução 4K a 120Hz', 45.00 ),
	( 21, 'Kit de Chaves de Precisão', 'Conjunto com 115 peças magnetizadas para manutenção de eletrônicos', 85.00 ),
	( 22, 'Fita LED Inteligente', 'Rolo de 5 metros RGB controlável via Wi-Fi e assistentes de voz', 110.00 ),
	( 23, 'Pasta Térmica de Prata', 'Seringa de 4g com alta condutividade térmica para processadores e placas de vídeo', 35.00 )
;

SELECT * FROM produtos;

ALTER TABLE produtos ADD nome VARCHAR(100);

DROP TABLE produtos;
