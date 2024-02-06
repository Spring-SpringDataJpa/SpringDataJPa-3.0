--CREATE TABLE IF NOT EXISTS Product (
--    id UUID PRIMARY KEY,
--    name VARCHAR(255) NOT NULL,
--    quantity INT NOT NULL,
--    price DOUBLE NOT NULL
--);

CREATE TABLE IF NOT EXISTS Product (
    id UUID,
    name VARCHAR(255) NOT NULL,
    quantity INT,
    price DOUBLE NOT NULL
);