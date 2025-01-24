CREATE TABLE IF NOT EXISTS locations (
    id  BIGSERIAL PRIMARY KEY,
    lat REAL      NOT NULL,
    lon REAL      NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id    BIGSERIAL    PRIMARY KEY,
    email VARCHAR(254) NOT NULL,
    name  VARCHAR(250) NOT NULL,
          UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
    id   BIGSERIAL   PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
         UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS events (
    id                 BIGSERIAL     PRIMARY KEY,
    annotation         VARCHAR(2000) NOT NULL,
    category_id        BIGINT        NOT NULL,
    confirmed_requests BIGINT        NOT NULL,
    created_on         TIMESTAMP     NOT NULL,
    description        VARCHAR(7000) NOT NULL,
    event_date         TIMESTAMP     NOT NULL,
    initiator_id       BIGINT        NOT NULL,
    location_id        BIGINT        NOT NULL,
    paid               BOOLEAN       NOT NULL,
    participant_limit  BIGINT        NOT NULL,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN       NOT NULL,
    state              VARCHAR(9)    NOT NULL,
    title              VARCHAR(120)  NOT NULL,
                       FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
                       FOREIGN KEY (initiator_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests (
    id           BIGSERIAL  PRIMARY KEY,
    created      TIMESTAMP  NOT NULL,
    event_id     BIGINT     NOT NULL,
    requester_id BIGINT     NOT NULL,
    status       VARCHAR(9) NOT NULL,
                 CONSTRAINT unique_request UNIQUE (event_id, requester_id),
                 FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
                 FOREIGN KEY (requester_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilations (
    id     BIGSERIAL   PRIMARY KEY,
    pinned BOOLEAN     NOT NULL,
    title  VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS events_compilations (
    PRIMARY KEY (compilation_id, event_id),
    compilation_id BIGINT NOT NULL,
    event_id       BIGINT NOT NULL,
                   FOREIGN KEY (compilation_id) REFERENCES compilations(id) ON DELETE CASCADE,
                   FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments (
    id        BIGSERIAL     PRIMARY KEY,
    content   VARCHAR(2000) NOT NULL,
    created   TIMESTAMP     NOT NULL,
    updated   TIMESTAMP,
    event_id  BIGINT        NOT NULL,
    author_id BIGINT        NOT NULL,
              FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
              FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
);