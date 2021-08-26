create schema if not exists smallshop;

create TABLE IF NOT EXISTS smallshop.user (
    id uuid NOT NULL DEFAULT random_uuid(),
    username varchar(16),
    password varchar(72),
    admin_status varchar(16) NOT NULL DEFAULT 'ACTIVE' NULL_TO_DEFAULT,
    role varchar(16) NOT NULL DEFAULT 'ROLE_USER' NULL_TO_DEFAULT,
    PRIMARY KEY(id)
    );

insert into smallshop.user (id, username, password, admin_status, role) values('a1b9b31d-e73c-4112-af7c-b68530f38222', 'admin', '{bcrypt}$2a$10$neR0EcYY5./tLVp4litNyuBy/kfrTsqEv8hiyqEKX0TXIQQwC/5Rm', 'ACTIVE', 'ADMIN');
insert into smallshop.user (id, username, password, admin_status, role) values('a1b9b31d-e73c-4112-af7c-b68530f38223', 'noadmin', '{bcrypt}$2a$10$neR0EcYY5./tLVp4litNyuBy/kfrTsqEv8hiyqEKX0TXIQQwC/5Rm', 'ACTIVE', 'USER');

create TABLE IF NOT EXISTS smallshop.customer (
    id uuid NOT NULL DEFAULT random_uuid(),
    name varchar(16),
    surname varchar(16),
    photo varchar(255),
    created_by uuid NOT NULL,
    modified_by uuid NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (created_by)
    REFERENCES smallshop.user (id),
    FOREIGN KEY (modified_by)
    REFERENCES smallshop.user (id)
);

insert into smallshop.customer (id, name, surname, photo, created_by, modified_by) values('6d62d909-f957-430e-8689-b5129c0bb75e', 'Customer', 'One', '', 'a1b9b31d-e73c-4112-af7c-b68530f38223', 'a1b9b31d-e73c-4112-af7c-b68530f38223');
insert into smallshop.customer (id, name, surname, photo, created_by, modified_by) values('618ffaff-cbcd-48d4-8848-a15601e6725b', 'Customer', 'Two', '', 'a1b9b31d-e73c-4112-af7c-b68530f38223', 'a1b9b31d-e73c-4112-af7c-b68530f38223');

create TABLE IF NOT EXISTS smallshop.user_token (
    id uuid NOT NULL DEFAULT random_uuid(),
    refresh_token varchar(128),
    user_id uuid NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id)
    REFERENCES smallshop.user(id)
);