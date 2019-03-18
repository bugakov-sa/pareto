CREATE TABLE robot (
    id SERIAL PRIMARY KEY,
    class_name TEXT NOT NULL
);

CREATE TABLE robot_param (
    robot_id BIGINT REFERENCES robot(id),
    name TEXT,
    value TEXT NOT NULL,
    PRIMARY KEY (robot_id, name)
);

CREATE TABLE context (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE context_param (
    context_id BIGINT REFERENCES context(id),
    name TEXT,
    value TEXT NOT NULL,
    PRIMARY KEY (context_id, name)
);

CREATE TABLE play (
    id SERIAL PRIMARY KEY,
    robot_id BIGINT REFERENCES robot(id),
    context_id BIGINT REFERENCES context(id),
    status INTEGER NOT NULL
);

CREATE TABLE play_pnl (
    play_id BIGINT,
    time BIGINT,
    open INTEGER NOT NULL,
    close INTEGER NOT NULL,
    min INTEGER NOT NULL,
    max INTEGER NOT NULL,
    PRIMARY KEY (play_id, time)
);

CREATE TABLE play_event (
    id SERIAL PRIMARY KEY,
    play_id BIGINT REFERENCES play(id),
    time TIMESTAMP NOT NULL,
    event_type SMALLINT
);

CREATE TABLE event_param (
    event_id BIGINT REFERENCES context(id),
    name TEXT,
    value TEXT NOT NULL,
    PRIMARY KEY (event_id, name)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE product_quotation (
    product_id BIGINT REFERENCES product(id),
    time BIGINT NOT NULL,
    open INTEGER NOT NULL,
    close INTEGER NOT NULL,
    min INTEGER NOT NULL,
    max INTEGER NOT NULL,
    PRIMARY KEY (product_id, time)
);