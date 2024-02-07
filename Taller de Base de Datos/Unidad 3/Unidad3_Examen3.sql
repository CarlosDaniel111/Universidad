USE Northwind
GO

/*
1.- PROCEDIMIENTO ALMACENADO QUE RECIBA LA CLAVE DE UNA CATEGORIA, EL PROCEDIMIENTO DEBE FORZAR LA
ELIMINACION DE LA CATEGORIA, AUNQUE TENGA HIJOS EN LAS TABLAS PRODUCTS U ORDERDETAILS
*/

CREATE PROC SP_PROBLEMA1 @ClaveCategoria INT
AS
BEGIN
	SELECT * FROM Categories WHERE CategoryID = @ClaveCategoria
	
	DECLARE @ClaveProducto INT
	SELECT @ClaveProducto = MIN(ProductID) FROM Products WHERE CategoryID = @ClaveCategoria

	WHILE @ClaveProducto IS NOT NULL
	BEGIN
		DELETE FROM [Order Details] WHERE ProductID = @ClaveProducto
		SELECT @ClaveProducto = MIN(ProductID) FROM Products WHERE CategoryID = @ClaveCategoria AND ProductID > @ClaveProducto
	END

	DELETE FROM Products WHERE CategoryID = @ClaveCategoria
	DELETE FROM Categories WHERE CategoryID = @ClaveCategoria

	SELECT * FROM Categories WHERE CategoryID = @ClaveCategoria
END
GO

EXEC SP_PROBLEMA1 2

SELECT * FROM Categories

/*
2.- LA TABLA CLIENTES SE LE AGREGO EL CAMPO TIPOCLIENTE, MEDIANTE UN PROCEDIMIENTO ALMACENADO
ACTUALIZAR DICHO CAMPO DE ACUERDO A LAS SIGUIENTES CONDICIONES:
	- SI TIENE DE 1 A 50 ORDENES: CLIENTE NORMAL
	- SI TIENE DE 51 A 150 ORDENES: CLIENTE BUENO
	- MAS DE 150 ORDENES: CLIENTE EXCELENTE
*/

ALTER TABLE Customers ADD TipoCliente NVARCHAR(20)
GO

CREATE PROC SP_PROBLEMA2
AS
BEGIN
	CREATE TABLE #T (ClaveCliente NCHAR(5), TotalOrdenes INT)
	INSERT #T
	SELECT C.CustomerID, COUNT(O.OrderID)
	FROM Customers C
	INNER JOIN Orders O ON O.CustomerID = C.CustomerID
	GROUP BY C.CustomerID

	DECLARE @ClaveCliente NCHAR(5), @TipoCliente NVARCHAR(20), @TotalOrdenes INT
	SELECT @ClaveCliente = MIN(ClaveCliente) FROM #T

	WHILE @ClaveCliente IS NOT NULL
	BEGIN
		SELECT @TotalOrdenes = TotalOrdenes FROM #T WHERE ClaveCliente = @ClaveCliente
		IF @TotalOrdenes BETWEEN 1 AND 50
			SELECT @TipoCliente = 'CLIENTE NORMAL'
		ELSE IF @TotalOrdenes BETWEEN 51 AND 150
			SELECT @TipoCliente = 'CLIENTE BUENO'
		ELSE IF @TotalOrdenes > 150
			SELECT @TipoCliente = 'CLIENTE EXCELENTE'
		UPDATE Customers SET TipoCliente = @TipoCliente WHERE CustomerID = @ClaveCliente
		SELECT @ClaveCliente = MIN(ClaveCliente) FROM #T WHERE ClaveCliente > @ClaveCliente
	END
END
GO

SELECT * FROM Customers
EXEC SP_PROBLEMA2
SELECT * FROM Customers
GO

/*
3.- FUNCION ESCALAR QUE RECIBA LA CLAVE DEL EMPLEADO Y REGRESE COMO PARAMETRO DE SALIDA EL LISTADO DE
LOS AÑOS BISIESTOS VIVIDOS
*/
-- AÑO % 4 = 0 AND (AÑO % 100 != 0 OR AÑO % 400 = 0)

CREATE FUNCTION dbo.FN_PROBLEMA3(@ClaveEmpleado INT)
RETURNS NVARCHAR(MAX)
AS
BEGIN
	DECLARE @Listado NVARCHAR(MAX), @Fecha DATETIME, @Año INT
	SELECT @Listado = ''
	SELECT @Fecha = BirthDate FROM Employees WHERE EmployeeID = @ClaveEmpleado

	WHILE @Fecha <= GETDATE()
	BEGIN
		SELECT @Año = YEAR(@Fecha)
		IF @Año % 4 = 0 AND (@Año % 100 != 0 OR @Año % 400 = 0)
			SELECT @Listado = @Listado + TRIM(STR(@Año)) + ', '
		SELECT @Fecha = DATEADD(YY, 1, @Fecha)
	END

	RETURN @Listado
END
GO

SELECT [Listado de años bisiestos] =  dbo.FN_PROBLEMA3(1)
GO

/*
4.- FUNCION DE TABLA EN LINEA QUE RECIBA LA CLAVE DEL EMPLEADO Y REGRESE EN UNA CONSULTA LOS NOMBRES DE
LOS TERRITORIOS Y EN OTRA COLUMNA NOMBRE DE LAS REGIONES QUE ATIENDE
*/

CREATE FUNCTION dbo.FN_PROBLEMA4(@ClaveEmpleado INT)
RETURNS TABLE
AS
RETURN(
	SELECT [Nombre del territorio] = T.TerritoryDescription, [Nombre de la region] = RegionDescription
	FROM EmployeeTerritories ET
	INNER JOIN Territories T ON T.TerritoryID = ET.TerritoryID
	INNER JOIN Region R ON R.RegionID = T.RegionID
	WHERE ET.EmployeeID = @ClaveEmpleado
)
GO

SELECT * FROM dbo.FN_PROBLEMA4(2)
GO

/*
5.- FUNCION DE TABLA DE MULTISENTENCIA QUE RECIBA UN AÑO, QUE REGRESE UNA TABLA CON NOMBRE DEL
PRODUCTO QUE SE VENDIERON ESE AÑO Y EN OTRA COLUMNA, TODOS LOS FOLIO DE ORDENES QUE SE HAN
VENDIDO LOS PRODUCTOS
*/

CREATE FUNCTION dbo.FN_PROBLEMA5(@Año INT)
RETURNS @RESP TABLE(NombreProducto NVARCHAR(50), Folios NVARCHAR(MAX))
AS
BEGIN
	DECLARE @T TABLE(ClaveProducto INT)
	INSERT @T
	SELECT P.ProductID
	FROM Orders O
	INNER JOIN [Order Details] OD ON OD.OrderID = O.OrderID
	INNER JOIN Products P ON P.ProductID = OD.ProductID
	WHERE YEAR(O.OrderDate) = 1996
	GROUP BY P.ProductID

	DECLARE @ClaveProducto INT, @ClaveOrden INT, @ListadoFolios NVARCHAR(MAX), @NombreProducto NVARCHAR(100)
	SELECT @ClaveProducto = MIN(ClaveProducto) FROM @T

	WHILE @ClaveProducto IS NOT NULL
	BEGIN
		SELECT @ListadoFolios = ''
		SELECT @ClaveOrden = MIN(O.OrderID)
							 FROM Orders O
							 INNER JOIN [Order Details] OD ON OD.OrderID = O.OrderID
							 WHERE OD.ProductID = @ClaveProducto AND YEAR(O.OrderDate) = @Año

		WHILE @ClaveOrden IS NOT NULL
		BEGIN
			SELECT @ListadoFolios = @ListadoFolios + TRIM(STR(@ClaveOrden)) + ', '
			SELECT @ClaveOrden = MIN(O.OrderID)
							 FROM Orders O
							 INNER JOIN [Order Details] OD ON OD.OrderID = O.OrderID
							 WHERE OD.ProductID = @ClaveProducto AND YEAR(O.OrderDate) = @Año AND O.OrderID > @ClaveOrden
		END

		SELECT @NombreProducto = ProductName FROM Products WHERE ProductID = @ClaveProducto
		INSERT @RESP VALUES(@NombreProducto, @ListadoFolios)

		SELECT @ClaveProducto = MIN(ClaveProducto) FROM @T WHERE ClaveProducto > @ClaveProducto
	END

	RETURN
END
GO

SELECT * FROM dbo.FN_PROBLEMA5(1996)