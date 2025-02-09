CREATE SEQUENCE IF NOT EXISTS note_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE note
(
    id           BIGINT NOT NULL,
    title        VARCHAR(255),
    translations JSONB,
    CONSTRAINT pk_note PRIMARY KEY (id)
);