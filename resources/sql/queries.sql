-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id

-- :name get-products :? :*
-- :doc retrieves the active product records
SELECT * FROM products WHERE is_active = TRUE

-- :name create-product! :! :n
-- :doc creates a new product record
INSERT INTO products
(name, slug, image_url, price, is_active)
VALUES (:name, :slug, :image_url, :price, :is_active)

-- :name get-product :? :1
-- :doc retrieve a product given the slug.
SELECT * FROM products
WHERE slug = :slug
