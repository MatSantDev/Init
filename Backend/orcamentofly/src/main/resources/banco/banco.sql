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

INSERT INTO produtos ( id, nome, descricao, valorUnitario, estoque ) VALUES
<<<<<<< HEAD
	( 1, 'Teclado Gamer', 'Teclado muito top', 120.00, 12 ),
	( 2, 'Mouse RGB', 'Mouse de qualidade', 50.00, 12 ),
	( 3, 'Monitor 24" IPS', 'Monitor Full HD com taxa de atualização de 144Hz e bordas finas', 850.00, 12 ),
	( 4, 'Headset Gamer 7.1', 'Fone de ouvido com som surround e microfone com cancelamento de ruído', 250.00, 12 ),
	( 5, 'Mousepad Gigante', 'Mousepad emborrachado com bordas costuradas, tamanho 90x40cm', 75.00, 12 ),
	( 6, 'Cadeira Ergonômica', 'Cadeira de escritório com suporte lombar, encosto de cabeça e ajuste de altura', 950.00, 12 ),
	( 7, 'Webcam Full HD', 'Câmera 1080p com microfone embutido ideal para videoconferências e streams', 180.00, 12 ),
	( 8, 'Microfone Condensador', 'Microfone de estúdio USB com braço articulado e pop filter', 320.00, 12 ),
	( 9, 'Placa de Vídeo RTX 3060', 'GPU de alta performance com 12GB de VRAM GDDR6 e Ray Tracing', 1850.00, 12 ),
	( 10, 'Processador Octa-Core', 'CPU de última geração com clock base de 3.8GHz', 1200.00, 12 ),
	( 11, 'SSD NVMe 1TB', 'Armazenamento ultrarrápido com taxa de leitura de 3500MB/s', 450.00, 12 ),
	( 12, 'Memória RAM 16GB', 'Módulo DDR4 de 3200MHz com dissipador de calor em alumínio', 280.00, 12 ),
	( 13, 'Placa Mãe B550', 'Placa-mãe compatível com processadores AM4 e suporte a PCIe 4.0', 750.00, 12 ),
	( 14, 'Fonte 650W 80 Plus', 'Fonte de alimentação com certificação Bronze e PFC ativo', 350.00, 12 ),
	( 15, 'Gabinete Mid Tower', 'Gabinete com lateral em vidro temperado e 3 fans RGB inclusos', 420.00, 12 ),
	( 16, 'Roteador Wi-Fi 6', 'Roteador dual-band gigabit com suporte a dezenas de conexões simultâneas', 310.00, 12 ),
	( 17, 'Filtro de Linha 8 Tomadas', 'Protetor eletrônico com fusível de segurança e cabo de 2 metros', 65.00, 12 ),
	( 18, 'Suporte Articulado para Monitor', 'Braço de mesa com pistão a gás para telas de 17 a 32 polegadas', 210.00, 12 ),
	( 19, 'Hub USB-C 7 em 1', 'Adaptador com portas HDMI, USB 3.0, leitor de cartões SD e MicroSD', 140.00, 12 ),
	( 20, 'Cabo HDMI 2.1', 'Cabo trançado em nylon de 2 metros com suporte a resolução 4K a 120Hz', 45.00, 12 ),
	( 21, 'Kit de Chaves de Precisão', 'Conjunto com 115 peças magnetizadas para manutenção de eletrônicos', 85.00, 12 ),
	( 22, 'Fita LED Inteligente', 'Rolo de 5 metros RGB controlável via Wi-Fi e assistentes de voz', 110.00, 12 ),
	( 23, 'Pasta Térmica de Prata', 'Seringa de 4g com alta condutividade térmica para processadores e placas de vídeo', 35.00, 12 )
=======
                                                                         ( 1, 'Teclado Gamer', 'Teclado muito top', 120.00, 12 ),
                                                                         ( 2, 'Mouse RGB', 'Mouse de qualidade', 50.00, 12 ),
                                                                         ( 3, 'Monitor 24" IPS', 'Monitor Full HD com taxa de atualização de 144Hz e bordas finas', 850.00, 12 ),
                                                                         ( 4, 'Headset Gamer 7.1', 'Fone de ouvido com som surround e microfone com cancelamento de ruído', 250.00, 12 ),
                                                                         ( 5, 'Mousepad Gigante', 'Mousepad emborrachado com bordas costuradas, tamanho 90x40cm', 75.00, 12 ),
                                                                         ( 6, 'Cadeira Ergonômica', 'Cadeira de escritório com suporte lombar, encosto de cabeça e ajuste de altura', 950.00, 12 ),
                                                                         ( 7, 'Webcam Full HD', 'Câmera 1080p com microfone embutido ideal para videoconferências e streams', 180.00, 12 ),
                                                                         ( 8, 'Microfone Condensador', 'Microfone de estúdio USB com braço articulado e pop filter', 320.00, 12 ),
                                                                         ( 9, 'Placa de Vídeo RTX 3060', 'GPU de alta performance com 12GB de VRAM GDDR6 e Ray Tracing', 1850.00, 12 ),
                                                                         ( 10, 'Processador Octa-Core', 'CPU de última geração com clock base de 3.8GHz', 1200.00, 12 ),
                                                                         ( 11, 'SSD NVMe 1TB', 'Armazenamento ultrarrápido com taxa de leitura de 3500MB/s', 450.00, 12 ),
                                                                         ( 12, 'Memória RAM 16GB', 'Módulo DDR4 de 3200MHz com dissipador de calor em alumínio', 280.00, 12 ),
                                                                         ( 13, 'Placa Mãe B550', 'Placa-mãe compatível com processadores AM4 e suporte a PCIe 4.0', 750.00, 12 ),
                                                                         ( 14, 'Fonte 650W 80 Plus', 'Fonte de alimentação com certificação Bronze e PFC ativo', 350.00, 12 ),
                                                                         ( 15, 'Gabinete Mid Tower', 'Gabinete com lateral em vidro temperado e 3 fans RGB inclusos', 420.00, 12 ),
                                                                         ( 16, 'Roteador Wi-Fi 6', 'Roteador dual-band gigabit com suporte a dezenas de conexões simultâneas', 310.00, 12 ),
                                                                         ( 17, 'Filtro de Linha 8 Tomadas', 'Protetor eletrônico com fusível de segurança e cabo de 2 metros', 65.00, 12 ),
                                                                         ( 18, 'Suporte Articulado para Monitor', 'Braço de mesa com pistão a gás para telas de 17 a 32 polegadas', 210.00, 12 ),
                                                                         ( 19, 'Hub USB-C 7 em 1', 'Adaptador com portas HDMI, USB 3.0, leitor de cartões SD e MicroSD', 140.00, 12 ),
                                                                         ( 20, 'Cabo HDMI 2.1', 'Cabo trançado em nylon de 2 metros com suporte a resolução 4K a 120Hz', 45.00, 12 ),
                                                                         ( 21, 'Kit de Chaves de Precisão', 'Conjunto com 115 peças magnetizadas para manutenção de eletrônicos', 85.00, 12 ),
                                                                         ( 22, 'Fita LED Inteligente', 'Rolo de 5 metros RGB controlável via Wi-Fi e assistentes de voz', 110.00, 12 ),
                                                                         ( 23, 'Pasta Térmica de Prata', 'Seringa de 4g com alta condutividade térmica para processadores e placas de vídeo', 35.00, 12 )
>>>>>>> fe0670de5e849ef8690c3b82d807661a47f1f6b2
;

CREATE TABLE servicos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          valorUnitario DECIMAL(10,2) NOT NULL
);

INSERT INTO servicos (nome, descricao, valorUnitario) VALUES
                                                          ('Consultoria TI', 'Análise de infraestrutura e melhorias', 250.00),
                                                          ('Recuperação de Dados', 'Recuperação de arquivos deletados ou HDs corrompidos', 450.00),
                                                          ('Instalação de Rede', 'Cabeamento estruturado e configuração de roteadores', 350.00),
                                                          ('Limpeza Preventiva', 'Limpeza física e troca de pasta térmica', 120.00),
                                                          ('Upgrade de Hardware', 'Instalação de SSD, RAM ou Placa de Vídeo', 100.00),
                                                          ('Remoção de Malware', 'Limpeza profunda de vírus e spywares', 150.00),
                                                          ('Suporte Remoto', 'Atendimento via AnyDesk ou TeamViewer (1h)', 80.00),
                                                          ('Configuração de Nuvem', 'Configuração de Google Drive, iCloud ou OneDrive', 120.00),
                                                          ('Hospedagem de Site', 'Configuração de domínio e servidor web', 200.00),
                                                          ('Backup Estruturado', 'Criação de rotinas de backup automático', 300.00),
                                                          ('Instalação de SO', 'Instalação de Windows ou Linux com ativação', 150.00),
                                                          ('Reparo de Placa Mãe', 'Soldagem e reparo de componentes eletrônicos', 550.00),
                                                          ('Montagem de PC', 'Montagem completa a partir de peças avulsas', 250.00),
                                                          ('Configuração de VPN', 'Acesso seguro remoto para empresas', 400.00),
                                                          ('Treinamento Digital', 'Ensino básico de uso de ferramentas de escritório', 100.00),
                                                          ('Otimização de Sistema', 'Melhoria de performance e limpeza de registro', 90.00),
                                                          ('Troca de Conector', 'Substituição de conector de carga (DC Jack)', 180.00),
                                                          ('Crimpagem de Cabos', 'Confecção de cabos de rede sob medida (p/ metro)', 15.00),
                                                          ('Análise de Segurança', 'Pentest básico em redes locais', 1200.00),
                                                          ('Migração de Dados', 'Transferência de arquivos entre computadores', 130.00),
                                                          ('Suporte Presencial', 'Visita técnica local (primeira hora)', 180.00)
;

CREATE TABLE clientes (
<<<<<<< HEAD
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR( 255 ) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    cep VARCHAR(10),
    endereco VARCHAR(255),
    sexo CHAR(1),
    dataNascimento DATE,
    criadoEm DATE NOT NULL
);


INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('João Silva', 'joao@email.com', '(11) 9328-7438', '12345678900', '08700000', 'Rua A, 123', 'M', '1990-05-10', '2026-04-13');
INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('Maria Souza', 'maria@email.com', '(11) 9328-7438', '98765432100', '08650000', 'Av. Central, 456', 'F', '1995-08-20', '2026-04-13');
INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('Carlos Pereira', 'carlos@email.com', '(11) 9328-7438', '45612378900', '08500000', 'Rua das Flores, 789', 'M', '1988-02-15', '2026-04-13');
INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('Ana Oliveira', 'ana@email.com', '(11) 9328-7438', '32165498700', '08400000', 'Rua B, 321', 'F', '2000-11-30', '2026-04-13');

CREATE TABLE orcamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    dataOrcamento DATE NOT NULL,
    observacao TEXT,
    valorTotal DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_orcamentos_clientes
        FOREIGN KEY (cliente_id)
        REFERENCES clientes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
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

INSERT INTO orcamentos ( cliente_id, dataOrcamento, observacao, valorTotal ) VALUES
    (1, '2026-04-02', 'Consultoria em gestão de projetos', 1200.00),
    (2, '2026-04-03', 'Configuração de servidor local', 850.00),
    (3, '2026-04-04', 'Recuperação de dados em HD externo', 500.00),
    (4, '2026-04-05', 'Instalação de rede Wi-Fi corporativa', 750.00),
    (2, '2026-04-06', 'Upgrade de memória RAM e SSD', 400.00),
    (3, '2026-04-07', 'Remoção de vírus e otimização', 200.00),
    (2, '2026-04-08', 'Montagem de PC Gamer completo', 600.00),
    (1, '2026-04-09', 'Suporte remoto mensal (Contrato)', 350.00),
    (1, '2026-04-10', 'Troca de bateria de notebook', 250.00),
    (2, '2026-04-11', 'Desenvolvimento de Landing Page', 1500.00),
    (2, '2026-04-12', 'Configuração de impressoras em rede', 180.00),
    (2, '2026-04-13', 'Treinamento de software interno', 450.00),
    (2, '2026-04-14', 'Limpeza interna de notebook', 120.00),
    (2, '2026-04-15', 'Atualização de BIOS e Drivers', 100.00),
    (2, '2026-04-16', 'Instalação de Pacote Office e Softwares', 150.00),
    (4, '2026-04-17', 'Configuração de Backup em Nuvem', 300.00),
    (4, '2026-04-18', 'Substituição de teclado de laptop', 220.00),
    (2, '2026-04-19', 'Auditoria de segurança de rede', 2000.00),
    (1, '2026-04-20', 'Manutenção preventiva preventiva trimestral', 400.00),
    (3, '2026-04-21', 'Conserto de dobradiça de notebook', 180.00),
    (3, '2026-04-22', 'Migração de e-mails para Workspace', 650.00)
;

INSERT INTO orcamento_item
    (
        descricao,
        tipoOrcamentoItem,
        quantidade,
        valorUnitario,
        subtotal,
        orcamento_id,
        produto_id,
        servico_id
    )
=======
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          telefone VARCHAR(20) NOT NULL,
                          cpf VARCHAR(14) NOT NULL UNIQUE,
                          cep VARCHAR(10),
                          endereco VARCHAR(255),
                          sexo CHAR(1),
                          dataNascimento DATE,
                          criadoEm DATE NOT NULL
);


INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('João Silva', 'joao@email.com', 11999999999, '12345678900', '08700000', 'Rua A, 123', 'M', '1990-05-10', '2026-04-13');
INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('Maria Souza', 'maria@email.com', 11988888888, '98765432100', '08650000', 'Av. Central, 456', 'F', '1995-08-20', '2026-04-13');
INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('Carlos Pereira', 'carlos@email.com', 11977777777, '45612378900', '08500000', 'Rua das Flores, 789', 'M', '1988-02-15', '2026-04-13');
INSERT INTO clientes
(nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm)
VALUES
    ('Ana Oliveira', 'ana@email.com', 11966666666, '32165498700', '08400000', 'Rua B, 321', 'F', '2000-11-30', '2026-04-13');

CREATE TABLE orcamentos (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            cliente_id INT NOT NULL,
                            dataOrcamento DATE NOT NULL,
                            observacao TEXT,
                            valorTotal DECIMAL(10,2) NOT NULL,

                            CONSTRAINT fk_orcamentos_clientes
                                FOREIGN KEY (cliente_id)
                                    REFERENCES clientes(id)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE
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

INSERT INTO orcamentos ( cliente_id, dataOrcamento, observacao, valorTotal ) VALUES
                                                                                 (1, '2026-04-02', 'Consultoria em gestão de projetos', 1200.00),
                                                                                 (2, '2026-04-03', 'Configuração de servidor local', 850.00),
                                                                                 (3, '2026-04-04', 'Recuperação de dados em HD externo', 500.00),
                                                                                 (4, '2026-04-05', 'Instalação de rede Wi-Fi corporativa', 750.00),
                                                                                 (2, '2026-04-06', 'Upgrade de memória RAM e SSD', 400.00),
                                                                                 (3, '2026-04-07', 'Remoção de vírus e otimização', 200.00),
                                                                                 (2, '2026-04-08', 'Montagem de PC Gamer completo', 600.00),
                                                                                 (1, '2026-04-09', 'Suporte remoto mensal (Contrato)', 350.00),
                                                                                 (1, '2026-04-10', 'Troca de bateria de notebook', 250.00),
                                                                                 (2, '2026-04-11', 'Desenvolvimento de Landing Page', 1500.00),
                                                                                 (2, '2026-04-12', 'Configuração de impressoras em rede', 180.00),
                                                                                 (2, '2026-04-13', 'Treinamento de software interno', 450.00),
                                                                                 (2, '2026-04-14', 'Limpeza interna de notebook', 120.00),
                                                                                 (2, '2026-04-15', 'Atualização de BIOS e Drivers', 100.00),
                                                                                 (2, '2026-04-16', 'Instalação de Pacote Office e Softwares', 150.00),
                                                                                 (4, '2026-04-17', 'Configuração de Backup em Nuvem', 300.00),
                                                                                 (4, '2026-04-18', 'Substituição de teclado de laptop', 220.00),
                                                                                 (2, '2026-04-19', 'Auditoria de segurança de rede', 2000.00),
                                                                                 (1, '2026-04-20', 'Manutenção preventiva preventiva trimestral', 400.00),
                                                                                 (3, '2026-04-21', 'Conserto de dobradiça de notebook', 180.00),
                                                                                 (3, '2026-04-22', 'Migração de e-mails para Workspace', 650.00)
;

INSERT INTO orcamento_item
(
    descricao,
    tipoOrcamentoItem,
    quantidade,
    valorUnitario,
    subtotal,
    orcamento_id,
    produto_id,
    servico_id
)
>>>>>>> fe0670de5e849ef8690c3b82d807661a47f1f6b2
VALUES
    ('Mouse Gamer RGB', 'PRODUTO', 1, 89.90, 89.90, 1, 1, NULL),
    ('Formatação de Computador', 'SERVICO', 1, 150.00, 150.00, 1, NULL, 1),
    ('Troca de Tela de Celular', 'SERVICO', 1, 300.00, 300.00, 2, NULL, 2)
;