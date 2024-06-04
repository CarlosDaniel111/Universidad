CREATE DATABASE EmpresaDW
GO
USE EmpresaDW
GO
create schema Dimension
GO
SET LANGUAGE spanish
GO
-- TABLA DIMENSION FECHA
CREATE TABLE Dimension.Fecha(
   FechaID INT PRIMARY KEY,
   Fecha DATETIME NOT NULL,
   Anio INT NOT NULL,
   Mes NVARCHAR(15) NOT NULL,
   Dia NVARCHAR(15) NOT NULL,
   Semestre NVARCHAR(30) NOT NULL,
   Cuatrimestre NVARCHAR(30) NOT NULL,
   Trimestre NVARCHAR(30) NOT NULL,
   Bimestre NVARCHAR(30) NOT NULL,
   Estacion NVARCHAR(30) NOT NULL,
   SemanaDelAnio NVARCHAR(30) NOT NULL,
   NombreDiaSemana NVARCHAR(15) NOT NULL,
   DiaDelAnio NVARCHAR(15) NOT NULL
)

GO
-- Procedimiento Almacenado para llenar Fecha
alter PROCEDURE InsertarFecha @FechaActual datetime
AS
INSERT INTO Dimension.Fecha(
	FechaID,Fecha,Anio,Mes,Dia,Semestre,Cuatrimestre,Trimestre,Bimestre,Estacion,SemanaDelAnio,NombreDiaSemana,DiaDelAnio
)VALUES (
	(DATEPART(year , @FechaActual) * 10000) + (DATEPART(month , @FechaActual)*100)
       + DATEPART(day , @FechaActual),
	   @FechaActual,
	   YEAR(@FechaActual),
	   DATENAME(MM,@FechaActual),
	   'Dia ' + STR(DATEPART(DD,@FechaActual),2),
	   CASE WHEN MONTH(@FechaActual) <= 6 THEN 'Semestre Ene-Jun' ELSE 'Semestre Jul-Dic' END,
	   CASE WHEN MONTH(@FechaActual) BETWEEN 1 AND 4 THEN 'Cuatrimestre Ene-Abr'
            WHEN MONTH(@FechaActual) BETWEEN 5 AND 8 THEN 'Cuatrimestre May-Ago'
            WHEN MONTH(@FechaActual) BETWEEN 9 AND 12 THEN 'Cuatrimestre Sep-Dic' END,
       CASE WHEN MONTH(@FechaActual) BETWEEN 1 AND 3 THEN 'Trimestre Ene-Mar'
            WHEN MONTH(@FechaActual) BETWEEN 4 AND 6 THEN 'Trimestre Abr-Jun'
            WHEN MONTH(@FechaActual) BETWEEN 7 AND 9 THEN 'Trimestre Jul-Sep'
            WHEN MONTH(@FechaActual) BETWEEN 10 AND 12 THEN 'Trimestre Oct-Dic' END,
       CASE WHEN MONTH(@FechaActual) BETWEEN 1 AND 2 THEN 'Bimestre Ene-Feb'
            WHEN MONTH(@FechaActual) BETWEEN 3 AND 4 THEN 'Bimestre Mar-Abr'
            WHEN MONTH(@FechaActual) BETWEEN 5 AND 6 THEN 'Bimestre May-Jun'
            WHEN MONTH(@FechaActual) BETWEEN 7 AND 8 THEN 'Bimestre Jul-Ago'
            WHEN MONTH(@FechaActual) BETWEEN 9 AND 10 THEN 'Bimestre Sep-Oct'
            WHEN MONTH(@FechaActual) BETWEEN 11 AND 12 THEN 'Bimestre Nov-Dic' END,
       CASE WHEN @FechaActual >= STR(YEAR(@FechaActual))+'-03-21' AND @FechaActual < STR(YEAR(@FechaActual))+'-06-21' THEN 'Primavera'
			WHEN @FechaActual >= STR(YEAR(@FechaActual))+'-06-21' AND @FechaActual < STR(YEAR(@FechaActual))+'-09-23' THEN 'Verano'
			WHEN @FechaActual >= STR(YEAR(@FechaActual))+'-09-23' AND @FechaActual < STR(YEAR(@FechaActual))+'-12-21' THEN 'Otono'
			ELSE 'Invierno' END,
       'Semana ' + STR(DATEPART(WEEK, @FechaActual),2), /* Semana del Año */
       DATENAME(WEEKDAY, @FechaActual), /* Nombre del Día de la Semana */
       'Dia ' + STR(DATEPART(DAYOFYEAR, @FechaActual),3) /* Día del Año */
)
delete from Dimension.Fecha
GO
-- BUCLE DE CARGAS DE FECHAS
set dateformat 'YMD'
declare @StartDate datetime, @EndDate datetime
set @StartDate = '20150101'
set @EndDate = '20300101'
while @StartDate <= @EndDate begin
   exec InsertarFecha @StartDate
   set @StartDate = dateadd(day,1,@StartDate)
end
GO
SELECT * FROM Dimension.Fecha
GO

-- TABLA DIMENSION PAIS
CREATE TABLE Dimension.Pais(
	Nombre NVARCHAR(30) PRIMARY KEY,
	Continente NVARCHAR(20) NOT NULL,
	Region NVARCHAR(30) NOT NULL,
	Idioma NVARCHAR(25) NOT NULL,
	Moneda NVARCHAR(25) NOT NULL,
	Poblacion NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	Area NVARCHAR(10) NOT NULL, --Grande,Mediano,Chico
	PIB NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	Gobierno NVARCHAR(25) NOT NULL,
	Religion NVARCHAR(25) NOT NULL
)
GO
-- INSERTAR DATOS
INSERT INTO Dimension.Pais (Nombre,Continente,Region,Idioma,Moneda,Poblacion,Area,PIB,Gobierno,Religion)
VALUES ('Japon','Asia','Este de Asia', 'Japonés', 'Yen japonés','Alto', 'Mediano', 'Alto', 'Monarquia constitucional','Sintoismo'),
('Espana', 'Europa', 'Suroeste de Europa', 'Espanol', 'Euro','Bajo', 'Mediano', 'Bajo', 'Monarquia parlamentaria','Catolica'),
('Italia', 'Europa', 'Sur de Europa', 'Italiano', 'Euro','Medio', 'Chico', 'Medio', 'Republica parlamentaria','Catolica'),
('Alemania', 'Europa', 'Centro de Europa', 'Aleman', 'Euro', 'Medio', 'Chico', 'Medio', 'Republica parlamentaria','Catolica'),
('USA', 'America', 'Norte de America', 'Ingles', 'Dolar estadounidense', 'Alto', 'Grande', 'Alto', 'Republica federal','Cristianismo'),
('Corea del Sur', 'Asia', 'Este de Asia', 'Coreano', 'Won surcoreano', 'Bajo', 'Chico', 'Bajo', 'Republica presidencial','Cristianismo'),
('China', 'Asia', 'Este de Asia', 'Chino mandarin', 'Yuan', 'Alto', 'Grande', 'Alto', 'Republica socialista','Budismo')

SELECT * FROM Dimension.Pais

GO
-- TABLA DIMENSION MEDIO DE TRANSPORTE
CREATE TABLE Dimension.Transporte(
	Nombre NVARCHAR(20) PRIMARY KEY,
	VelocidadPromedio NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	CapacidadCarga NVARCHAR(10) NOT NULL, --Grande,Mediano,Chico
	TipoCombustible NVARCHAR(15) NOT NULL,
	RangoAlcance NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	CostoPromedio NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	EficienciaEnergetica NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	EmisionCarbono NVARCHAR(10) NOT NULL --Alto,Medio,Bajo
)
GO
INSERT INTO Dimension.Transporte (Nombre, VelocidadPromedio, CapacidadCarga,TipoCombustible, RangoAlcance, CostoPromedio, EficienciaEnergetica, EmisionCarbono)
VALUES ('Carretera', 'Medio', 'Chico','Gasolina', 'Bajo', 'Bajo', 'Medio', 'Bajo'),
('Rieles', 'Medio', 'Mediano','Electricidad', 'Bajo', 'Bajo', 'Bajo', 'Bajo'),
('Cielo', 'Alto', 'Mediano','Queroseno', 'Medio', 'Alto', 'Alto', 'Alto'),
('Mar', 'Bajo', 'Grande','Diesel', 'Alto', 'Medio', 'Bajo', 'Medio')

SELECT * FROM Dimension.Transporte
GO
-- Vista Materializada IMPORTACIONES
ALTER TABLE Importaciones ADD PRIMARY KEY(PaisOrigen,MedioTransporte,Fecha)

ALTER TABLE Importaciones ADD
CONSTRAINT FK_Importaciones_Pais FOREIGN KEY (PaisOrigen) REFERENCES Dimension.Pais (Nombre),
CONSTRAINT FK_Importaciones_Transporte FOREIGN KEY (MedioTransporte) REFERENCES Dimension.Transporte (Nombre),
CONSTRAINT FK_Importaciones_Fecha FOREIGN KEY (Fecha) REFERENCES Dimension.Fecha (FechaID)

alter table importaciones drop
CONSTRAINT FK_Importaciones_Pais,
CONSTRAINT FK_Importaciones_Transporte  ,
CONSTRAINT FK_Importaciones_Fecha 

-- VENTAS
-- TABLA DIMENSION EMPLEADO
CREATE TABLE Dimension.Empleado(
	EmpleadoID INT PRIMARY KEY,
	Nombre NVARCHAR(50) NOT NULL,
	Departamento NVARCHAR(50) NOT NULL,
	Cargo NVARCHAR(30) NOT NULL,
	NivelEducativo NVARCHAR(30) NOT NULL,
	Genero NVARCHAR(20) NOT NULL,
	EstadoCivil NVARCHAR(20) NOT NULL,
	TipoContrato NVARCHAR(40) NOT NULL,
	EstatusLaboral NVARCHAR(30) NOT NULL,
	Nacionalidad NVARCHAR(30) NOT NULL,
	TipoJornadaLaboral NVARCHAR(50) NOT NULL
)
GO
CREATE PROCEDURE LlenarTablaEmpleados
AS
BEGIN
    DECLARE @contador INT = 1;
    
    WHILE @contador <= 200
    BEGIN
        INSERT INTO Dimension.Empleado (EmpleadoID, Nombre, Departamento, Cargo, NivelEducativo, Genero, EstadoCivil, TipoContrato, EstatusLaboral, Nacionalidad, TipoJornadaLaboral)
        VALUES (
            @contador,
            'Empleado ' + CAST(@contador AS NVARCHAR(10)), -- Nombre aleatorio
            'Departamento ' + CAST((ABS(CHECKSUM(NEWID())) % 10) + 1 AS NVARCHAR(10)), -- Departamento aleatorio
            'Cargo ' + CAST((ABS(CHECKSUM(NEWID())) % 5) + 1 AS NVARCHAR(10)), -- Cargo aleatorio
            CASE ABS(CHECKSUM(NEWID())) % 4
                WHEN 0 THEN 'Bachillerato'
                WHEN 1 THEN 'Licenciatura'
                WHEN 2 THEN 'Maestría'
                ELSE 'Doctorado'
            END,
            CASE ABS(CHECKSUM(NEWID())) % 2
                WHEN 0 THEN 'Masculino'
                ELSE 'Femenino'
            END,
            CASE ABS(CHECKSUM(NEWID())) % 3
                WHEN 0 THEN 'Soltero'
                WHEN 1 THEN 'Casado'
                ELSE 'Divorciado'
            END,
            CASE ABS(CHECKSUM(NEWID())) % 3
                WHEN 0 THEN 'Indefinido'
                WHEN 1 THEN 'Temporal'
                ELSE 'Por proyecto'
            END,
            CASE ABS(CHECKSUM(NEWID())) % 2
                WHEN 0 THEN 'Activo'
                ELSE 'Inactivo'
            END,
			CASE ABS(CHECKSUM(NEWID())) % 5
                WHEN 0 THEN 'Mexicano'
                WHEN 1 THEN 'Estadounidense'
                WHEN 2 THEN 'Argentino'
                WHEN 3 THEN 'Colombiano'
				ELSE 'Chileno'
            END,
            CASE ABS(CHECKSUM(NEWID())) % 4
                WHEN 0 THEN 'Tiempo completo'
                WHEN 1 THEN 'Medio tiempo'
                WHEN 2 THEN 'Por horas'
                ELSE 'Por turnos'
            END
        )
        
        SET @contador = @contador + 1;
    END
END

exec LlenarTablaEmpleados

GO
select * from Dimension.Empleado
GO

-- TABLA DIMENSION ESTADO
CREATE TABLE Dimension.Estado(
	EstadoID INT PRIMARY KEY,
	Nombre NVARCHAR(30) NOT NULL,
	Capital NVARCHAR(30) NOT NULL,
	Poblacion NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	Area NVARCHAR(10) NOT NULL,  --Grande,Mediano,Chico
	PIB NVARCHAR(10) NOT NULL, -- Alto,Medio,Bajo
	NumMunicipios NVARCHAR(10) NOT NULL, --Alto,Medio,Bajo
	Region NVARCHAR(30) NOT NULL,
	Clima NVARCHAR(30) NOT NULL,
)
GO
INSERT INTO Dimension.Estado (EstadoID, Nombre, Capital, Poblacion, Area, PIB, NumMunicipios, Region, Clima)
VALUES (1, 'Aguascalientes', 'Aguascalientes', 'Medio', 'Mediano', 'Medio', 'Medio', 'Centro', 'Semiseco'),
(2, 'Baja California', 'Mexicali', 'Alto', 'Grande', 'Alto', 'Medio', 'Noroeste', 'Seco'),
(3, 'Baja California Sur', 'La Paz', 'Medio', 'Grande', 'Medio', 'Medio', 'Noroeste', 'Seco'),
(4, 'Campeche', 'Campeche', 'Medio', 'Grande', 'Bajo', 'Medio', 'Sureste', 'Calido subhumedo'),
(5, 'Coahuila', 'Saltillo', 'Medio', 'Grande', 'Medio', 'Alto', 'Noreste', 'Semiseco'),
(6, 'Colima', 'Colima', 'Medio', 'Chico', 'Medio', 'Medio', 'Centro', 'Calido subhumedo'),
(7, 'Chiapas', 'Tuxtla Gutiérrez', 'Alto', 'Grande', 'Bajo', 'Alto', 'Sureste', 'Calido subhumedo'),
(8, 'Chihuahua', 'Chihuahua', 'Alto', 'Grande', 'Alto', 'Medio', 'Noroeste', 'Seco'),
(9, 'Ciudad de México', 'Ciudad de México', 'Alto', 'Chico', 'Alto', 'Medio', 'Centro', 'Templado subhumedo'),
(10, 'Durango', 'Durango', 'Medio', 'Grande', 'Medio', 'Medio', 'Noreste', 'Semiseco'),
(11, 'Guanajuato', 'Guanajuato', 'Alto', 'Grande', 'Alto', 'Alto', 'Centro', 'Templado subhumedo'),
(12, 'Guerrero', 'Chilpancingo', 'Alto', 'Mediano', 'Medio', 'Alto', 'Sur', 'Calido subhumedo'),
(13, 'Hidalgo', 'Pachuca', 'Alto', 'Mediano', 'Medio', 'Alto', 'Centro', 'Semiseco'),
(14, 'Jalisco', 'Guadalajara', 'Alto', 'Grande', 'Alto', 'Alto', 'Centro', 'Semiseco'),
(15, 'Estado de México', 'Toluca', 'Alto', 'Grande', 'Alto', 'Alto', 'Centro', 'Templado subhumedo'),
(16, 'Michoacán', 'Morelia', 'Alto', 'Grande', 'Alto', 'Alto', 'Centro', 'Templado subhumedo'),
(17, 'Morelos', 'Cuernavaca', 'Medio', 'Chico', 'Alto', 'Medio', 'Centro', 'Calido subhumedo'),
(18, 'Nayarit', 'Tepic', 'Medio', 'Mediano', 'Medio', 'Medio', 'Noroeste', 'Semiseco'),
(19, 'Nuevo León', 'Monterrey', 'Alto', 'Grande', 'Alto', 'Alto', 'Noreste', 'Semiseco'),
(20, 'Oaxaca', 'Oaxaca', 'Alto', 'Grande', 'Bajo', 'Alto', 'Sur', 'Calido subhumedo'),
(21, 'Puebla', 'Puebla', 'Alto', 'Grande', 'Alto', 'Alto', 'Centro', 'Templado subhumedo'),
(22, 'Querétaro', 'Querétaro', 'Medio', 'Mediano', 'Alto', 'Medio', 'Centro', 'Semiseco'),
(23, 'Quintana Roo', 'Chetumal', 'Medio', 'Grande', 'Medio', 'Medio', 'Sureste', 'Calido subhumedo'),
(24, 'San Luis Potosí', 'San Luis Potosí', 'Medio', 'Grande', 'Medio', 'Medio', 'Centro', 'Semiseco'),
(25, 'Sinaloa', 'Culiacán', 'Alto', 'Grande', 'Medio', 'Alto', 'Noroeste', 'Seco'),
(26, 'Sonora', 'Hermosillo', 'Alto', 'Grande', 'Alto', 'Alto', 'Noroeste', 'Seco'),
(27, 'Tabasco', 'Villahermosa', 'Medio', 'Grande', 'Bajo', 'Alto', 'Sureste', 'Calido humedo'),
(28, 'Tamaulipas', 'Ciudad Victoria', 'Medio', 'Grande', 'Alto', 'Alto', 'Noreste', 'Semiseco'),
(29, 'Tlaxcala', 'Tlaxcala', 'Bajo', 'Chico', 'Medio', 'Medio', 'Centro', 'Templado subhumedo'),
(30, 'Veracruz', 'Xalapa', 'Alto', 'Grande', 'Alto', 'Alto', 'Este', 'Calido humedo'),
(31, 'Yucatán', 'Mérida', 'Medio', 'Grande', 'Medio', 'Medio', 'Sureste', 'Calido subhumedo'),
(32, 'Zacatecas', 'Zacatecas', 'Medio', 'Grande', 'Medio', 'Medio', 'Noreste', 'Semiseco')


SELECT * FROM Dimension.Estado


-- Vista Materializada Ventas
ALTER TABLE Ventas ADD PRIMARY KEY (EstadoID,EmpleadoID,Fecha)

ALTER TABLE Ventas ADD
CONSTRAINT FK_Ventas_Estado FOREIGN KEY (EstadoID) REFERENCES Dimension.Estado (EstadoID),
CONSTRAINT FK_Ventas_Empleado FOREIGN KEY (EmpleadoID) REFERENCES Dimension.Empleado (EmpleadoID),
CONSTRAINT FK_Ventas_Fecha FOREIGN KEY (Fecha) REFERENCES Dimension.Fecha (FechaID)

alter table ventas drop
CONSTRAINT FK_Ventas_Estado,
CONSTRAINT FK_Ventas_Empleado,
CONSTRAINT FK_Ventas_Fecha

SELECT * FROM VENTAS


