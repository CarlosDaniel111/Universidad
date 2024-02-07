USE Northwind
GO

-- 1.- PROCEDIMIENTO ALMACENADO QUE RECIBA DOS CLAVES DE EMPLEADOS Y REGRESE POR PARAMETRO DE SALIDA EL
-- EMPLEADO QUE MAS HA VENDIDO DE LOS DOS Y EL IMPORTE DE VENTAS

CREATE PROC SP_PROBLEMA1 @ClaveEmpleado1 INT, @ClaveEmpleado2 INT, @ClaveMasVendedor INT OUTPUT, @Importe NUMERIC(12, 3) OUTPUT AS
BEGIN
	DECLARE @ImporteEmpleado1 NUMERIC(12, 3), @ImporteEmpleado2 NUMERIC(12, 3)

	SELECT @ImporteEmpleado1 = SUM(OD.Quantity * OD.UnitPrice)
	FROM Orders O
	INNER JOIN [Order Details] OD ON OD.OrderID = O.OrderID
	WHERE O.EmployeeID = @ClaveEmpleado1

	SELECT @ImporteEmpleado2 = SUM(OD.Quantity * OD.UnitPrice)
	FROM Orders O
	INNER JOIN [Order Details] OD ON OD.OrderID = O.OrderID
	WHERE O.EmployeeID = @ClaveEmpleado2

	IF @ImporteEmpleado1 >= @ImporteEmpleado2
		BEGIN
			SELECT @ClaveMasVendedor = @ClaveEmpleado1
			SELECT @Importe = @ImporteEmpleado1
		END
	ELSE
		BEGIN
			SELECT @ClaveMasVendedor = @ClaveEmpleado2
			SELECT @Importe = @ImporteEmpleado2
		END
END
GO

DECLARE @ClaveMasVendedor INT, @Importe NUMERIC(12, 3)
EXEC SP_PROBLEMA1 1, 2, @ClaveMasVendedor OUTPUT, @Importe OUTPUT
SELECT EmpleadoMasVendedor = @ClaveMasVendedor, ImporteVentas = @Importe
GO

-- 2.- PROCEDIMIENTO ALMACENADO QUE RECIBA LA CLAVE DE LA CATEGORIA Y REGRESE POR RETORNO EL TOTAL DE PIEZAS
-- VENDIDAS DE DICHA CATEGORIA

CREATE PROC SP_PROBLEMA2 @ClaveCategoria INT AS
BEGIN
	DECLARE @TotalPiezas INT
	SELECT @TotalPiezas = SUM(OD.Quantity)
	FROM Products P
	INNER JOIN [Order Details] OD ON OD.ProductID = P.ProductID
	WHERE P.CategoryID = @ClaveCategoria
	RETURN @TotalPiezas
END
GO

DECLARE @TotalPiezas INT
EXEC @TotalPiezas = SP_PROBLEMA2 1
SELECT [Total de piezas] = @TotalPiezas
GO

-- 3.- FUNCION ESCALAR QUE RECIBA UN NUMERO ENTERO Y REGRESE LA SUMATORIA DE DICHO NUMERO DE 1 HASTA ESE
-- NUMERO. EJEMPLO: X = 4, RESULTADO = 1+2+3+4. (Recursivo)

CREATE FUNCTION dbo.FN_PROBLEMA3(@X INT)
RETURNS INT
AS
BEGIN
	IF @X = 1
		RETURN 1
	RETURN @X + dbo.FN_PROBLEMA3(@X - 1)
END
GO

SELECT dbo.FN_PROBLEMA3(5)
GO

-- 4.- FUNCION DE TABLA EN LINEA QUE RECIBA LA CLAVE DE JEFE Y REGRESE EL NOMBRE DEL JEFE ENVIADO, EL NOMBRE DE
-- LOS EMPLEADOS A SU CARGO Y LA DIFERENCIA DE EDAD ENTRE ELLOS.

CREATE FUNCTION dbo.FN_PROBLEMA4(@ClaveJefe INT)
RETURNS TABLE
AS
RETURN(
	SELECT Jefe = J.FirstName + ' ' + J.LastName, [Empleado a cargo] = E.FirstName + ' ' + E.LastName, [Diferencia de edad] =  TRIM(STR(ABS(DATEDIFF(YY, J.BirthDate, E.BirthDate)))) + ' años'
	FROM Employees E
	INNER JOIN Employees J ON J.EmployeeID = E.ReportsTo
	WHERE J.EmployeeID = @ClaveJefe
)
GO

SELECT * FROM dbo.FN_PROBLEMA4(2)
GO

-- 5.- FUNCION DE TABLA DE MULTISENTENCIA QUE RECIBA LA CLAVE DEL EMPLEADO, QUE REGRESE UNA TABLA CON LA
-- FECHA Y LOS FOLIOS DE LAS ORDENES QUE SE REALIZARON ESA FECHA DEL EMPLEADO QUE SE ENVIO COMO PARAMETRO

CREATE FUNCTION dbo.FN_PROBLEMA5(@ClaveEmpleado INT)
RETURNS @T TABLE(Fecha DATETIME, Folios NVARCHAR(MAX))
AS
BEGIN
	DECLARE @FechaOrden DATETIME, @ClaveOrden INT, @ListadoFolios NVARCHAR(MAX)
	SELECT @FechaOrden = MIN(OrderDate) FROM Orders WHERE EmployeeID = @ClaveEmpleado

	WHILE @FechaOrden IS NOT NULL
	BEGIN
		SELECT @ListadoFolios = ''
		SELECT @ClaveOrden = MIN(OrderID) FROM Orders WHERE EmployeeID = @ClaveEmpleado AND OrderDate = @FechaOrden
		
		WHILE @ClaveOrden IS NOT NULL
		BEGIN
			SELECT @ListadoFolios = @ListadoFolios + TRIM(STR(@ClaveOrden)) + ', '
			SELECT @ClaveOrden = MIN(OrderID) FROM Orders WHERE EmployeeID = @ClaveEmpleado AND OrderDate = @FechaOrden AND OrderID > @ClaveOrden
		END

		INSERT @T VALUES(@FechaOrden, @ListadoFolios)
		SELECT @FechaOrden = MIN(OrderDate) FROM Orders WHERE EmployeeID = @ClaveEmpleado AND OrderDate > @FechaOrden
	END

	RETURN
END
GO

SELECT * FROM dbo.FN_PROBLEMA5(2)