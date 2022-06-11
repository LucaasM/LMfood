insert into Cozinha (nome) values ("Tailandesa");
insert into Cozinha (nome) values ("Brasileira");
insert into Cozinha (nome) values ("Italiana");

insert into Restaurante (nome, taxa_frete, cozinha_id) values ("Chines bar", 4.50, 1);
insert into Restaurante (nome, taxa_frete, cozinha_id) values ("Bololo lanches", 5.00, 2);
insert into Restaurante (nome, taxa_frete, cozinha_id) values ("Hatata", 2.00, 2);

insert into Estado (nome) values ("Paraíba");
insert into Estado (nome) values ("São Paulo");
insert into Estado (nome) values ("Rio de Janeiro");
insert into Estado (nome) values ("Bahia");

insert into Cidade (nome, estado_id) values ("Guarabira", 1);
insert into Cidade (nome, estado_id) values ("João Pessoa", 1);
insert into Cidade (nome, estado_id) values ("São Paulo", 2);
insert into Cidade (nome, estado_id) values ("Rio de Janeiro", 3);