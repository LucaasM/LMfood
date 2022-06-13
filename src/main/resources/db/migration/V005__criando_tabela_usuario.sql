create table usuario (
    id bigint not null auto_increment,
    data_cadastro date,
    email varchar(20),
    nome varchar(25),
    senha varchar(20),
    primary key (id)
) engine=InnoDB default charset=utf8;