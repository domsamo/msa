CREATE TABLE `users` (
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `user_id` VARCHAR(50) NULL DEFAULT '0',
                         `pwd` VARCHAR(50) NULL DEFAULT '0',
                         `name` VARCHAR(50) NULL DEFAULT '0',
                         `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT users_PK PRIMARY KEY(id)
)
    COLLATE='utf8mb4_general_ci'
;

CREATE TABLE `orders` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `order_id` VARCHAR(255) NULL,
                          `product_id` VARCHAR(120) NULL,
                          `qty` int NULL DEFAULT 0,
                          `unit_price` int NULL DEFAULT 0,
                          `totol_price` int NULL DEFAULT 0,
                          `user_id` VARCHAR(255) NULL DEFAULT '0',
                          `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT users_PK PRIMARY KEY(id)
)
    COLLATE='utf8mb4_general_ci'
;

CREATE TABLE `catalog` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `product_id` VARCHAR(120) NULL,
                           `product_name` VARCHAR(255) NULL,
                           `unit_price` int NULL DEFAULT 0,
                           `stock` int NULL DEFAULT 0,
                           `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT users_PK PRIMARY KEY(id)
)
    COLLATE='utf8mb4_general_ci'
;
