#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: personne
#------------------------------------------------------------

CREATE TABLE personne(
        id_personne int (11) Auto_increment  NOT NULL ,
        login       Varchar (50) NOT NULL ,
        mdp         Varchar (50) NOT NULL ,
        nom         Varchar (50) NOT NULL ,
        prenom      Varchar (50) NOT NULL ,
        adresse     Varchar (100) NOT NULL ,
        cdp         Varchar (5) NOT NULL ,
        ville       Varchar (50) NOT NULL ,
        fonction    Varchar (50) NOT NULL ,
        PRIMARY KEY (id_personne ) ,
        UNIQUE (login )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: commande
#------------------------------------------------------------

CREATE TABLE commande(
        id_commande    int (11) Auto_increment  NOT NULL ,
        statut         Varchar (50) NOT NULL ,
        prix_total     Float ,
        date_commande  Datetime ,
        date_livraison Datetime ,
        login          Varchar (50) NOT NULL ,
        PRIMARY KEY (id_commande )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: ligne_commande
#------------------------------------------------------------

CREATE TABLE ligne_commande(
        id_lg_commande int (11) Auto_increment  NOT NULL ,
        quantite       Int NOT NULL ,
        id_commande    Int NOT NULL ,
        id_produit     Int NOT NULL ,
        PRIMARY KEY (id_lg_commande )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: produit
#------------------------------------------------------------

CREATE TABLE produit(
        id_produit    int (11) Auto_increment  NOT NULL ,
        nom_produit   Varchar (50) NOT NULL ,
        categorie     Varchar (50) NOT NULL ,
        prix_unitaire Float NOT NULL ,
        description   Varchar (100) ,
        quantite      Int NOT NULL ,
        login         Varchar (50) NOT NULL ,
        PRIMARY KEY (id_produit )
)ENGINE=InnoDB;

ALTER TABLE commande ADD CONSTRAINT FK_commande_login FOREIGN KEY (login) REFERENCES personne(login);
ALTER TABLE ligne_commande ADD CONSTRAINT FK_ligne_commande_id_commande FOREIGN KEY (id_commande) REFERENCES commande(id_commande);
ALTER TABLE ligne_commande ADD CONSTRAINT FK_ligne_commande_id_produit FOREIGN KEY (id_produit) REFERENCES produit(id_produit);
ALTER TABLE produit ADD CONSTRAINT FK_produit_login FOREIGN KEY (login) REFERENCES personne(login);
