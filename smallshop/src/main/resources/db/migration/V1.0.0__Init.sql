create schema if not exists smallshop;

create TABLE IF NOT EXISTS smallshop.user (
    id uuid NOT NULL DEFAULT random_uuid(),
    username varchar(16),
    password varchar(40),
    admin_status varchar(16) NOT NULL DEFAULT 'ACTIVE' NULL_TO_DEFAULT,
    role varchar(16) NOT NULL DEFAULT 'ROLE_USER' NULL_TO_DEFAULT,
    PRIMARY KEY(id)
    );

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

create TABLE IF NOT EXISTS smallshop.user_token (
    id uuid NOT NULL DEFAULT random_uuid(),
    refresh_token varchar(128),
    user_id uuid NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id)
    REFERENCES smallshop.user(id)
);