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
