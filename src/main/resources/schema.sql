CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    point INT NOT NULL
);

CREATE TABLE IF NOT EXISTS places (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    rating INT NOT NULL
);

CREATE TABLE IF NOT EXISTS posts (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    picture VARCHAR(255),
    rating INT NOT NULL,
    user_id CHAR(36) NOT NULL,
    place_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (place_id) REFERENCES places(id),
    CONSTRAINT check_rating CHECK (rating >= 0 AND rating <= 5)
);