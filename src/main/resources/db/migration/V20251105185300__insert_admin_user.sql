INSERT INTO tb_user (id, username, password, email, roles)
VALUES (
    '${admin_id}',
    '${admin_username}',
    '${admin_password}',
    '${admin_email}',
    ARRAY['ADMIN']
);
