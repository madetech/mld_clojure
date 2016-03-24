CREATE TABLE products
(id VARCHAR(20) PRIMARY KEY,
 name VARCHAR(30),
 slug VARCHAR(30),
 image_url VARCHAR(50),
 price decimal(6,2),
 is_active BOOLEAN);