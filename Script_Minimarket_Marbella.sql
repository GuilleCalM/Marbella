-- ------------------Creacion de BD (Ejecutar antes de primera ejecución de aplicativo web)---------------------
create database marbella CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use marbella;

-- --------Inserts básicos para las tablas (Ejecutar despues de primera ejecución de aplicativo web)------------

INSERT INTO tipo_usuario VALUES (1,'Cliente');
INSERT INTO tipo_usuario VALUES (2,'Vendedor');
INSERT INTO tipo_usuario VALUES (3,'Admin');

INSERT INTO usuario VALUES (1001,'$2a$12$GAUrz2hK7Kb3QWbZkIrBrOLOpfmalw22RYhzPGt74s/k4XHfYGVKu','admin',3);
INSERT INTO usuario VALUES (1002,'$2a$12$GAUrz2hK7Kb3QWbZkIrBrOLOpfmalw22RYhzPGt74s/k4XHfYGVKu','vendedor1',2);
INSERT INTO usuario VALUES (1003,'$2a$12$GAUrz2hK7Kb3QWbZkIrBrOLOpfmalw22RYhzPGt74s/k4XHfYGVKu','vendedor2',2);

INSERT INTO categoria VALUES (1,'Abarrotes');
INSERT INTO categoria VALUES (2,'Frutas y verduras');
INSERT INTO categoria VALUES (3,'Lácteos y carnes');
INSERT INTO categoria VALUES (4,'Aseo y limpieza');
INSERT INTO categoria VALUES (5,'Bebidas');
INSERT INTO categoria VALUES (6,'Licores');
INSERT INTO categoria VALUES (7,'Librería');

INSERT INTO estado_pedido VALUES (1,'Creado');
INSERT INTO estado_pedido VALUES (2,'Confirmado');
INSERT INTO estado_pedido VALUES (3,'Reparto');
INSERT INTO estado_pedido VALUES (4,'Entregado');
INSERT INTO estado_pedido VALUES (5,'Cancelado');

INSERT INTO marca VALUES (1,' ');
INSERT INTO marca VALUES (2,'Cocinero');
INSERT INTO marca VALUES (3,'Costeño');
INSERT INTO marca VALUES (4,'Florida');
INSERT INTO marca VALUES (5,'3 ositos');
INSERT INTO marca VALUES (6,'Cartavio');
INSERT INTO marca VALUES (7,'Kirma');
INSERT INTO marca VALUES (8,'Favorita');
INSERT INTO marca VALUES (9,'Universal');
INSERT INTO marca VALUES (10,'Nicolini');
INSERT INTO marca VALUES (11,'Gloria');
INSERT INTO marca VALUES (12,'Paracas');
INSERT INTO marca VALUES (13,'Moncler');
INSERT INTO marca VALUES (14,'Savital');
INSERT INTO marca VALUES (15,'Doctor');
INSERT INTO marca VALUES (16,'Colgate');
INSERT INTO marca VALUES (17,'Sapolio');
INSERT INTO marca VALUES (18,'Bolivar');
INSERT INTO marca VALUES (19,'Clorox');
INSERT INTO marca VALUES (20,'Cielo');
INSERT INTO marca VALUES (21,'San Luis');
INSERT INTO marca VALUES (22,'San Mateo');
INSERT INTO marca VALUES (23,'Inka Kola');
INSERT INTO marca VALUES (24,'Coca Cola');
INSERT INTO marca VALUES (25,'Concordia');
INSERT INTO marca VALUES (26,'Frugos Valle');
INSERT INTO marca VALUES (27,'Tampico');
INSERT INTO marca VALUES (28,'Volt');
INSERT INTO marca VALUES (29,'Cusqueña');
INSERT INTO marca VALUES (30,'Cristal');
INSERT INTO marca VALUES (31,'Pilsen');
INSERT INTO marca VALUES (32,'Tabernero');
INSERT INTO marca VALUES (33,'Baileys');
INSERT INTO marca VALUES (34,'Johnnie Walker');
INSERT INTO marca VALUES (35,'Portón');
INSERT INTO marca VALUES (36,'Piscano');
INSERT INTO marca VALUES (37,'Class & Work');
INSERT INTO marca VALUES (38,'Faber Castell');
INSERT INTO marca VALUES (39,'Standford');
INSERT INTO marca VALUES (40,'Artesco');
INSERT INTO marca VALUES (41,'Pegafan');

-- Abarrotes:
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7001,'Aceite', 2, 'Aceite vegetal en botella de 1L', 11.00,1,100,'aceite_cocinero.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7002,'Arroz', 3, 'Arroz extra graneado en bolsa de 750 g', 4.00,1,58,'costeno_arroz.jpg');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7003,'Atún', 4, 'Filete de atún en lata de 170 g', 6.50,1,95,'atun_florida.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7004,'Avena', 5, 'Avena clásica en bolsa de 120 g', 1.30,1,83,'avena_tres_ositos.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7005,'Azúcar', 6, 'Azucar blanca en bolsa de 1 kg', 4.50,1,51,'azucar_blanca_cartavio.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7006,'Café', 7, 'Café instantaneo Doypack de 45 g', 7.60,1,25,'cafe_kirma.jpg');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7007,'Harina', 8, 'Harina sin preparar en bolsa de 250 g', 1.10,1,80,'harina_favorita.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7008,'Gelatina', 9, 'Gelatina sabor a fresa en bolsa de 150 g', 3.50,1,10,'gelatina_fresa.jpg');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7009,'Fideo', 10, 'Fideo spaghetti en bolsa de 250 g', 2.10,1,150,'tallarin_delgado.webp');

-- Frutas y verduras:
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7010,'Mandarina', 1, 'Mandarina Satsuma sin pepa por 1 Kg', 2.50,2,34,'mandarina.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7011,'Manzana', 1, 'Manzana Israel por 1 Kg', 5.50,2,53,'manzana.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7012,'Naranja', 1, 'Naranja para jugo por 1 Kg', 2.50,2,74,'naranja.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7013,'Papaya', 1, 'Papaya entera por 2 Kg aprox.', 8.40,2,91,'papaya.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7014,'Plátano', 1, 'Plátano de seda por 5 unidades', 2.60,2,29,'platano.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7015,'Cebolla', 1, 'Cebolla roja por 1 Kg', 3.30,2,44,'cebolla_roja.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7016,'Limón', 1, 'Limón ácido por 1 Kg', 4.00,2,33,'limones.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7017,'Papa', 1, 'Papa blanca tipo yungay por 1 Kg', 2.20,2,85,'papa.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7018,'Tomate', 1, 'Tomate italiano por 1 Kg', 3.80,2,173,'tomate.jfif');

-- Lácteos y carnes:
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7019,'Leche evaporada', 11, 'Leche evaporada en lata de 400 g', 3.60,3,58,'leche_gloria_lata.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7020,'Leche UHT', 11, 'Leche UHT en caja de 1 L', 4.80,3,23,'leche_gloria_fresca.jpg');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7021,'Mantequilla', 11, 'Mantequilla con sal en barra de 100 g', 4.40,3,66,'mantequilla_gloria.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7022,'Queso Fresco', 1, 'Queso fresco en molde por porciones de 100 g', 2.70,3,76,'queso_fresco.jpg');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7023,'Huevo', 1, 'Huevo pardo a granel en malla de 1 Kg', 8.50,3,81,'huevos.jpg');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7024,'Yogurt', 11, 'Yogurt semi-descremado en botella de 1 Kg', 6.10,3,23,'yogurt_gloria.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7025,'Pollo fresco', 1, 'Pollo entero con menudencia de 2 Kg aprox.', 8.50,3,45,'Pollo_entero_con_menudencia.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7026,'Milanesa de pollo', 1, 'Milanesa de pollo en paquete de 3 unidades', 6.00,3,36,'milanesa_de_pollo.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7027,'Carne molida', 1, 'Carne molida de res por porciones de 100 g', 2.60,3,64,'carne_molida.jfif');

-- Aseo y limpieza:
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7028,'Papel Higiénico', 12, 'Papel Higiénico doble hoja negro en paquete de 2 unidades ', 7.60,4,77,'papel_higienico.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7029,'Jabón', 13, 'Jabón corporal Nutri care en caja de 1 unidad', 4.00,4,34,'jabon_moncler.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7030,'Shampoo', 14, 'Shampoo de palta y sábila en frasco de 530 ml', 9.90,4,56,'shampoo-savital.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7031,'Pasta dental', 15, 'Pasta dental Ultra Fluor en tubo de 220 g', 6.90,4,55,'pasta_dental.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7032,'Pack cepillos', 16, 'Cepillos de cerda dura en pack de 2 unidades', 7.00,4,93,'cepillo_colgate.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7033,'Detergente', 17, 'Detergente aroma bebé en bolsa de 450 g', 5.30,4,82,'detergente_sapolio.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7034,'Jabón', 18, 'Jabón de ropa clásico en barra de 190 g', 3.20,4,56,'jabon_bolivar.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7035,'Lejía', 19, 'Lejía tradicional en frasco de 680 g', 1.90,4,17,'lejia_clorox.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7036,'Limpiatodo', 17, 'Limpiatodo con esencia de lavanda en frasco de 900 ml', 3.50,4,44,'limpiatodo_sapolio.webp');

-- Bebidas:
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7037,'Agua', 20, 'Agua sin gas en botella de 625 ml', 1.30,5,70,'agua_cielo.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7038,'Pack agua', 21, 'Agua sin gas en pack de bidones de 7 L c/u', 12.90,5,36,'agua_sanluis.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7039,'Agua', 22, 'Agua sin gas en bidón de 7 L', 6.90,5,58,'agua_sanmateo.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7040,'Gaseosa', 23, 'Gaseosa tradicional amarilla en botella de 3 L', 9.90,5,34,'gaseosa_incakola.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7041,'Gaseosa', 24, 'Gaseosa tradicional negra en botella de 3 L', 9.90,5,98,'gaseosa_cocacola.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7042,'Tripack gaseosa', 25, 'Pack de 3 gaseosas Concordia sabores varios de 3L c/u', 15.50,5,122,'pack_gaseosas.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7043,'Pack néctar', 26, 'Néctar de durazno en pack de 2 cajas de 1.5 L c/u', 8.00,5,131,'nectar_frugos.jfif');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7044,'Jugo de naranja', 27, 'Jugo de naranja Citrus Punch en botella de 3 L', 8.50,5,75,'jugo_tampico.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7045,'Bebida energizante', 28, 'Energizante sabor ginseng en botella de 300 ml', 2.20,5,45,'bebida_volt.webp');

-- Licores:
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7046,'Six pack cerveza', 29, 'Six pack de cerveza negra en botellas de 310 ml c/u', 24.90,6,65,'cerveza_cusqueña.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7047,'Six pack cerveza', 30, 'Six pack de cerveza rubia en botellas de 330 ml c/u', 23.90,6,87,'cerveza_cristal.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7048,'Six pack cerveza', 31, 'Six pack de cerveza rubia en botellas de 305 ml c/u', 19.90,6,94,'cerveza_pilsen.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7049,'Vino', 32, 'Variedad Rose en botella de 750 ml', 17.90,6,34,'vino_tabernero.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7050,'Licor de crema', 33, 'Variedad Irish Cream en botella de 750 ml', 69.90,6,55,'crema_baileys.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7051,'Whisky', 34, 'Variedad Black Label en botella de 750 ml', 198.00,6,72,'whisky_johnnie.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7052,'Ron', 6, 'Variedad Black en botella de 1 L', 38.90,6,45,'ron_cartavio.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7053,'Pisco', 35, 'Variedad Mosto Verde en botella de 750 ml', 82.90,6,90,'pisco_porton.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7054,'Pack chilcano', 36, 'Sabor maracuya en pack de 4 botellas de 275 ml c/u', 22.90,6,37,'chilcano_piscano.webp');

-- Librería:
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7055,'Papel bond', 37, 'Papel bond A-4 en paquete de 500 hojas', 14.90,7,31,'papelbond_classwork.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7056,'Colores', 38, 'Caja de colores variados de 15 unidades', 8.20,7,65,'colores_fabercastell.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7057,'Lapiceros', 38, 'Caja de lapiceros de colores variados en paquete de 5 unidades', 5.30,7,88,'lapiceros_fabercastell.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7058,'Cuaderno', 39, 'Cuaderno cuadriculado Deluxe de 92 hojas', 6.20,7,34,'cuaderno_standford.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7059,'Lápices de grafito', 40, 'Caja de lápices de grafito 2B en paquete de 12 unidades', 5.30,7,54,'lapiz_artesco.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7060,'Set de témperas', 40, 'Caja de témperas de colores variados de 7 unidades', 8.70,7,66,'settemperas_artesco.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7061,'Cinta de embalaje', 41, 'Cinta de embalaje transparente de 2 pulgadas', 5.10,7,77,'cintaembalaje_pegafan.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7062,'Perforador', 40, 'Perforador escolar M-01 azul en caja de 1 unidad', 7.30,7,94,'perforador_artesco.webp');
insert into producto (cod_pro,nombre_pro,cod_marca,descripcion_pro,precio_pro,id_cat,stock,img_pro) values(7063,'Pack escolar', 40, 'Pack escolar Artesco con 3 lapiceros de colores, marcador y corrector', 7.00,7,64,'pack_artesco.webp');

DELIMITER $$
CREATE PROCEDURE sp_ventas_ultima_semana()
BEGIN
 SELECT DAYOFWEEK(fecha_pedido) as num_dia, 
         SUM(monto_total) as total
 FROM pedido
  WHERE fecha_pedido BETWEEN CURDATE() - INTERVAL WEEKDAY(CURDATE()) DAY AND CURDATE()
 GROUP BY num_dia
 ORDER BY num_dia;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_reporte_pedidos ()
	BEGIN
		SELECT ep.descrip_estado AS 'name',COUNT(*) AS 'value'
        FROM pedido p INNER JOIN estado_pedido ep ON p.cod_est = ep.cod_est
        WHERE ep.descrip_estado IN ('Creado','Confirmado','Reparto','Entregado','Cancelado')
        GROUP BY ep.descrip_estado;
    END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_productos_mas_vendidos()
BEGIN
  SELECT p.nombre_pro as 'name', SUM(d.cantidad) as 'value'
  FROM producto p
  INNER JOIN detalle_pedido d ON p.cod_pro = d.cod_pro
  INNER JOIN pedido pd on d.cod_ped = pd.cod_ped
  WHERE pd.fecha_pedido >= DATE_SUB(NOW(), INTERVAL 3 DAY)
  GROUP BY p.cod_pro
  ORDER BY value DESC;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_top_10_clientes()
BEGIN
  SELECT c.nombre_cli As 'name', SUM(o.monto_total) AS 'value'
  FROM cliente c
  JOIN pedido o ON c.cod_cli = o.cod_cli
  GROUP BY c.cod_cli
  ORDER BY value DESC
  LIMIT 10;
END$$
DELIMITER ;
