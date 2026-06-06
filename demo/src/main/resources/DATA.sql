INSERT INTO product_entity (version,name, category, description, price, amount_in_stock)
VALUES (1,'4K Ultra HD Monitor', 'Electronics', '27-inch IPS display with 144Hz refresh rate', 349.99, 45);

INSERT INTO product_entity (version,name, category, description, price, amount_in_stock)
VALUES (1,'Wireless Noise-Canceling Headphones', 'Electronics', 'Over-ear Bluetooth headphones with 30-hour battery life', 199.99, 120);

INSERT INTO product_entity (version,name, category, description, price, amount_in_stock)
VALUES (1,'Denim Jacket', 'Clothing', 'Classic blue cotton denim jacket with button closure', 65.00, 85);


INSERT INTO product_entity (version,name, category, description, price, amount_in_stock)
VALUES (1,'Ergonomic Office Chair', 'Furniture', 'High-back mesh chair with lumbar support and adjustable armrests', 189.95, 30);


INSERT INTO coupon_entity (code, discount, discount_type, start_date, end_date)
VALUES ('SUMMER2024', 20.0, 'PERCENTAGE', '2024-06-01', '2024-08-31');

INSERT INTO coupon_entity (code, discount, discount_type, start_date, end_date)
VALUES ('WELCOME10', 10.0, 'FIXED', '2024-01-01', '2024-12-31');

INSERT INTO coupon_entity (code, discount, discount_type, start_date, end_date)
VALUES ('FLASHSALE', 30.0, 'PERCENTAGE', '2024-07-01', '2024-07-07');

INSERT INTO coupon_entity (code, discount, discount_type, start_date, end_date)
VALUES ('LOYALTY25', 25.0, 'PERCENTAGE', '2024-01-01', '2024-06-30');

INSERT INTO coupon_entity (code, discount, discount_type, start_date, end_date)
VALUES ('SPRING50', 50.0, 'FIXED', '2024-03-01', '2024-05-31');

INSERT INTO coupon_rule_entity (coupon_id, type, "value")
VALUES (1, 'ONCE_PER_USER', '1');

INSERT INTO coupon_rule_entity (coupon_id, type, "value")
VALUES (2, 'ONCE_PER_USER', '1');

INSERT INTO coupon_rule_entity (coupon_id, type, "value")
VALUES (3, 'ONCE_PER_USER', '1');

INSERT INTO coupon_rule_entity (coupon_id, type, "value")
VALUES (4, 'ONCE_PER_USER', '1');

INSERT INTO coupon_rule_entity (coupon_id, type, "value")
VALUES (5, 'ONCE_PER_USER', '1');

