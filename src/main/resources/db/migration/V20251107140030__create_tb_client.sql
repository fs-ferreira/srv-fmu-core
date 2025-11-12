CREATE TABLE tb_client (
    id BIGINT NOT NULL PRIMARY KEY,
    client_id VARCHAR(255) NOT NULL,
    client_secret VARCHAR(255) NOT NULL,
    redirect_uri VARCHAR(255) NOT NULL,
    scope VARCHAR(255)
);
