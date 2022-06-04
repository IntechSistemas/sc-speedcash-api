CREATE TABLE IF NOT EXISTS estado(
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL, 
	sigla VARCHAR(2) NOT NULL,
	codigo VARCHAR(2) NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cidade(
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL,
	codigo VARCHAR(8) NOT NULL,
	estado_id INTEGER NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(estado_id) REFERENCES estado(id)
	
);

CREATE TABLE IF NOT EXISTS forma_pagamento(
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(20) NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
);

CREATE TABLE IF NOT EXISTS configuracao_geral(
	id SERIAL PRIMARY KEY,
	quantidade_mesas INTEGER,
	cor_tema VARCHAR(15) NOT NULL,
	tipo_conexao VARCHAR(20) NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
);

CREATE TABLE IF NOT EXISTS configuracao_nfe(
	id SERIAL PRIMARY KEY,
	serie INTEGER NOT NULL,
	numero INTEGER NOT NULL,
	ambiente_producao BOOLEAN NOT NULL DEFAULT TRUE,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
);

CREATE TABLE IF NOT EXISTS configuracao_nfce(
	id SERIAL PRIMARY KEY,
	serie INTEGER NOT NULL,
	numero INTEGER NOT NULL,
	csc_producao_id VARCHAR(8) NOT NULL,
	csc_producao_token VARCHAR(36) NOT NULL,
	csc_homologacao_id VARCHAR(8),
	csc_homologacao_token VARCHAR(36),
	ambiente_producao BOOLEAN NOT NULL DEFAULT TRUE,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
);

CREATE TABLE IF NOT EXISTS configuracao_documento_fiscal(
	id SERIAL PRIMARY KEY,
	certificado_caminho VARCHAR(100) NOT NULL,
	certificado_senha VARCHAR(20) NOT NULL,
	configuracao_nfe_id INTEGER,
	configuracao_nfce_id INTEGER,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(configuracao_nfe_id ) REFERENCES configuracao_nfe(id),
	FOREIGN KEY(configuracao_nfce_id) REFERENCES configuracao_nfce(id)
	
);

CREATE TABLE IF NOT EXISTS representante_legal(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL, 
	cpf VARCHAR(14) NOT NULL,
	email VARCHAR(100) NOT NULL,
	fone VARCHAR(14) NOT NULL
);

CREATE TABLE IF NOT EXISTS empresa(
	id SERIAL PRIMARY KEY,
	
	razao_social VARCHAR(60) NOT NULL,
	nome_fantasia VARCHAR(60),
	cnpj VARCHAR(18) NOT NULL,
	inscricao_estadual VARCHAR(14),
	regime_tributario VARCHAR(20),
	ativo BOOLEAN NOT NULL DEFAULT TRUE,
	emitir_nfe BOOLEAN NOT NULL DEFAULT TRUE,
	emitir_nfce BOOLEAN NOT NULL DEFAULT TRUE,
	usa_stone_api BOOLEAN NOT NULL DEFAULT TRUE,
	endereco_cep VARCHAR(10) NOT NULL,
	endereco_logradouro VARCHAR(50) NOT NULL,
	endereco_numero VARCHAR(10) NOT NULL,
	endereco_complemento VARCHAR(50),
	endereco_bairro VARCHAR(20) NOT NULL,
	endereco_cidade_id INTEGER NOT NULL,
	representante_legal_id INTEGER NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	configuracao_documento_fiscal_id INTEGER,
	configuracao_geral_id INTEGER,
	
	FOREIGN KEY(configuracao_documento_fiscal_id) REFERENCES configuracao_documento_fiscal(id),
	FOREIGN KEY(configuracao_geral_id) REFERENCES configuracao_geral(id),
	FOREIGN KEY(endereco_cidade_id) REFERENCES cidade(id),
	FOREIGN KEY(representante_legal_id) REFERENCES representante_legal(id)
);

CREATE TABLE IF NOT EXISTS forma_pagamento_empresa(
	forma_pagamento_id INTEGER,
	empresa_id INTEGER
);

CREATE TABLE IF NOT EXISTS configuracao_bancaria(
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(20) NOT NULL, 
	token VARCHAR(100),
	secret VARCHAR(100),
	client_id VARCHAR(100),
	oauth_key VARCHAR(100),
	url_api VARCHAR(100),
	loja_id VARCHAR(100),
	caixa_id VARCHAR(100),
	qrcode VARCHAR(100),
	seller_token VARCHAR(100),
	stone_code VARCHAR(100),
	ambiente_producao BOOLEAN DEFAULT TRUE,
	ativo BOOLEAN DEFAULT TRUE,
	empresa_id INTEGER NOT NULL,
	codigo_convite VARCHAR(6),
	agencia_numero VARCHAR(4),
	agencia_conta_pj VARCHAR(20),
	hash VARCHAR(50),
	tipo_convenio VARCHAR(1),
	
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(empresa_id) REFERENCES empresa(id)
);

CREATE TABLE IF NOT EXISTS fornecedor(
	id SERIAL PRIMARY KEY, 
	nome VARCHAR(100) NOT NULL,
	cnpj VARCHAR(18) NOT NULL,
	inscricao_estadual VARCHAR(14),
	inscricao_estadual_st VARCHAR(14),
	fone VARCHAR(14) NOT NULL,
	endereco_cep VARCHAR(10) NOT NULL,
	endereco_logradouro VARCHAR(50) NOT NULL,
	endereco_numero VARCHAR(10) NOT NULL,
	endereco_complemento VARCHAR(50),
	endereco_bairro VARCHAR(20) NOT NULL,
	endereco_cidade_id INTEGER NOT NULL,
	empresa_id INTEGER NOT NULL,
	
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(empresa_id) REFERENCES empresa(id),
	FOREIGN KEY(endereco_cidade_id) REFERENCES cidade(id)
);

CREATE TABLE IF NOT EXISTS grupo(
	id SERIAL PRIMARY KEY, 
	descricao VARCHAR(50) NOT NULL,
	empresa_id INTEGER NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(empresa_id) REFERENCES empresa(id)
);

CREATE TABLE IF NOT EXISTS unidade(
	id SERIAL PRIMARY KEY, 
	descricao VARCHAR(20) NOT NULL,
	sigla VARCHAR(2) NOT NULL, 
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
);

CREATE TABLE IF NOT EXISTS produto(
	id SERIAL PRIMARY KEY, 
	descricao VARCHAR(100) NOT NULL,
	cprod INTEGER NOT NULL,
	cean VARCHAR(14) NOT NULL,
	ncm VARCHAR(8) NOT NULL,
	cest VARCHAR(7) NOT NULL,
	quantidade INTEGER NOT NULL DEFAULT 0,
	valor_compra INTEGER NOT NULL DEFAULT 0,
	valor_venda INTEGER NOT NULL DEFAULT 0,
	margem_lucro INTEGER NOT NULL DEFAULT 0,
	servico BOOLEAN DEFAULT FALSE,
	ativo BOOLEAN DEFAULT TRUE,
	empresa_id INTEGER NOT NULL,
	fornecedor_id INTEGER NOT NULL,
	grupo_id INTEGER NOT NULL,
	unidade_id INTEGER NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(empresa_id) REFERENCES empresa(id),
	FOREIGN KEY(fornecedor_id) REFERENCES fornecedor(id),
	FOREIGN KEY(grupo_id) REFERENCES grupo(id),
	FOREIGN KEY(unidade_id) REFERENCES unidade(id)
);

CREATE TABLE IF NOT EXISTS permissao(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	descricao VARCHAR(50) NOT NULL,
	adm BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS cargo(
	id SERIAL PRIMARY KEY,
	descricao VARCHAR(30) NOT NULL,
	empresa_id INTEGER NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(empresa_id) REFERENCES empresa(id)
);

CREATE TABLE IF NOT EXISTS cargo_permissao(
	cargo_id INTEGER,
	permissao_id INTEGER,
	
	FOREIGN KEY(cargo_id) REFERENCES cargo(id),
	FOREIGN KEY(permissao_id) REFERENCES permissao(id)
);

CREATE TABLE IF NOT EXISTS usuario(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	login VARCHAR(20) UNIQUE NOT NULL,
	senha VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	ativo BOOLEAN NOT NULL DEFAULT TRUE,
	empresa_id INTEGER NOT NULL,
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP
	
);

CREATE TABLE IF NOT EXISTS usuario_cargo(
	usuario_id INTEGER,
	cargo_id INTEGER
);

CREATE TABLE IF NOT EXISTS cliente(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	cnpj VARCHAR(18),
	cpf VARCHAR(14),
	fone VARCHAR(14) NOT NULL,
	email VARCHAR(100) NOT NULL,
	inscricao_estadual VARCHAR(14),
	inscricao_municipal VARCHAR(14),
	endereco_cep VARCHAR(10) NOT NULL,
	endereco_logradouro VARCHAR(50) NOT NULL,
	endereco_numero VARCHAR(10) NOT NULL,
	endereco_complemento VARCHAR(50),
	endereco_bairro VARCHAR(20) NOT NULL,
	endereco_cidade_id INTEGER NOT NULL,
	empresa_id INTEGER NOT NULL,
	
	data_cadastro TIMESTAMP NOT NULL,
	data_atualizacao TIMESTAMP,
	
	FOREIGN KEY(empresa_id) REFERENCES empresa(id)
	
);

CREATE TABLE IF NOT EXISTS pedido(
	id SERIAL PRIMARY KEY,
	status varchar(10) not null,
	valor_total INTEGER NOT NULL DEFAULT 0,
	desconto INTEGER NOT NULL DEFAULT 0,
	troco INTEGER NOT NULL DEFAULT 0,
	data_cadastro TIMESTAMP NOT NULL,
	cadastrado_por INTEGER NOT NULL,
	data_cancelamento TIMESTAMP,
	cancelado_por INTEGER,
	data_confirmacao TIMESTAMP,
	confirmado_por INTEGER,
	
	empresa_id INTEGER NOT NULL,
	cliente_id INTEGER,
	
	FOREIGN KEY(empresa_id) REFERENCES empresa(id),
	FOREIGN KEY(cliente_id) REFERENCES cliente(id),
	FOREIGN KEY(cadastrado_por) REFERENCES usuario(id),
	FOREIGN KEY(cancelado_por) REFERENCES usuario(id),
	FOREIGN KEY(confirmado_por) REFERENCES usuario(id)
	
);

CREATE TABLE IF NOT EXISTS item_pedido(
	id SERIAL PRIMARY KEY,
	quantidade	INTEGER NOT NULL,
	preco_unitario INTEGER NOT NULL,
	preco_total INTEGER NOT NULL,
	pedido_id INTEGER NOT NULL,
	produto_id INTEGER NOT NULL,
	
	FOREIGN KEY(pedido_id) REFERENCES pedido(id),
	FOREIGN KEY(produto_id) REFERENCES produto(id)
	
);

CREATE TABLE IF NOT EXISTS cartao
(
  id serial primary key,
  autorizacao character varying(20) NOT NULL,
  cnpj character varying(18) NOT NULL,
  pos character varying(20) NOT NULL,
  nsu character varying(50) NOT NULL,
  bandeira character varying(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS pagamento(
	id SERIAL PRIMARY KEY,
	valor_pago INTEGER NOT NULL DEFAULT 0,
	status		VARCHAR(10) NOT NULL DEFAULT 'PENDENTE',
	pedido_id	INTEGER NOT NULL,
	forma_pagamento_id INTEGER NOT NULL,
	parcelas SMALLINT NOT NULL DEFAULT 0,
	transacao_id VARCHAR(100),
	cartao_id INTEGER,
	
	FOREIGN KEY(pedido_id) REFERENCES pedido(id),
	FOREIGN KEY(forma_pagamento_id) REFERENCES forma_pagamento(id),
	FOREIGN KEY(cartao_id) REFERENCES cartao(id)
);

CREATE TABLE IF NOT EXISTS foto_produto(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(150) NOT NULL,
	descricao VARCHAR(150) NOT NULL,
	content_type VARCHAR(80) NOT NULL,
	tamanho INTEGER NOT NULL,
	
	produto_id INTEGER NOT NULL,
	
	FOREIGN KEY(produto_id) REFERENCES produto(id)
);