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
(1, 'Nuevo León'),
(2, 'San Luis Potosí'),
(3, 'Sinaloa'),
(4, 'Sonora'),
(5, 'Tabasco')

INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(1, 1, 'Monterrey'),
(1, 2, 'Guadalupe'),
(1, 3, 'San Nicolás de los Garza'),
(1, 4, 'Apodaca'),
(1, 5, 'Escobedo')

-- San Luis Potosí
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(2, 1, 'San Luis Potosí'),
(2, 2, 'Soledad de Graciano Sánchez'),
(2, 3, 'Matehuala'),
(2, 4, 'Ciudad Valles'),
(2, 5, 'Rioverde')

-- Sinaloa
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(3, 1, 'Culiacán'),
(3, 2, 'Mazatlán'),
(3, 3, 'Ahome'),
(3, 4, 'Guasave'),
(3, 5, 'Navolato')

-- Sonora
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(4, 1, 'Hermosillo'),
(4, 2, 'Cajeme'),
(4, 3, 'Nogales'),
(4, 4, 'San Luis Río Colorado'),
(4, 5, 'Guaymas')

-- Tabasco
INSERT INTO Municipios (IdEstado, IdMunicipio, Nombre) VALUES
(5, 1, 'Centro'),
(5, 2, 'Cárdenas'),
(5, 3, 'Comalcalco'),
(5, 4, 'Macuspana'),
(5, 5, 'Jalapa')

-- Monterrey, Nuevo León
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 1, 1, 'Monterrey Centro'),
(1, 1, 2, 'San Jerónimo'),
(1, 1, 3, 'San Pedro Garza García')

-- Guadalupe, Nuevo León
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 2, 1, 'Guadalupe Centro'),
(1, 2, 2, 'Cumbres'),
(1, 2, 3, 'Contry')

-- San Nicolás de los Garza, Nuevo León
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 3, 1, 'San Nicolás Centro'),
(1, 3, 2, 'Las Puentes'),
(1, 3, 3, 'Anáhuac')

-- Apodaca, Nuevo León
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 4, 1, 'Apodaca Centro'),
(1, 4, 2, 'Huinalá'),
(1, 4, 3, 'Ciudad Apodaca')

-- Escobedo, Nuevo León
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(1, 5, 1, 'General Escobedo Centro'),
(1, 5, 2, 'Linda Vista'),
(1, 5, 3, 'Colonia San Francisco')

-- San Luis Potosí, San Luis Potosí
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 1, 1, 'San Luis Potosí Centro'),
(2, 1, 2, 'Las Palmas'),
(2, 1, 3, 'Lomas del Pedregal')

-- Soledad de Graciano Sánchez, San Luis Potosí
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 2, 1, 'Soledad Centro'),
(2, 2, 2, 'La Loma'),
(2, 2, 3, 'San Felipe')

-- Matehuala, San Luis Potosí
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 3, 1, 'Matehuala Centro'),
(2, 3, 2, 'Las Palmas'),
(2, 3, 3, 'San José de las Pilas')

-- Ciudad Fernández, San Luis Potosí
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 4, 1, 'Ciudad Fernández Centro'),
(2, 4, 2, 'Santa Rosa'),
(2, 4, 3, 'Valle de Guadalupe')

-- Rioverde, San Luis Potosí
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(2, 5, 1, 'Rioverde Centro'),
(2, 5, 2, 'La Florida'),
(2, 5, 3, 'El Potosino')

-- Culiacán, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 1, 1, 'Culiacán Centro'),
(3, 1, 2, 'Las Quintas'),
(3, 1, 3, 'Tres Ríos')

-- Mazatlán, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 2, 1, 'Mazatlán Centro'),
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
(3, 4, 2, 'Juan José Ríos'),
(3, 4, 3, 'Los Ángeles')

-- Navolato, Sinaloa
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(3, 5, 1, 'Navolato Centro'),
(3, 5, 2, 'Las Puentes'),
(3, 5, 3, 'El Potrero')

-- Hermosillo, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 1, 1, 'Hermosillo Centro'),
(4, 1, 2, 'Las Lomas'),
(4, 1, 3, 'Paseo del Río')

-- Ciudad Obregón, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 2, 1, 'Ciudad Obregón Centro'),
(4, 2, 2, 'Las Torres'),
(4, 2, 3, 'Benito Juárez')

-- Nogales, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 3, 1, 'Nogales Centro'),
(4, 3, 2, 'Revolución'),
(4, 3, 3, 'Los Alamos')

-- San Luis Río Colorado, Sonora
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(4, 4, 1, 'San Luis Río Colorado Centro'),
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

-- Cárdenas, Tabasco
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(5, 2, 1, 'Cárdenas Centro'),
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
(5, 4, 3, 'Benito Juárez')

-- Jalapa, Tabasco
INSERT INTO Ciudades (IdEstado, IdMunicipio, IdCiudad, Nombre) VALUES
(5, 5, 1, 'Jalapa Centro'),
(5, 5, 2, 'Teapa'),
(5, 5, 3, 'Pichucalco')

-- BUSCAR CON ESTADO
SELECT M.Nombre FROM MUNICIPIOS M INNER JOIN ESTADOS E ON E.IdEstado = M.IdEstado WHERE E.Nombre = 'Sinaloa'

-- BUSCAR CON ESTADO Y MUNICIPIO
SELECT C.Nombre FROM CIUDADES C INNER JOIN MUNICIPIOS M ON M.IdMunicipio = C.IdMunicipio  INNER JOIN ESTADOS E ON E.IdEstado = C.IdEstado WHERE M.Nombre = 'Culiacán' AND E.Nombre = 'Sinaloa'