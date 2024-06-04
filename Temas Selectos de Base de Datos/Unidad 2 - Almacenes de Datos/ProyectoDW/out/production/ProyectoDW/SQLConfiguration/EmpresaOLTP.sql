CREATE DATABASE EmpresaOLTP

USE EmpresaOLTP

-- CREAR TABLA IMPORTACIONES
CREATE TABLE Importaciones(
	ID INT IDENTITY(1,1) PRIMARY KEY,
	PaisOrigen NVARCHAR(30) NOT NULL,
	MedioTransporte NVARCHAR(20) NOT NULL,
	Fecha INT NOT NULL,
	Importe DECIMAL(12,2) NOT NULL
)

GO
--CREAR VISTA MATERIALIZADA Importaciones
CREATE VIEW VistaImportaciones AS
SELECT PaisOrigen,MedioTransporte,Fecha,Importe=SUM(Importe),Unidades=COUNT(*) FROM Importaciones
GROUP BY PaisOrigen,MedioTransporte,Fecha
GO
SELECT * INTO EmpresaDW.DBO.Importaciones FROM EmpresaOLTP.DBO.VistaImportaciones

-- CREAR TABLA VENTAS
CREATE TABLE TICKETSH(
TICKET VARCHAR(8) PRIMARY KEY NOT NULL,
FECHA INT NOT NULL,
IDESTADO INT NOT NULL,
IDCIUDAD INT NOT NULL,
IDTIENDA INT NOT NULL,
IDEMPLEADO INT NOT NULL
)
GO
CREATE TABLE TICKETSD(
TICKET VARCHAR(8) NOT NULL,
IDPRODUCTO INT NOT NULL,
UNIDADES INT NOT NULL,
PRECIO INT NOT NULL
PRIMARY KEY (TICKET,IDPRODUCTO),
FOREIGN KEY (TICKET) REFERENCES TICKETSH(TICKET)
)
GO

-- CREAR VISTA MATERIALIZADA Ventas
CREATE VIEW VistaVentas AS
SELECT EstadoID = TH.IDEstado,EmpleadoID = TH.IDEmpleado,TH.Fecha, Importe = SUM(TD.PRECIO), Unidades = SUM(TD.UNIDADES), MayorUnidadesVendidas = MAX(TD.UNIDADES)
FROM TICKETSD TD
INNER JOIN TICKETSH TH ON TD.TICKET = TH.TICKET
GROUP BY TH.IDESTADO, TH.IDEMPLEADO,TH.FECHA
GO
SELECT * INTO EmpresaDW.DBO.Ventas FROM EmpresaOLTP.DBO.VistaVentas