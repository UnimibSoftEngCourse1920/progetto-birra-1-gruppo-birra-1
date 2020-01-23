create database homebrew;
use homebrew;

create table ricetta(
     nomeBirra varchar(50) NOT NULL PRIMARY KEY,
     tempo decimal(3,2),
     procedimento varchar(500),
     titoloNota varchar(50),
     descrizioneNota varchar(500))
     Engine=Innodb;
	
create table ingrediente (
     nome varchar(50) NOT NULL PRIMARY KEY,
     quantità double,
     tipo enum('malto','luppoli','zucchero','acqua','lievito'),
     bloccato boolean)
     Engine=Innodb;
	
create table ricettaIngrediente(
     ricetta varchar(50) NOT NULL,
	 ingrediente varchar(50) NOT NULL,
     PRIMARY KEY (ricetta, ingrediente),
     FOREIGN KEY (ricetta) REFERENCES ricetta(nomeBirra),
     FOREIGN KEY (ingrediente) REFERENCES ingrediente(nome))
     Engine = Innodb;
	
create table attrezzatura(
     nome varchar(50) NOT NULL PRIMARY KEY,
     portata decimal(3,2),
     tipo enum('tubo','fermentatore','cisterna'))
     Engine=Innodb;

create table ricettaAttrezzatura(
     ricetta varchar(50) NOT NULL,
     attrezzatura varchar(50) NOT NULL,
     PRIMARY KEY (ricetta, attrezzatura),
     FOREIGN KEY (ricetta) REFERENCES ricetta (nomeBirra),
     FOREIGN KEY (attrezzatura) REFERENCES attrezzatura (nome))
     Engine=Innodb;
