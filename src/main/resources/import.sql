INSERT INTO tb_user (name, email, password)
VALUES
('João Silva', 'joao.silva@example.com', 'senha123'),
('Maria Oliveira', 'maria.oliveira@example.com', 'senha456');

INSERT INTO tb_address (cep, street, number, additional, neighborhood, city_name, state_name)
VALUES
('12345-678', 'Rua das Flores', '123', 'Apto 45', 'Jardim das Palmeiras', 'São Paulo', 'SP'),
('98765-432', 'Av. Paulista', '1000', 'Sala 100', 'Centro', 'São Paulo', 'SP');

INSERT INTO tb_user_address (user_id, address_id, address_type)
VALUES
(1, 1, 1),  -- João Silva, Endereço Rua das Flores, Tipo de Endereço 1
(2, 2, 1);  -- Maria Oliveira, Endereço Av. Paulista, Tipo de Endereço 1

INSERT INTO tb_category (image, "name")
VALUES
('electronics.jpg', 'Electronics'),
('home_appliances.jpg', 'Home Appliances'),
('gaming.jpg', 'Gaming'),
('furniture.jpg', 'Furniture');

INSERT INTO tb_product (description, highlight, image, "name", price, promotion, promotion_price, status, category_id)
VALUES
('Smartphone with high resolution display', TRUE, 'smartphone1.jpg', 'Smartphone X', 799.99, TRUE, 699.99, TRUE, 1),
('Wireless Noise Cancelling Headphones', FALSE, 'headphones1.jpg', 'Headphones Y', 199.99, FALSE, NULL, TRUE, 2),
('4K Ultra HD Smart TV', TRUE, 'tv1.jpg', 'Smart TV Z', 1199.99, TRUE, 999.99, TRUE, 3),
('Gaming Laptop with High Performance GPU', TRUE, 'laptop1.jpg', 'Gaming Laptop W', 1499.99, FALSE, NULL, TRUE, 4),
('Bluetooth Speaker with Deep Bass', FALSE, 'speaker1.jpg', 'Bluetooth Speaker V', 129.99, FALSE, NULL, TRUE, 5),
('Smartwatch with Fitness Tracking', TRUE, 'smartwatch1.jpg', 'Smartwatch U', 299.99, TRUE, 249.99, TRUE, 6),
('Portable Power Bank 10000mAh', FALSE, 'powerbank1.jpg', 'Power Bank T', 49.99, FALSE, NULL, TRUE, 7),
('Wireless Charging Pad', FALSE, 'chargingpad1.jpg', 'Charging Pad S', 39.99, TRUE, 29.99, TRUE, 8),
('Smart Home Hub with Voice Assistant', TRUE, 'smarthub1.jpg', 'Smart Home Hub R', 89.99, FALSE, NULL, TRUE, 9),
('Ergonomic Office Chair', FALSE, 'chair1.jpg', 'Office Chair Q', 159.99, TRUE, 139.99, TRUE, 10),
('4TB External Hard Drive', FALSE, 'harddrive1.jpg', 'External Hard Drive P', 109.99, TRUE, 89.99, TRUE, 11),
('Wireless Ergonomic Mouse', FALSE, 'mouse1.jpg', 'Ergonomic Mouse O', 49.99, FALSE, NULL, TRUE, 12),
('Mechanical Gaming Keyboard', TRUE, 'keyboard1.jpg', 'Gaming Keyboard N', 129.99, FALSE, NULL, TRUE, 13),
('27-inch 144Hz Gaming Monitor', TRUE, 'monitor1.jpg', 'Gaming Monitor M', 349.99, TRUE, 299.99, TRUE, 14),
('Noise Cancelling In-Ear Headphones', FALSE, 'earphones1.jpg', 'In-Ear Headphones L', 99.99, FALSE, NULL, TRUE, 15);

INSERT INTO tb_order (subtotal, freight_charge, total_amount, creation_date, confirmation_date, cancellation_date, delivery_date, delivery_address, customer_id, status_order)
VALUES
(150.00, 10.00, 160.00, NOW(), NULL, NULL, DATE_ADD(NOW(), INTERVAL 3 DAY), 1, 1, 'CREATED'),
(200.00, 15.00, 215.00, NOW(), NOW(), NULL, DATE_ADD(NOW(), INTERVAL 5 DAY), 2, 2, 'CONFIRMED');

INSERT INTO tb_product_order (product_id, order_id, price_on_purchase, promotion_price)
VALUES
(1, 1, 100.00, 90.00),
(2, 1, 50.00, NULL),
(3, 2, 200.00, 180.00);