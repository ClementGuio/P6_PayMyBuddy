CREATE DATABASE paymybuddy;

USE paymybuddy;

CREATE TABLE account (

    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,

    email VARCHAR(64) NOT NULL UNIQUE,

    pword VARCHAR(60) NOT NULL,

    balance FLOAT DEFAULT 0.0,

    firstname VARCHAR(64),

    lastname VARCHAR(64)
);

CREATE TABLE payment (

    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,

    id_debitor INTEGER NOT NULL REFERENCES account(id),

    id_creditor INTEGER NOT NULL REFERENCES account(id),

    pay_datetime DATETIME NOT NULL,

    description VARCHAR(30),

    amount FLOAT NOT NULL,

    company_fee FLOAT NOT NULL 
);


CREATE TABLE link_user (
    
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,

    id_account INTEGER NOT NULL REFERENCES account(id),

    id_friend INTEGER NOT NULL REFERENCES account(id)
);


