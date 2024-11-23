-- Inserção para tb_store
INSERT INTO tb_store (id, "domain", "name") VALUES
(1, 'store1.com', 'Store One');

-- Inserções para tb_user
INSERT INTO tb_user (id, email, "name", "password", store_id) VALUES
(1, 'breno@gmail.com', 'Breno', 'password1', 1),
(2, 'harlon@gmail.com', 'Harlon', 'password2', 1),
(3, 'pedro@gmail.com', 'Pedro', 'password3', 1);

-- Inserções para tb_address
INSERT INTO tb_address (id, additional, cep, city_name, neighborhood, "number", state_name, street, store_id) VALUES
(1, 'Apartment 1', '12345-678', 'City One', 'Neighborhood A', '100', 'State A', 'Street One', 1),
(2, 'Apartment 2', '23456-789', 'City One', 'Neighborhood B', '200', 'State A', 'Street Two', 1),
(3, 'House', '34567-890', 'City One', 'Neighborhood C', '300', 'State A', 'Street Three', 1);

-- Inserções para tb_category
INSERT INTO tb_category (id, image, "name", store_id) VALUES
(1, 'image1.jpg', 'Category One', 1),
(2, 'image2.jpg', 'Category Two', 1),
(3, 'image3.jpg', 'Category Three', 1);

-- Inserções para tb_order
INSERT INTO tb_order (id, cancellation_date, confirmation_date, creation_date, delivery_date, freight_charge, status_order, subtotal, total_amount, customer_id, delivery_address, store_id) VALUES
(1, NULL, '2023-09-10 10:30:00', '2023-09-10 10:00:00', '2023-09-15 12:00:00', 10.00, 'Confirmed', 100.00, 110.00, 1, 1, 1),
(2, '2023-09-11 09:00:00', NULL, '2023-09-11 08:00:00', NULL, 15.00, 'Cancelled', 200.00, 215.00, 2, 2, 1),
(3, NULL, '2023-09-12 14:00:00', '2023-09-12 13:00:00', '2023-09-17 18:00:00', 20.00, 'Confirmed', 300.00, 320.00, 3, 3, 1);

-- Inserções para tb_product
INSERT INTO tb_product (id, description, highlight, image_url, "name", price, promotion, promotion_price, status, store_id, category_id, brand) VALUES
(1, 'Broca diamantada para procedimentos de precisão', true, 'broca_diamantada.jpg', 'Broca Diamantada', 15.00, false, NULL, true, 1, 1, 'Diamante Dental'),
(2, 'Espelho clínico para melhor visualização intraoral', false, 'espelho_clinico.jpg', 'Espelho Clínico', 20.00, true, 18.00, true, 1, 1, 'ClinicoPro'),
(3, 'Kit de curetas periodontais para raspagem de tártaro', true, 'cureta_periodontal.jpg', 'Curetas Periodontais', 75.00, false, NULL, true, 1, 1, 'Higiene Dental'),
(4, 'Sugador cirúrgico descartável', false, 'sugador_cirurgico.jpg', 'Sugador Cirúrgico', 10.00, true, 9.00, true, 1, 2, 'SurgicalTools'),
(5, 'Espátula para manipulação de resinas e materiais', true, 'espatula.jpg', 'Espátula Odontológica', 12.00, false, NULL, true, 1, 2, 'ResinCare'),
(6, 'Alavanca para extração dentária', false, 'alavanca_extracao.jpg', 'Alavanca de Extração', 50.00, true, 45.00, true, 1, 3, 'Extractions Inc.'),
(7, 'Pinça para sutura e manipulação de materiais', true, 'pinca_sutura.jpg', 'Pinça de Sutura', 22.00, false, NULL, true, 1, 3, 'SutureMaster'),
(8, 'Agulha carpule para anestesia local', false, 'agulha_carpule.jpg', 'Agulha Carpule', 5.00, true, 4.50, true, 1, 1, 'Anesthesia Plus'),
(9, 'Composto resinoso fotopolimerizável', true, 'resina_composta.jpg', 'Resina Composta', 80.00, false, NULL, true, 1, 1, 'ResinTech'),
(10, 'Fio dental para profilaxia', false, 'fio_dental.jpg', 'Fio Dental', 8.00, true, 7.50, true, 1, 2, 'ProCare'),
(11, 'Escova interdental para limpeza de espaços', true, 'escova_interdental.jpg', 'Escova Interdental', 10.00, false, NULL, true, 1, 2, 'CleanBrush'),
(12, 'Kit de bandejas de aço inoxidável para instrumentação', true, 'bandeja_inox.jpg', 'Bandeja Inox', 25.00, false, NULL, true, 1, 3, 'Instrument Pro');

-- Inserções para tb_product_order
INSERT INTO tb_product_order (id, price_on_purchase, promotion_price, order_id, product_id) VALUES
(1, 50.00, NULL, 1, 1),
(2, 70.00, 65.00, 2, 2),
(3, 100.00, NULL, 3, 3);

-- Inserções para tb_user_address
INSERT INTO tb_user_address (id, address_type, address_id, user_id) VALUES
(1, 1, 1, 1),
(2, 2, 2, 2),
(3, 3, 3, 3);
