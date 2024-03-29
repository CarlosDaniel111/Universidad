CREATE DATABASE CONGRESOS
GO
USE CONGRESOS
GO
-- CREACION DE TABLAS
CREATE TABLE CONGRESOS(
CONID INT NOT NULL,
CONNOMBRE NVARCHAR(20) NOT NULL,
CONDESCRIPCION NVARCHAR(50) NULL,
CONFECHAINI DATETIME NOT NULL,
CONFECHAFIN DATETIME NOT NULL,
CONLUGAR NVARCHAR(20) NOT NULL)
GO
CREATE TABLE EXPOSITORES(
EXPID INT NOT NULL,
EXPNOMBRE NVARCHAR(20) NOT NULL,
EXPAPELLIDOS NVARCHAR(40) NOT NULL,
EXPCORREO NVARCHAR(50) NULL,
EXPCELULAR NCHAR(10) NULL)
GO
CREATE TABLE MUNICIPIOS(
MUNID INT NOT NULL,
MUNNOMBRE NVARCHAR(20) NOT NULL)
GO
CREATE TABLE ESCUELAS(
ESCID INT NOT NULL,
ESCNOMBRE NVARCHAR(30) NOT NULL,
ESCDOMICILIO NVARCHAR(30) NOT NULL,
MUNID INT NOT NULL)
GO
CREATE TABLE ESTUDIANTES(
ESTID INT NOT NULL,
ESTNOMBRE NVARCHAR(20) NOT NULL,
ESTAPELLIDOS NVARCHAR(40) NOT NULL,
ESTDOMICILIO NVARCHAR(30) NULL,
ESTCORREO NVARCHAR(50) NULL,
ESTCELULAR NCHAR(10) NULL,
ESCID INT NOT NULL)
GO
CREATE TABLE REGISTRO(
FOLIO INT NOT NULL,
FECHA DATETIME NOT NULL,
ESTID INT NOT NULL,
CONID INT NOT NULL)
GO
CREATE TABLE EVENTOS(
EVEID INT NOT NULL,
EVENOMBRE NVARCHAR(20) NOT NULL,
EVEDESCRIPCION NVARCHAR(50) NOT NULL,
EVEFECHA DATETIME NOT NULL,
EVELUGAR NVARCHAR(20) NOT NULL,
EVECOSTO NUMERIC(7,2) NOT NULL,
EXPID INT NOT NULL)
GO
CREATE TABLE EVENTOSXREG(
FOLIO INT NOT NULL,
EVEID INT NOT NULL)
GO
-- CREACION DE LLAVES PRIMARIAS
ALTER TABLE CONGRESOS ADD CONSTRAINT PK_CONGRESOS PRIMARY KEY(CONID)
ALTER TABLE EXPOSITORES ADD CONSTRAINT PK_EXPOSITORES PRIMARY KEY(EXPID)
ALTER TABLE MUNICIPIOS ADD CONSTRAINT PK_MUNICIPIOS PRIMARY KEY(MUNID)
ALTER TABLE ESCUELAS ADD CONSTRAINT PK_ESCUELAS PRIMARY KEY(ESCID)
ALTER TABLE ESTUDIANTES ADD CONSTRAINT PK_ESTUDIANTES PRIMARY KEY(ESTID)
ALTER TABLE REGISTRO ADD CONSTRAINT PK_REGISTRO PRIMARY KEY(FOLIO)
ALTER TABLE EVENTOS ADD CONSTRAINT PK_EVENTOS PRIMARY KEY(EVEID)
ALTER TABLE EVENTOSXREG ADD CONSTRAINT PK_EVENTOSXREG PRIMARY KEY(FOLIO,EVEID)
GO
-- LLAVES EXTERNAS
ALTER TABLE ESCUELAS ADD
CONSTRAINT FK_ESCUELAS_MUN FOREIGN KEY(MUNID) REFERENCES MUNICIPIOS(MUNID)
GO
ALTER TABLE ESTUDIANTES ADD
CONSTRAINT FK_ESTUDIANTES_ESC FOREIGN KEY(ESCID) REFERENCES ESCUELAS(ESCID)
GO
ALTER TABLE EVENTOS ADD
CONSTRAINT FK_EVENTOS_EXP FOREIGN KEY(EXPID) REFERENCES EXPOSITORES(EXPID)
GO
ALTER TABLE REGISTRO ADD
CONSTRAINT FK_REGISTRO_EST FOREIGN KEY(ESTID) REFERENCES ESTUDIANTES(ESTID),
CONSTRAINT FK_REGISTRO_CON FOREIGN KEY(CONID) REFERENCES CONGRESOS(CONID)
GO
ALTER TABLE EVENTOSXREG ADD
CONSTRAINT FK_EVEXREG_REG FOREIGN KEY(FOLIO) REFERENCES REGISTRO(FOLIO),
CONSTRAINT FK_EVEXREG_EVE FOREIGN KEY(EVEID) REFERENCES EVENTOS(EVEID)
GO
-- LLAVES UNICAS
ALTER TABLE ESTUDIANTES ADD
CONSTRAINT UC_ESTUDIANTES_COR UNIQUE(ESTCORREO)
GO
ALTER TABLE EXPOSITORES ADD
CONSTRAINT UC_EXPOSITORES_COR UNIQUE(EXPCORREO)
GO
-- VALORES POR DEFAULT
ALTER TABLE ESTUDIANTES ADD
CONSTRAINT DC_ESTUDIANTES_DOM DEFAULT('DOMICILIO DESCONOCIDO') FOR ESTDOMICILIO,
CONSTRAINT DC_ESTUDIANTES_CEL DEFAULT('SIN TELEFO') FOR ESTCELULAR
GO
ALTER TABLE EXPOSITORES ADD
CONSTRAINT DC_EXPOSITORES_CEL DEFAULT('SIN TELEFO') FOR EXPCELULAR
GO
-- DE COMPROBACION
ALTER TABLE CONGRESOS ADD
CONSTRAINT CC_CONGRESOS_FINI CHECK (CONFECHAINI > '1-1-2018'),
CONSTRAINT CC_CONGRESOS_FFIN CHECK (CONFECHAFIN >= CONFECHAINI)
GO
ALTER TABLE REGISTRO ADD
CONSTRAINT CC_REGISTRO_FECHA CHECK (FECHA > '1-1-2018')
GO
ALTER TABLE EVENTOS ADD
CONSTRAINT CC_EVENTOS_FECHA CHECK (EVEFECHA > '1-1-2018'),
CONSTRAINT CC_EVENTOS_COSTO CHECK (EVECOSTO > 0)
GO
-- INSERCIONES
INSERT INTO CONGRESOS VALUES(1, 'INGENIEBROS', 'CONGRESO CHILO', '09/09/2023', '09/14/2023', 'MAZATLAN')
INSERT INTO CONGRESOS VALUES(2, 'TALLER DE LECTURA', 'LECTORES EN PROCESO', '10/09/2023', '10/14/2023', 'LOS MOCHIS')
INSERT INTO CONGRESOS VALUES(3, 'INNOVACION', 'BUENO BARATO', '11/09/2023', '11/14/2023', 'REINO UNIDO')
INSERT INTO CONGRESOS VALUES(4, 'BRINCA QUE BRNCA', 'SALTOS POR DOQUIER', '12/09/2023', '12/14/2023', 'FINLANDIA')
INSERT INTO CONGRESOS VALUES(5, 'TALLER DE ING', 'INNOVATE', '01/09/2024', '01/14/2024', 'MAZATLAN')
GO
INSERT INTO MUNICIPIOS VALUES (1, 'CULIACAN')
INSERT INTO MUNICIPIOS VALUES (2, 'LOS MOCHIS')
INSERT INTO MUNICIPIOS VALUES (3, 'NAVOJOA')
INSERT INTO MUNICIPIOS VALUES (4, 'MAZATLAN')
INSERT INTO MUNICIPIOS VALUES (5, 'NAVOLATO')
GO
INSERT INTO EXPOSITORES VALUES (1, 'RAMON', 'LOPEZ HERNANDEZ', 'ramon@gmail.com', '6676938710')
INSERT INTO EXPOSITORES VALUES (2, 'ESPERANZA', 'HERAS VILLANUEVA', 'espvll@gmail.com', '667730273')
INSERT INTO EXPOSITORES VALUES (3, 'ROBERTO', 'GOMEZ BOLA�OS', 'rober@gmail.com', DEFAULT)
INSERT INTO EXPOSITORES VALUES (4, 'MARIA', 'ANTONIETA DE LAS NIEVES', 'nieves@gmail.com', '6676936193')
INSERT INTO EXPOSITORES VALUES (5, 'CARLOS', 'BELTRAN ZARAGOZA', 'carlos@gmail.com', '6675948172')
GO
INSERT INTO ESCUELAS VALUES(1, 'LOBOS', 'VILLAS DEL RIO', 1)
INSERT INTO ESCUELAS VALUES(2, 'CETIS', 'CALLE LAS AMAPOLAS', 2)
INSERT INTO ESCUELAS VALUES(3, 'SENDA', 'LAS QUINTAS', 3)
INSERT INTO ESCUELAS VALUES(4, 'JEAN PIAGET', 'LAS QUINTAS', 3)
INSERT INTO ESCUELAS VALUES(5, 'SEBEC', 'VILLA JUAREZ', 5)
GO
INSERT INTO ESTUDIANTES VALUES(1, 'CESAR', 'VALENZUELA', 'CALLE TAMAULIPAS', 'CESAR@GMAIL.COM', '6684720392', 1)
INSERT INTO ESTUDIANTES VALUES(2, 'CARLOS', 'BELTRAN', 'CALLE XICOTENCATL', 'CARLOS@GMAIL.COM', '6684720392', 2)
INSERT INTO ESTUDIANTES VALUES(3, 'KAREN', 'ONTIVEROS', DEFAULT, 'KAREN@GMAIL.COM', '6684720392', 3)
INSERT INTO ESTUDIANTES VALUES(4, 'ALFONSO', 'DEL SAGRADO CORAZON DE JESUS', 'CALLE LA TUNA', 'ALFON@GMAIL.COM', '6627859302', 4)
INSERT INTO ESTUDIANTES VALUES(5, 'ARMANDO', 'PAREDES', 'CALLE ESPA�A', 'PAREDES@GMAIL.COM', DEFAULT, 5)
GO
INSERT INTO EVENTOS VALUES(1, 'DIVERTIKIDS', 'ANIMADORAS', '01/15/2024', 'BADIRAGUATO', '8000.00', 1)
INSERT INTO EVENTOS VALUES(2, 'PINTACARA', 'ARTISTAS', '02/15/2024', 'LA CONQUISTA', '3000.00', 3)
INSERT INTO EVENTOS VALUES(3, 'EVENTO 2', 'BUEN EVENTO', '05/15/2024', 'CALLE 123', '15000.00', 4)
INSERT INTO EVENTOS VALUES(4, 'EDUCACION FISICA', 'ENTRENO', '03/15/2024', 'BUGAMBILIAS', '1000.00', 5)
INSERT INTO EVENTOS VALUES(5, 'COME COME', 'COMIDA', '04/15/2024', 'BARRANCOS',�'2000.00',�5)
GO
INSERT INTO REGISTRO VALUES(1,'10/10/2023',3,2)
INSERT INTO REGISTRO VALUES(2,'01/10/2023',5,5)
INSERT INTO REGISTRO VALUES(3,'11/12/2023',5,3)
INSERT INTO REGISTRO VALUES(4,'10/13/2023',1,2)
INSERT INTO REGISTRO VALUES(5,'09/13/2023',2,1)
GO
INSERT INTO EVENTOSXREG VALUES(1,4)
INSERT INTO EVENTOSXREG VALUES(4,1)
INSERT INTO EVENTOSXREG VALUES(2,5)
INSERT INTO EVENTOSXREG VALUES(3,2)
INSERT INTO EVENTOSXREG VALUES(5,3)