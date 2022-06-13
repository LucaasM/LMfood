create table estado_cidade_list (
    estado_id bigint not null,
    cidade_list_id bigint not null) engine=InnoDB default charset=utf8;

alter table estado_cidade_list add constraint FK60214jpr800ukjvy8xpf8oyy7 foreign key (cidade_list_id) references cidade (id);

alter table estado_cidade_list add constraint FK34lhg26emeqy1jyl23nml8gqw foreign key (estado_id) references estado (id);