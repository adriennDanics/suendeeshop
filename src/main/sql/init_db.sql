DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS productCategories;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS shoppingCart;

DROP SEQUENCE IF EXISTS product_id_seq;
DROP SEQUENCE IF EXISTS productCat_id_seq;
DROP SEQUENCE IF EXISTS supplier_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS cart_id_seq;

CREATE SEQUENCE productCat_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE productCategories
(
id INT NOT NULL PRIMARY KEY DEFAULT nextval('productCat_id_sequence'),
name varchar(40) NOT NULL,
department varchar(40),
description varchar(255)
);

CREATE SEQUENCE supplier_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE suppliers
(
id INT NOT NULL PRIMARY KEY DEFAULT nextval('supplier_id_sequence'),
name varchar(40) NOT NULL,
description varchar(255)
);

CREATE SEQUENCE product_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE products
(
id INT NOT NULL PRIMARY KEY DEFAULT nextval('product_id_sequence'),
name varchar(255) NOT NULL,
default_price decimal(20,2) NOT NULL,
currency CHAR(3) DEFAULT 'USD',
description varchar(255), 
productCat_id INT Not NULL REFERENCES productCategories(id),
supplier_id INT Not NULL REFERENCES suppliers(id)
);

CREATE SEQUENCE user_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE users
(
id INT NOT NULL PRIMARY KEY DEFAULT nextval('user_id_sequence'),
user_name VARCHAR(255) NOT NULL UNIQUE,
email VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL
);

CREATE SEQUENCE cart_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE shoppingCart
(
id INT NOT NULL PRIMARY KEY DEFAULT nextval('cart_id_sequence'),
user_id INT Not NULL REFERENCES users(id),
product_id INT Not NULL REFERENCES products(id),
order_number INT
);

CREATE SEQUENCE userDet_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE userDetails
(
id INT NOT NULL PRIMARY KEY DEFAULT nextval('product_id_sequence'),
user_id INT Not NULL REFERENCES users(id),
email varchar(255) NOT NULL,
phone varchar(255), 
shipping_address varchar(255) NOT NULL,
billing_address varchar(255) NOT NULL
);

INSERT INTO suppliers (name, description) VAlUES ('Nature', 'All around and provides');
INSERT INTO suppliers (name, description) VAlUES ('Higher Power', 'Cosmic power (existence debated)');
INSERT INTO suppliers (name, description) VAlUES ('Inner Voice', 'Inner monologue to encourage or discourage');
INSERT INTO suppliers (name, description) VAlUES ('Fate', 'Cosmic power to help or hinder');

INSERT INTO productCategories (name, department, description) VAlUES ('Necessities', 'Abstract','Everything you need to live your life but nothing material.');
INSERT INTO productCategories (name, department, description) VAlUES ('Power-ups', 'Abstract','Everything you might need to live your life happily, mainly attitude.');
INSERT INTO productCategories (name, department, description) VAlUES ('Material Goods', 'Material Goods', 'What you might want and cannot get anywhere else.');
INSERT INTO productCategories (name, department, description) VAlUES ('Priceless', 'Abstract', 'What no one else has but you really want anyway.');

INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Eternal Life', 10000000000000, 'Fantastic price. You will be able to watch all your enemies die (your friends too)! Lifetime guarantee!', 4, 2);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Will To Live', 10000, 'Tired of living? Buy today and you will learn to love your life immediately. Terms and conditions apply.', 2, 3);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Sense Of Humour', 20003.90, 'If you think you already have it, buy NOW. Political correctness sold separately.', 1, 1);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Motivation', 100000, E'Couldn\'t be bothered to come up with a proper description so here you go.', 4, 4);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('True Love', 69, E'We think it\'s a myth but we also need to pay bills. It comes cheap, apparently.', 2, 3);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Two-tailed Dog', 0.5, 'Sweet, cute, genetically modified best friend for sale. NOT PC!!!', 3, 1);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Free Beer', 0, 'Order a lifetime supply of (NOT craft) beer of suitably low quality.', 3, 1);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Beauty', 210000, 'Solve all of your problems at once. Personality not included but true love is free.', 2, 1);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Basic Intelligence', 0.99, 'Tired of your IQ being under room temperature? Upgrade it now to triple digits!', 1, 1);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Intelligence Raise', 1000, 'Tired of travelling through space-time linearly? Invent something to change that, using this power-up!', 2, 2);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Schizophrenia', 666, E'Buy as a gift to anyone you hate. Optionally, get it for yourself, in case you\'re feeling lonely.', 2, 3);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Good Luck', 777, 'Buy a patch of good luck you can use five times to make everything go your way.', 2, 4);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Bad Luck', 4444, 'Buy a patch of bad luck you can use five times to make everything go wrong for somebody deserving.', 4, 4);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('Conscience', 0, 'It can get in the way of copious amounts of debauchery and general fun. Get it for your enemies!', 1, 1);
INSERT INTO products (name, default_price, description, productCat_id, supplier_id) VAlUES ('72 Virgins', 72000, E'Afraid of death? Don\'t know how to make a bomb? Let us make it easy for you at a bargain price.', 2, 2);

