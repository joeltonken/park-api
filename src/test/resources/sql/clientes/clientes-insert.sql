insert into
    USUARIOS (id, username, password, role) values (100, 'joelton@email.com', '$2a$12$Yf6dj1P9u51Sd1rzG86.ueacbpyPUzsL56DBWy1HiRyWfDiLcZRfy', 'ROLE_ADMIN');
insert into
    USUARIOS (id, username, password, role) values (101, 'joel@email.com', '$2a$12$Yf6dj1P9u51Sd1rzG86.ueacbpyPUzsL56DBWy1HiRyWfDiLcZRfy', 'ROLE_CLIENTE');
insert into
    USUARIOS (id, username, password, role) values (102, 'joe@email.com', '$2a$12$Yf6dj1P9u51Sd1rzG86.ueacbpyPUzsL56DBWy1HiRyWfDiLcZRfy', 'ROLE_CLIENTE');
insert into
    USUARIOS (id, username, password, role) values (103, 'joselu@email.com', '$2a$12$Yf6dj1P9u51Sd1rzG86.ueacbpyPUzsL56DBWy1HiRyWfDiLcZRfy', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Joel Rocha', '14931923020', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Joe Gomes', '64867663018', 102);