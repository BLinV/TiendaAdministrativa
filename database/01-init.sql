CREATE DATABASE IF NOT EXISTS www_tienda DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'tienda_admin'@'localhost' IDENTIFIED BY '$NZoj0hndbbn4qexoU0miFLCRhu4OzZeD4fpfd2wa';
GRANT ALL PRIVILEGES ON www_tienda.* TO 'tienda_admin'@'localhost';

CREATE USER IF NOT EXISTS 'tienda_user'@'localhost' IDENTIFIED BY '$g4lNGnZRZHHuuLuaezxVTb0KH36uMJJLs8jpT74Ma';
GRANT SELECT, INSERT, UPDATE, DELETE ON www_tienda.* TO 'tienda_user'@'localhost';
