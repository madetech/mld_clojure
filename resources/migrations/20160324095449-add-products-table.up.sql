CREATE TABLE products
(id MEDIUMINT NOT NULL AUTO_INCREMENT,
 name VARCHAR(30),
 slug VARCHAR(30),
 image_url VARCHAR(50),
 price decimal(6,2),
 is_active BOOLEAN,
 PRIMARY KEY (id));