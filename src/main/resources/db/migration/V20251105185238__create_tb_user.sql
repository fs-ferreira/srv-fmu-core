CREATE TABLE tb_user (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    roles VARCHAR[]
);