create database homebrew;
use homebrew;

create table ricetta(
     nomeBirra varchar(50) NOT NULL PRIMARY KEY,
     tempo double,
     procedimento varchar(500),
     titoloNota varchar(50),
     descrizioneNota varchar(500))
     Engine=Innodb;
	
create table ingrediente (
     nomeIngrediente varchar(50) NOT NULL PRIMARY KEY,
     quantita double,
     tipo enum('malto','luppoli','zucchero','acqua','lievito'),
     bloccato boolean)
     Engine=Innodb;
	
create table ricettaIngrediente(
     nomeBirra varchar(50) NOT NULL,
	 nomeIngrediente varchar(50) NOT NULL,
     PRIMARY KEY (nomeBirra, nomeIngrediente),
     FOREIGN KEY (nomeBirra) REFERENCES ricetta(nomeBirra),
     FOREIGN KEY (nomeIngrediente) REFERENCES ingrediente(nomeIngrediente))
     Engine = Innodb;
	
create table attrezzatura(
     nomeAttrezzatura varchar(50) NOT NULL PRIMARY KEY,
     portata double,
     tipo enum('tubo','fermentatore','cisterna'))
     Engine=Innodb;

create table ricettaAttrezzatura(
     nomeBirra varchar(50) NOT NULL,
     nomeAttrezzatura varchar(50) NOT NULL,
     PRIMARY KEY (nomeBirra, nomeAttrezzatura),
     FOREIGN KEY (nomeBirra) REFERENCES ricetta (nomeBirra),
     FOREIGN KEY (nomeAttrezzatura) REFERENCES attrezzatura (nomeAttrezzatura))
     Engine=Innodb;
