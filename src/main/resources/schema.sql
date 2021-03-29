CREATE TABLE  IF NOT EXISTS account (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
login VARCHAR(20) NOT NULL UNIQUE,
address VARCHAR(50) NOT NULL,
amount BIGINT
);

CREATE TABLE  IF NOT EXISTS account_operation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idSource BIGINT,
    idDestination BIGINT,
    amount BIGINT,
    operationType VARCHAR(50),
    operationStatus BOOLEAN
);
