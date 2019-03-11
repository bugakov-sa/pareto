CREATE TABLE algorithm (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE robot (
    id SERIAL PRIMARY KEY,
    algorithm_id BIGINT NOT NULL REFERENCES algorithm(id)
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
    id BIGINT PRIMARY KEY,
    robot_id BIGINT REFERENCES robot(id),
    context_id BIGINT REFERENCES context(id)
);

CREATE TABLE play_event (
    id BIGINT PRIMARY KEY,
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

CREATE TABLE play_status (
    play_id BIGINT REFERENCES play(id),
    time TIMESTAMP NOT NULL,
    status SMALLINT
);