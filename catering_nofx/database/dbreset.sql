DELETE
FROM catering.menuitems
WHERE true;
DELETE
FROM catering.menusections
WHERE true;
DELETE
FROM catering.menufeatures
WHERE true;
DELETE
FROM catering.menus
WHERE true;
DELETE
FROM catering.services
WHERE true;
DELETE
FROM catering.summarysheets
WHERE TRUE;
DELETE
FROM catering.events
WHERE TRUE;

DELETE
FROM catering.tasks
WHERE TRUE;

DELETE
FROM catering.shifts
WHERE TRUE;

/* FILL Menus */
LOCK TABLES `catering`.menus WRITE;
/*!40000 ALTER TABLE `catering`.menus
    DISABLE KEYS */;
INSERT INTO `catering`.menus
VALUES (80, 'Coffee break mattutino', 2, 1),
       (82, 'Coffee break pomeridiano', 2, 1),
       (86, 'Cena di compleanno pesce', 3, 1);
/*!40000 ALTER TABLE `catering`.menus
    ENABLE KEYS */;
UNLOCK TABLES;

/* Fill MenuSections */
LOCK TABLES `catering`.menusections WRITE;
/*!40000 ALTER TABLE  `catering`.menusections
    DISABLE KEYS */;
INSERT INTO  `catering`.menusections
VALUES (41, 86, 'Antipasti', 0),
       (42, 86, 'Primi', 1),
       (43, 86, 'Secondi', 2),
       (44, 86, 'Dessert', 3);
/*!40000 ALTER TABLE  `catering`.menusections
    ENABLE KEYS */;
UNLOCK TABLES;

/* Fill MenuItems */
LOCK TABLES `catering`.menuitems WRITE;
/*!40000 ALTER TABLE `catering`.menuitems
    DISABLE KEYS */;
INSERT INTO `catering`.menuitems
VALUES (96, 80, 0, 'Croissant vuoti', 9, 0),
       (97, 80, 0, 'Croissant alla marmellata', 9, 1),
       (98, 80, 0, 'Pane al cioccolato mignon', 10, 2),
       (99, 80, 0, 'Panini al latte con prosciutto crudo', 12, 4),
       (100, 80, 0, 'Panini al latte con prosciutto cotto', 12, 5),
       (101, 80, 0, 'Panini al latte con formaggio spalmabile alle erbe', 12, 6),
       (102, 80, 0, 'Girelle all\'uvetta mignon', 11, 3),
       (103, 82, 0, 'Biscotti', 13, 1),
       (104, 82, 0, 'Lingue di gatto', 14, 2),
       (105, 82, 0, 'Bigné alla crema', 15, 3),
       (106, 82, 0, 'Bigné al caffè', 15, 4),
       (107, 82, 0, 'Pizzette', 16, 5),
       (108, 82, 0, 'Croissant al prosciutto crudo mignon', 9, 6),
       (109, 82, 0, 'Tramezzini tonno e carciofini mignon', 17, 7),
       (112, 86, 41, 'Vitello tonnato', 1, 0),
       (113, 86, 41, 'Carpaccio di spada', 2, 1),
       (114, 86, 41, 'Alici marinate', 3, 2),
       (115, 86, 42, 'Penne alla messinese', 5, 0),
       (116, 86, 42, 'Risotto alla zucca', 20, 1),
       (117, 86, 43, 'Salmone al forno', 8, 0),
       (118, 86, 44, 'Sorbetto al limone', 18, 0),
       (119, 86, 44, 'Torta Saint Honoré', 19, 1);
/*!40000 ALTER TABLE `catering`.menuitems
    ENABLE KEYS */;
UNLOCK TABLES;

/* Fill MenuFeatures */
LOCK TABLES `catering`.menufeatures WRITE;
/*!40000 ALTER TABLE `catering`.menufeatures
    DISABLE KEYS */;
INSERT INTO `catering`.menufeatures
VALUES (80, 'Richiede cuoco', 0),
       (80, 'Buffet', 0),
       (80, 'Richiede cucina', 0),
       (80, 'Finger food', 0),
       (80, 'Piatti caldi', 0),
       (82, 'Richiede cuoco', 0),
       (82, 'Buffet', 0),
       (82, 'Richiede cucina', 0),
       (82, 'Finger food', 0),
       (82, 'Piatti caldi', 0),
       (86, 'Richiede cuoco', 0),
       (86, 'Buffet', 0),
       (86, 'Richiede cucina', 0),
       (86, 'Finger food', 0),
       (86, 'Piatti caldi', 0);
/*!40000 ALTER TABLE `catering`.menufeatures
    ENABLE KEYS */;
UNLOCK TABLES;

/* FIll Shifts */
LOCK TABLES `catering`.shifts WRITE;
/*!40000 ALTER TABLE `catering`.shifts
    DISABLE KEYS */;
INSERT INTO `catering`.shifts (`id`, `date`, `h_start`, `h_end`, `place`) VALUES
 (1,'2024-08-30', '09:00:00', '18:00:00', 'sede'),
 (2,'2024-08-30', '18:00:00', '23:00:00', 'sede'),
 (3,'2024-08-31', '09:00:00', '18:00:00', 'sede'),
 (4,'2024-08-31', '18:00:00', '23:00:00', 'sede');

/*!40000 ALTER TABLE `catering`.shifts
    ENABLE KEYS */;
UNLOCK TABLES;

/* FIll Services */
LOCK TABLES `catering`.services WRITE;
/*!40000 ALTER TABLE `catering`.services
    DISABLE KEYS */;
INSERT INTO `catering`.services (`id`, `event_id`, `name`, `proposed_menu_id`, `approved_menu_id`, `service_date`, `time_start`, `time_end`, `participants`, `id_menu`)
VALUES
    (1, 2, 'Cena', 86, 0, '2024-08-13', '20:00:00', '23:30:00', 25, 80),
    (2, 1, 'Coffee break mattino', 0, 80, '2024-09-25', '10:30:00', '11:30:00', 100, 82),
    (3, 1, 'Colazione di lavoro', 0, 0, '2024-09-25', '13:00:00', '14:00:00', 80, 86),
    (4, 1, 'Coffee break pomeriggio', 0, 82, '2024-09-25', '16:00:00', '16:30:00', 100, NULL),
    (5, 1, 'Cena sociale', 0, 0, '2024-09-25', '20:00:00', '22:30:00', 40, NULL),
    (6, 3, 'Pranzo giorno 1', 0, 0, '2024-10-02', '12:00:00', '15:00:00', 200, NULL),
    (7, 3, 'Pranzo giorno 2', 0, 0, '2024-10-03', '12:00:00', '15:00:00', 300, NULL),
    (8, 3, 'Pranzo giorno 3', 0, 0, '2024-10-04', '12:00:00', '15:00:00', 400, NULL);
/*!40000 ALTER TABLE `catering`.services
    ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `catering`.events WRITE;
/*!40000 ALTER TABLE `catering`.events
    DISABLE KEYS */;
INSERT INTO `catering`.events(`id`, `name`, `date_start`, `date_end`, `participants`, `organizer_id`) VALUES
(1, 'Convegno Agile Community', '2024-09-25', '2020-09-25', 100, 2),
(2, 'Compleanno di Manuela', '2024-08-13', '2020-08-13', 25, 2),
(3, 'Fiera del Sedano Rapa', '2024-10-02', '2020-10-04', 400, 1);


/*!40000 ALTER TABLE `catering`.events
    ENABLE KEYS */;
UNLOCK TABLES;
