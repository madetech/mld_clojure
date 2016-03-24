alter table products modify id INTEGER NOT NULL AUTO_INCREMENT;
insert into products (name, slug, image_url, price, is_active) values ('Test Product', 'test-product', '', 999.99, TRUE);
