INSERT INTO category (id, name) VALUES('573a9865-c6b4-4453-9626-e8d393dd7508', 'Monitores');

INSERT INTO category (id, name) VALUES('d4689483-560a-4518-acb8-eff748425147', 'Monitor Gamer');

INSERT INTO product (id, name, description, price, available) VALUES ('4568aa76-ab9e-471f-a6bc-9d28b99515d2', 'Monitor Gamer LG UltraGear 27 Full HD', 'Monitor Gamer LG UltraGear 27 Full HD, 144Hz, 1ms, IPS, HDMI e DisplayPort, HDR 10, 99% sRGB, FreeSync Premium, VESA - 27GN65R', 999.99, true);

INSERT INTO product_category (product_id, category_id) VALUES('4568aa76-ab9e-471f-a6bc-9d28b99515d2', '573a9865-c6b4-4453-9626-e8d393dd7508');

INSERT INTO product_category (product_id, category_id) VALUES('4568aa76-ab9e-471f-a6bc-9d28b99515d2', 'd4689483-560a-4518-acb8-eff748425147');

INSERT INTO picture (id, name, description, url, size, content_type, product_id) VALUES('490ee725-949d-4f2b-aa88-8de7f5a0b91f', '490ee725-949d-4f2b-aa88-8de7f5a0b91f_monitor-gamer-lg-ultragear-27.jpg', 'principal', 'https://guilheme.s3.sa-east-1.amazonaws.com/products-catalog/490ee725-949d-4f2b-aa88-8de7f5a0b91f_monitor-gamer-lg-ultragear-27.jpg', 84719, 'image/jpeg', '4568aa76-ab9e-471f-a6bc-9d28b99515d2');