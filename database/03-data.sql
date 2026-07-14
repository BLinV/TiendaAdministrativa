--  Credenciales de los usuarios:
--     admin    / admin123
--     vendedor / vendedor123
--     cliente  / cliente123

USE www_tienda;

INSERT INTO usuarios (nombre, usuario, clave) VALUES
('Administrador del Sistema', 'admin',    '$2b$10$/tOyXnVlH7ZL12/19Aj6BOuI6m0/heC2mA3GLUuZrdPzqeR1xALjC'),
('Maria Vendedora',           'vendedor', '$2b$10$5i3.elc1fZ7z4gSYSIytauYo3p9R/0KWdp21oNXnRldS.kk3XJB/W'),
('Juan Cliente',              'cliente',  '$2b$10$de5O.k6fbro1Iv4EAWUbnu/sBOunz148aE4XzMJc2goS3cpCMNXWO');

INSERT INTO categorias (nombre, descripcion) VALUES
('Electronica',      'Dispositivos electronicos, computo y accesorios tecnologicos'),
('Calzado',          'Zapatos, zapatillas y calzado deportivo para toda la familia'),
('Hogar',            'Articulos de cocina, decoracion y organizacion del hogar'),
('Deportes',         'Equipamiento deportivo, fitness y actividades al aire libre'),
('Libros',           'Literatura, tecnicos y material de estudio');

INSERT INTO productos (nombre, descripcion, precio, stock, id_categoria) VALUES
-- Electronica (1)
('Laptop Pro 14',            'Portatil 14 pulgadas, 16GB RAM, SSD 512GB',            3499.00, 12, 1),
('Mouse Inalambrico',        'Mouse ergonomico bluetooth, bateria de larga duracion',   89.90, 150, 1),
('Teclado Mecanico RGB',     'Teclado mecanico switches azules, retroiluminado',       249.00, 45, 1),
('Monitor 27 4K',            'Monitor 27 pulgadas resolucion 4K, panel IPS',          1299.00, 20, 1),
('Audifonos Bluetooth',      'Audifonos over-ear con cancelacion de ruido activa',     399.00, 0,  1),

-- Calzado (2)
('Zapato de Vestir Negro',   'Zapato formal de cuero genuino para caballero',          329.00, 30, 2),
('Zapatilla Running Ultra',  'Zapatilla deportiva para correr, suela amortiguada',     459.00, 60, 2),
('Calzado de Hombres Casual','Calzado casual de hombres, comodo para uso diario',      219.00, 40, 2),
('Bota de Montana',          'Bota impermeable para trekking y montana',               689.00, 18, 2),

-- Hogar (3)
('Juego de Sartenes',        'Set de 3 sartenes antiadherentes de aluminio forjado',   279.00, 25, 3),
('Lampara de Escritorio',    'Lampara LED regulable con puerto USB integrado',          99.90, 80, 3),
('Organizador Modular',      'Estanteria modular de 6 compartimentos',                 349.00, 15, 3),
('Cafetera Espresso',        'Cafetera espresso semiautomatica 15 bares',             1150.00, 10, 3),

-- Deportes (4)
('Mancuernas Ajustables',    'Par de mancuernas ajustables de 2 a 24 kg',             899.00, 22, 4),
('Bicicleta Urbana',         'Bicicleta de ciudad, 7 velocidades, cuadro aluminio',   2299.00, 8,  4),
('Colchoneta de Yoga',       'Colchoneta antideslizante de 6mm de grosor',              79.90, 120, 4),
('Balon de Futbol Pro',      'Balon tamaño 5, cosido a mano, apto para cesped',       129.00, 65, 4),

-- Libros (5)
('Clean Code',               'Manual de buenas practicas de desarrollo de software',   189.00, 35, 5),
('El Quijote',               'Edicion anotada de la obra de Miguel de Cervantes',       19.90, 200, 5),
('Patrones de Diseño',       'Los 23 patrones clasicos de la Banda de los Cuatro',     229.00, 28, 5);
