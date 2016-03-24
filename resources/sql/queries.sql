-- :name get-products :? :*
-- :doc retrieves the active product records
SELECT * FROM products WHERE is_active = TRUE

-- :name get-inactive-products :? :*
-- :doc retrieves the inactive product records
SELECT * FROM products WHERE is_active = FALSE

-- :name create-product! :! :n
-- :doc creates a new product record
INSERT INTO products
(name, slug, image_url, price, is_active)
VALUES (:name, :slug, :image_url, :price, :is_active)

-- :name delete-product! :! :n
-- :deletes a product record
UPDATE products
SET is_active = FALSE
WHERE id = :id

-- :name update-product! :! :n
-- :doc update a product record
UPDATE products SET name = :name, image_url = :image_url, price = :price, is_active = :is_active WHERE slug = :slug

-- :name get-product :? :1
-- :doc retrieve a product given the slug.
SELECT * FROM products
WHERE slug = :slug

-- :name get-product-by-id :? :1
-- :doc retrieve a product given the slug.
SELECT * FROM products
WHERE id = :id
