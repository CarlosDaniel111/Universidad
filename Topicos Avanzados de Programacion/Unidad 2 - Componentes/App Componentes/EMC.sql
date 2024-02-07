USE Empresa
GO
CREATE TABLE ESTADOS(
IdEstado INT NOT NULL,
Nombre VARCHAR(50) NOT NULL)
GO
CREATE TABLE MUNICIPIOS(
IdEstado INT NOT NULL,
IdMunicipio INT NOT NULL,
Nombre VARCHAR(50) NOT NULL)
GO
CREATE TABLE CIUDADES(
IdEstado INT NOT NULL,
IdMunicipio INT NOT NULL,
IdCiudad INT NOT NULL,
Nombre VARCHAR(50) NOT NULL)
GO
ALTER TABLE ESTADOS ADD CONSTRAINT PK_ESTADOS PRIMARY KEY(IdEstado)
ALTER TABLE MUNICIPIOS ADD CONSTRAINT PK_MUN PRIMARY KEY(IdEstado,IdMunicipio)
ALTER TABLE CIUDADES ADD CONSTRAINT PK_CIUD PRIMARY KEY(IdEstado,IdMunicipio,IdCiudad)
GO
ALTER TABLE MUNICIPIOS ADD
CONSTRAINT FK_MUN_EST FOREIGN KEY(IdEstado) REFERENCES ESTADOS(IdEstado)
ALTER TABLE CIUDADES ADD
CONSTRAINT FK_CIUD_MUN FOREIGN KEY(IdEstado,IdMunicipio) REFERENCES MUNICIPIOS(IdEstado,IdMunicipio)
GO
INSERT INTO Estados (IdEstado, Nombre) VALUES
(1, 'Nuevo Le�n'),
(2, 'San Luis Potos�'),
(3, 'Sinaloa'),
(4, 'Sonora'),
(5, 'Tabasco')

INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(1, 1, 'Monterrey'),
(1, 2, 'Guadalupe'),
(1, 3, 'San Nicol�s de los Garza'),
(1, 4, 'Apodaca'),
(1, 5, 'Escobedo')

-- San Luis Potos�
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(2, 1, 'San Luis Potos�'),
(2, 2, 'Soledad de Graciano S�nchez'),
(2, 3, 'Matehuala'),
(2, 4, 'Ciudad Valles'),
(2, 5, 'Rioverde')

-- Sinaloa
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(3, 1, 'Culiac�n'),
(3, 2, 'Mazatl�n'),
(3, 3, 'Ahome'),
(3, 4, 'Guasave'),
(3, 5, 'Navolato')

-- Sonora
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(4, 1, 'Hermosillo'),
(4, 2, 'Cajeme'),
(4, 3, 'Nogales'),
(4, 4, 'San Luis R�o Colorado'),
(4, 5, 'Guaymas')

-- Tabasco
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(5, 1, 'Centro'),
(5, 2, 'C�rdenas'),
(5, 3, 'Comalcalco'),
(5, 4, 'Macuspana'),
(5, 5, 'Jalapa')

-- Monterrey, Nuevo Le�n
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 1, 1, 'Monterrey Centro'),
(1, 1, 2, 'San Jer�nimo'),
(1, 1, 3, 'San Pedro Garza Garc�a')

-- Guadalupe, Nuevo Le�n
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 2, 1, 'Guadalupe Centro'),
(1, 2, 2, 'Cumbres'),
(1, 2, 3, 'Contry')

-- San Nicol�s de los Garza, Nuevo Le�n
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 3, 1, 'San Nicol�s Centro'),
(1, 3, 2, 'Las Puentes'),
(1, 3, 3, 'An�huac')

-- Apodaca, Nuevo Le�n
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 4, 1, 'Apodaca Centro'),
(1, 4, 2, 'Huinal�'),
(1, 4, 3, 'Ciudad Apodaca')

-- Escobedo, Nuevo Le�n
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 5, 1, 'General Escobedo Centro'),
(1, 5, 2, 'Linda Vista'),
(1, 5, 3, 'Colonia San Francisco')

-- San Luis Potos�, San Luis Potos�
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 1, 1, 'San Luis Potos� Centro'),
(2, 1, 2, 'Las Palmas'),
(2, 1, 3, 'Lomas del Pedregal')

-- Soledad de Graciano S�nchez, San Luis Potos�
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 2, 1, 'Soledad Centro'),
(2, 2, 2, 'La Loma'),
(2, 2, 3, 'San Felipe')

-- Matehuala, San Luis Potos�
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 3, 1, 'Matehuala Centro'),
(2, 3, 2, 'Las Palmas'),
(2, 3, 3, 'San Jos� de las Pilas')

-- Ciudad Fern�ndez, San Luis Potos�
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 4, 1, 'Ciudad Fern�ndez Centro'),
(2, 4, 2, 'Santa Rosa'),
(2, 4, 3, 'Valle de Guadalupe')

-- Rioverde, San Luis Potos�
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 5, 1, 'Rioverde Centro'),
(2, 5, 2, 'La Florida'),
(2, 5, 3, 'El Potosino')

-- Culiac�n, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 1, 1, 'Culiac�n Centro'),
(3, 1, 2, 'Las Quintas'),
(3, 1, 3, 'Tres R�os')

-- Mazatl�n, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 2, 1, 'Mazatl�n Centro'),
(3, 2, 2, 'Zona Dorada'),
(3, 2, 3, 'La Marina')

-- Ahome, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 3, 1, 'Los Mochis'),
(3, 3, 2, 'Topolobampo'),
(3, 3, 3, 'El Carrizo')

-- Guasave, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 4, 1, 'Guasave Centro'),
(3, 4, 2, 'Juan Jos� R�os'),
(3, 4, 3, 'Los �ngeles')

-- Navolato, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 5, 1, 'Navolato Centro'),
(3, 5, 2, 'Las Puentes'),
(3, 5, 3, 'El Potrero')

-- Hermosillo, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 1, 1, 'Hermosillo Centro'),
(4, 1, 2, 'Las Lomas'),
(4, 1, 3, 'Paseo del R�o')

-- Ciudad Obreg�n, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 2, 1, 'Ciudad Obreg�n Centro'),
(4, 2, 2, 'Las Torres'),
(4, 2, 3, 'Benito Ju�rez')

-- Nogales, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 3, 1, 'Nogales Centro'),
(4, 3, 2, 'Revoluci�n'),
(4, 3, 3, 'Los Alamos')

-- San Luis R�o Colorado, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 4, 1, 'San Luis R�o Colorado Centro'),
(4, 4, 2, 'Zona Norte'),
(4, 4, 3, 'Nuevo San Luis')

-- Guaymas, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 5, 1, 'Guaymas Centro'),
(4, 5, 2, 'Miramar'),
(4, 5, 3, 'San Carlos')

-- Centro, Tabasco
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(5, 1, 1, 'Villahermosa Centro'),
(5, 1, 2, 'Atasta'),
(5, 1, 3, 'La Manga')

-- C�rdenas, Tabasco
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(5, 2, 1, 'C�rdenas Centro'),
(5, 2, 2, 'Huimanguillo'),
(5, 2, 3, 'Tapijulapa')

-- Comalcalco, Tabasco
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(5, 3, 1, 'Comalcalco Centro'),
(5, 3, 2, 'La Venta'),
(5, 3, 3, 'Nacajuca')

-- Macuspana, Tabasco
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(5, 4, 1, 'Macuspana Centro'),
(5, 4, 2, 'La Chontalpa'),
(5, 4, 3, 'Benito Ju�rez')

-- Jalapa, Tabasco
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(5, 5, 1, 'Jalapa Centro'),
(5, 5, 2, 'Teapa'),
(5, 5, 3, 'Pichucalco')

-- BUSCAR CON ESTADO
SELECT M.Nombre FROM MUNICIPIOS M INNER JOIN ESTADOS E ON E.IdEstado = M.IdEstado WHERE E.Nombre = 'Sinaloa'

-- BUSCAR CON ESTADO Y MUNICIPIO
SELECT C.Nombre FROM CIUDADES C INNER JOIN MUNICIPIOS M ON M.IdMunicipio = C.IdMunicipio  INNER JOIN ESTADOS E ON E.IdEstado = C.IdEstado WHERE M.Nombre = 'Culiac�n' AND E.Nombre = 'Sinaloa'