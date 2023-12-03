CREATE DATABASE PI
use PI

create table Cliente (
    Usuario varchar(8) primary key,
    Nome varchar(50) not null,
    Telefone varchar(15) not null,
    Telefone_responsavel varchar(15),
    CPF varchar(14) not null,
    Data_nasc varchar(10) not null,
    email varchar(50) not null,
    Cidade varchar(50) not null,
    Estado varchar(2) not null,
    Como_conheceu varchar(50) not null,
    Ativado bit
);

create table Funcionario (
    Codigo_func varchar(8) primary key
);

create table Maquina (
    Codigo_maq varchar(10) primary key,
    Tipo varchar(25) not null,
    Preco_hr smallmoney not null
)

create table Sessao(
    codigo_sessao int identity (1,1) primary key,
    preco smallmoney not null,
    dia date not null,
    hora_inicio time not null,
    hora_fim time not null,
    Usuario varchar(8) not null references Cliente,
    Codigo_func varchar(8) not null references Funcionario,
    Codigo_maq varchar (10) not null references Maquina
)

insert into cliente values ('joao123', 'Joao', '(19)99999-9999', '(19)99999-9999', '123.456.789-00', '12/01/03', 'joao@email.com', 'Indaiatuba', 'SP', 'Shopping', '1');
insert into cliente values ('maria123', 'Maria', '(19)99999-9999', '(19)99999-9999', '458.654.879-44', '12/12/95', 'maria@email.com', 'Indaiatuba', 'SP', 'Site', '1');
insert into cliente values ('frede123', 'Frederico', '(19)99999-9999', '(19)99999-9999', '471.987.265-88', '16/03/99', 'fred@email.com', 'Indaiatuba', 'SP', 'Flyer', '1');
insert into cliente values ('vitor123', 'Vitor Hugo', '(19)99999-9999', '(19)99999-9999', '874.147.963-00', '12/11/00', 'joao@email.com', 'Indaiatuba', 'SP', 'Shopping', '1');
insert into cliente values ('pedro123', 'Pedro', '(19)99999-9999', '(19)99999-9999', '741.987.325-15', '12/01/03', 'pedro@email.com', 'Indaiatuba', 'SP', 'Shopping', '1');
insert into cliente values ('anama456', 'Ana Maria', '(19)88888-8888', '(19)88888-8888', '123.456.789-00', '04/05/95', 'ana@email.com', 'Campinas', 'SP', 'Shopping', '1');
insert into cliente values ('carla789', 'Carla', '(19)77777-7777', '(19)77777-7777', '987.654.321-11', '15/09/92', 'maria@email.com', 'São Paulo', 'SP', 'Anuncio', '1');
insert into cliente values ('carlo123', 'Carlos', '(19)66666-6666', '(19)66666-6666', '555.123.789-44', '22/12/98', 'carlos@email.com', 'Campinas', 'SP', 'TV', '1');
insert into cliente values ('lucas231', 'Lucas', '(19)55555-5555', '(19)55555-5555', '222.333.444-77', '07/07/01', 'lucas@email.com', 'São Paulo', 'SP', 'Instagram', '1');
insert into cliente values ('isabe456', 'Isabela', '(19)44444-4444', '(19)44444-4444', '333.555.777-22', '29/03/96', 'isabela@email.com', 'Campinas', 'SP', 'Shopping', '1');

insert into funcionario values ('pablo112');
insert into funcionario values ('maria123');
insert into funcionario values ('lucas456');
insert into funcionario values ('anama789');
insert into funcionario values ('joao123');
insert into funcionario values ('carlo567');
insert into funcionario values ('pedro789');
insert into funcionario values ('lara111');
insert into funcionario values ('vitor222');
insert into funcionario values ('isabe333');

insert into maquina values ('pc1', 'computador', '30');
insert into maquina values ('pc2', 'computador', '30');
insert into maquina values ('pc3', 'computador', '30');
insert into maquina values ('pc4', 'computador', '30');
insert into maquina values ('pc5', 'computador', '30');
insert into maquina values ('xbox1', 'console', '25');
insert into maquina values ('play1', 'console', '25');
insert into maquina values ('switch1', 'console', '20');
insert into maquina values ('simulador1', 'simulador', '35');
insert into maquina values ('vr1', 'vr', '30');



INSERT INTO sessao VALUES (50, '2023-10-23', '09:00', '11:00', 'frede123', 'maria123', 'play1');
INSERT INTO sessao VALUES (40, '2023-10-23', '09:00', '11:00', 'joao123', 'anama789', 'switch1');
INSERT INTO sessao VALUES (50, '2023-10-23', '09:00', '11:00', 'frede123', 'maria123', 'play2');
INSERT INTO sessao VALUES (60, '2023-10-23', '08:00', '10:00', 'carla789', 'carlo567', 'pc1');
INSERT INTO sessao VALUES (60, '2023-10-23', '08:00', '10:00', 'lucas231', 'carlo567', 'pc2');
INSERT INTO sessao VALUES (60, '2023-10-23', '08:00', '10:00', 'isabe456', 'carlo567', 'pc3');
INSERT INTO sessao VALUES (60, '2023-10-23', '08:00', '10:00', 'carlo123', 'carlo567', 'pc4');
INSERT INTO sessao VALUES (60, '2023-10-23', '08:00', '10:00', 'pedro123', 'carlo567', 'pc5');
INSERT INTO sessao VALUES (50, '2023-10-23', '16:00', '17:00', 'joao123', 'isabe333', 'xbox1');
INSERT INTO sessao VALUES (50, '2023-10-23', '16:00', '17:00', 'anama456', 'isabe333', 'xbox2');


