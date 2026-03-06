-- =====================================================
-- INSERTION DES CATEGORIES
-- =====================================================
INSERT INTO CATEGORIE (CODE, LIBELLE, DESCRIPTION) VALUES
                                                       (1, 'Antalgiques et Antipyrétiques', 'Médicaments contre la douleur et la fièvre'),
                                                       (2, 'Anti-inflammatoires', 'Médicaments réduisant l''inflammation'),
                                                       (3, 'Antibiotiques', 'Médicaments pour traiter les infections bactériennes'),
                                                       (4, 'Antihypertenseurs', 'Médicaments pour traiter l''hypertension artérielle'),
                                                       (5, 'Antidiabétiques', 'Médicaments pour traiter le diabète'),
                                                       (6, 'Antihistaminiques', 'Médicaments pour traiter les allergies'),
                                                       (7, 'Vitamines et Compléments', 'Suppléments nutritionnels'),
                                                       (8, 'Médicaments Cardiovasculaires', 'Médicaments pour le cœur et la circulation'),
                                                       (9, 'Médicaments Gastro-intestinaux', 'Médicaments pour les troubles digestifs'),
                                                       (10, 'Médicaments Respiratoires', 'Médicaments pour les troubles respiratoires')
                                                        ON CONFLICT (CODE) DO NOTHING;

ALTER TABLE CATEGORIE ALTER COLUMN CODE RESTART WITH 11;

-- =====================================================
-- INSERTION DES FOURNISSEURS
-- =====================================================
INSERT INTO FOURNISSEUR (ID, NOM, EMAIL) VALUES
                                             (1, 'PharmaPlus', 'alice.dupont@pharmaplus.com'),
                                             (2, 'MediWorld', 'bob.martin@mediworld.com'),
                                             (7, 'Fournisseur Test', 'elaasahli85@gmail.com')
    ON CONFLICT (ID) DO NOTHING;


ALTER TABLE FOURNISSEUR ALTER COLUMN ID RESTART WITH 8;

-- =====================================================
-- RELATION FOURNISSEUR - CATEGORIE (ManyToMany)
-- =====================================================
INSERT INTO fournisseur_categorie (categorie_code, fournisseur_id) VALUES
                                                                       (7, 1),  -- Vitamines -> PharmaPlus
                                                                       (3, 2),  -- Antibiotiques -> MediWorld
                                                                       (2, 7),  -- Anti-inflammatoires -> Fournisseur Test
                                                                       (1, 7)
                                                                    ON CONFLICT (categorie_code, fournisseur_id) DO NOTHING;-- Antalgiques -> Fournisseur Test
-- =====================================================
-- INSERTION DES MEDICAMENTS (SANS FOURNISSEUR_ID)
-- =====================================================

-- Catégorie 1 : Antalgiques
INSERT INTO MEDICAMENT
(NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE,
 UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO,
 INDISPONIBLE, IMAGEURL)
VALUES
    ('Paracétamol 500mg', 1, 'Boîte de 16 comprimés', 2.50, 2, 0, 10, false,
     'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=400'),

    ('Paracétamol 1000mg', 1, 'Boîte de 8 comprimés', 3.20, 50, 0, 100, false,
     'https://images.unsplash.com/photo-1471864190281-a93a3070b6de?w=400');

-- Catégorie 2 : Anti-inflammatoires
INSERT INTO MEDICAMENT
(NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE,
 UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO,
 INDISPONIBLE, IMAGEURL)
VALUES
    ('Diclofénac 50mg', 2, 'Boîte de 20 comprimés', 5.60, 300, 0, 35, false,
     'https://images.unsplash.com/photo-1628771065518-0d82f1938462?w=400'),

    ('Kétoprofène 100mg', 2, 'Boîte de 12 gélules', 6.80, 250, 0, 30, false,
     'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=400');

-- Catégorie 3 : Antibiotiques
INSERT INTO MEDICAMENT
(NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE,
 UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO,
 INDISPONIBLE, IMAGEURL)
VALUES
    ('Amoxicilline 500mg', 3, 'Boîte de 12 gélules', 5.90, 400, 0, 40, false,
     'https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=400');

-- =====================================================
-- INSERTION DES DISPENSAIRES
-- =====================================================
INSERT INTO DISPENSAIRE
(CODE, NOM, CONTACT, FONCTION, ADRESSE, CODE_POSTAL, VILLE, REGION, PAYS, TELEPHONE, FAX)
VALUES
    ('DSP01', 'Dispensaire Central Dakar', 'Dr. Amadou Diop', 'Directeur',
     '15 Avenue Léopold Sédar Senghor', '10200', 'Dakar', 'Dakar',
     'Sénégal', '+221-33-821-5555', '+221-33-821-5556'),

    ('DSP02', 'Dispensaire Saint-Louis', 'Dr. Fatou Sall', 'Chef de Service',
     '42 Rue de la République', '32000', 'Saint-Louis', 'Saint-Louis',
     'Sénégal', '+221-33-961-2345', '+221-33-961-2346');

-- =====================================================
-- INSERTION DES COMMANDES
-- =====================================================
INSERT INTO COMMANDE
(NUMERO, SAISIELE, ENVOYEELE, DISPENSAIRE_CODE, PORT, REMISE,
 DESTINATAIRE, ADRESSE, CODE_POSTAL, VILLE, REGION, PAYS)
VALUES
    (1, '2024-01-15', '2024-01-18', 'DSP01', 15.00, 5.00,
     'Dispensaire Central Dakar', '15 Avenue Léopold Sédar Senghor',
     '10200', 'Dakar', 'Dakar', 'Sénégal'),

    (2, '2024-01-20', '2024-01-23', 'DSP02', 20.00, 3.50,
     'Dispensaire Saint-Louis', '42 Rue de la République',
     '32000', 'Saint-Louis', 'Saint-Louis', 'Sénégal');

ALTER TABLE COMMANDE ALTER COLUMN NUMERO RESTART WITH 3;

-- =====================================================
-- INSERTION DES LIGNES
-- =====================================================
INSERT INTO LIGNE (COMMANDE_NUMERO, MEDICAMENT_REFERENCE, QUANTITE) VALUES
                                                                        (1, 1, 100),
                                                                        (1, 2, 50),
                                                                        (2, 1, 60),
                                                                        (2, 5, 40);

-- =====================================================
-- NOTE
-- Paracétamol 500mg (Stock=2 < Niveau=10)
-- Catégorie 1 liée au Fournisseur 7
-- => Déclenche un mail à : elaasahli85@gmail.com
-- =====================================================
