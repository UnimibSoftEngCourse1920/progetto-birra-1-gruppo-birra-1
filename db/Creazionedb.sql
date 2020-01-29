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
     tipo enum('MALTO','LUPPOLI','ZUCCHERO','ACQUA','LIEVITO'),
     bloccato boolean)
     Engine=Innodb;
	
create table ricettaIngrediente(
     nomeBirra varchar(50) NOT NULL,
     nomeIngrediente varchar(50) NOT NULL,
     percentuale double,
     PRIMARY KEY (nomeBirra, nomeIngrediente),
     FOREIGN KEY (nomeBirra) REFERENCES ricetta(nomeBirra),
     FOREIGN KEY (nomeIngrediente) REFERENCES ingrediente(nomeIngrediente))
     Engine = Innodb;
	
create table attrezzatura(
     nomeAttrezzatura varchar(50) NOT NULL PRIMARY KEY,
     portata double,
     tipo enum('TUBO','FERMENTATORE','CISTERNA'))
     Engine=Innodb;

create table ricettaAttrezzatura(
     nomeBirra varchar(50) NOT NULL,
     nomeAttrezzatura varchar(50) NOT NULL,
     PRIMARY KEY (nomeBirra, nomeAttrezzatura),
     FOREIGN KEY (nomeBirra) REFERENCES ricetta (nomeBirra),
     FOREIGN KEY (nomeAttrezzatura) REFERENCES attrezzatura (nomeAttrezzatura))
     Engine=Innodb;

insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('tubo1', 5.0, 'TUBO');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('tubo2', 10.0, 'TUBO');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('tubo3', 12.0, 'TUBO');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('cisterna1', 3.0, 'CISTERNA');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('cisterna2', 9.0, 'CISTERNA');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('cisterna3', 15.0, 'CISTERNA');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('fermentatore1', 5.0, 'FERMENTATORE');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('fermentatore2', 9.0, 'FERMENTATORE');


insert into attrezzatura (nomeAttrezzatura, portata, tipo) values('fermentatore3', 12.0, 'FERMENTATORE');