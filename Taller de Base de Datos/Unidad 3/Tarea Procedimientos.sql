USE Northwind

-- 1.- AGREGAR A LA TABLA PROVEEDORES EL CAMPO TOTALPIEZAS, EL CUAL REPRESENTARÁ EL 
-- TOTAL DE PIEZAS VENDIDAS DE CADA PROVEEDOR. CREAR UN PROCEDIMIENTO ALMACENADO 
-- QUE LLENE DICHO CAMPO.

-- CODIGO
ALTER TABLE Suppliers ADD TotalPiezas INT
GO
CREATE PROC SP_TOTALPIEZAS AS
BEGIN
	DECLARE @Proveedor INT,@Total INT
	SELECT @Proveedor = MIN(SupplierID)
	FROM Suppliers

	WHILE @Proveedor IS NOT NULL
	BEGIN
		SELECT @Total = SUM(OD.Quantity)
		FROM [Order Details] OD
		INNER JOIN Products P ON P.ProductID = OD.ProductID
		WHERE @Proveedor = P.SupplierID

		UPDATE Suppliers
		SET TotalPiezas = @Total
		WHERE SupplierID = @Proveedor

		SELECT @Proveedor = MIN(SupplierID)
		FROM Suppliers
		WHERE @Proveedor < SupplierID
	END
END
GO
-- EJECUCION
EXEC SP_TOTALPIEZAS
SELECT * FROM Suppliers
GO

-- 2.- SP QUE RECIBA LA CLAVE DEL EMPLEADO Y REGRESE 
-- POR RETORNO LA EDAD EXACTA DEL EMPLEADO.

-- CODIGO
CREATE PROC SP_EDAD_EXACTA @Emp INT, @Edad INT OUTPUT AS
BEGIN
	DECLARE @FechaNacimiento DATETIME
	SELECT @FechaNacimiento = BirthDate
	FROM Employees
	WHERE EmployeeID = @Emp

	SELECT @Edad = DATEDIFF(YY,@FechaNacimiento,GETDATE())
	SELECT @FechaNacimiento = DATEADD(YY,@Edad,@FechaNacimiento)
	IF @FechaNacimiento > GETDATE()
		SELECT @Edad = @Edad -1
END
GO
-- EJECUCION
DECLARE @Edad INT
EXEC SP_EDAD_EXACTA 1,@Edad OUTPUT
SELECT EdadExacta = @Edad
GO

-- 3.- PROCEDIMIENTO ALMACENADO QUE RECIBA COMO PARAMETRO UN AÑO Y REGRESE DOS PARAMETROS: 
--	• UN PARAMETRO CON EL NOMBRE DE TODOS LOS CLIENTES QUE COMPRARON ESE AÑO
--	• Y OTRO PARAMETRO CON LA LISTA DE LAS ORDENES REALIZADAS ESE AÑO.

-- CODIGO
CREATE PROC SP_ORDENES_AÑO 
@Año INT,@Clientes VARCHAR(5000) OUTPUT,@Ordenes VARCHAR(5000) OUTPUT AS
BEGIN
	DECLARE @Orden INT

	SELECT @Clientes = '', @Ordenes = ''

	SELECT @Orden = MIN(OrderID)
	FROM Orders
	WHERE YEAR(OrderDate) = @Año

	WHILE @Orden IS NOT NULL
	BEGIN
		SELECT @Clientes = @Clientes + C.CompanyName + ', ',
		@Ordenes = @Ordenes + STR(O.OrderId,5) + ', '
		FROM Orders O
		INNER JOIN Customers C ON C.CustomerID = O.CustomerID
		WHERE O.OrderID = @Orden

		SELECT @Orden = MIN(OrderID)
		FROM Orders
		WHERE YEAR(OrderDate) = @Año
		AND @Orden < OrderID
	END
	SELECT @Clientes = SUBSTRING(@Clientes,1,LEN(@Clientes)-1),
	@Ordenes = SUBSTRING(@Ordenes,1,LEN(@Ordenes)-1)
END
GO
-- EJECUCION
DECLARE @Clientes VARCHAR(5000),@Ordenes VARCHAR(5000)
EXEC SP_ORDENES_AÑO 1997,@Clientes OUTPUT, @Ordenes OUTPUT
SELECT Clientes = @Clientes, Ordenes = @Ordenes
GO

-- 4.- PROCEDIMIENTO ALMACENADO QUE REGRESE UNA TABLA CON EL AÑO Y LOS 
-- NOMBRES DE LOS CLIENTES QUE SE COMPRARON ESE AÑO.

-- CODIGO
CREATE PROC SP_CLIENTES_AÑO AS
BEGIN
	CREATE TABLE #TABLA(Año INT, NombreClientes VARCHAR(5000))
	
	DECLARE @Año INT
	SELECT @Año = MIN(YEAR(OrderDate))
	FROM Orders

	WHILE @Año IS NOT NULL
	BEGIN
		DECLARE @Orden INT, @Nombres VARCHAR(5000)
		SELECT @Nombres = ''
		SELECT @Orden = MIN(OrderID)
		FROM Orders
		WHERE YEAR(OrderDate) = @AÑO

		WHILE @Orden IS NOT NULL
		BEGIN
			SELECT @Nombres = @Nombres + C.CompanyName + ', '
			FROM Orders O
			INNER JOIN Customers C ON C.CustomerID = O.CustomerID
			WHERE O.OrderID = @Orden

			SELECT @Orden = MIN(OrderID)
			FROM Orders
			WHERE @Orden < OrderID
			AND YEAR(OrderDate) = @Año
		END

		SELECT @Nombres = SUBSTRING(@Nombres,1,LEN(@Nombres)-1)

		INSERT INTO #Tabla VALUES (@Año,@Nombres)

		SELECT @Año = MIN(YEAR(OrderDate))
		FROM Orders
		WHERE @Año < YEAR(OrderDate)
	END

	SELECT * FROM #TABLA
END
GO
-- EJECUCION
EXEC SP_CLIENTES_AÑO
GO

-- 5.- SP QUE RECIBA UN AÑO Y REGRESE COMO PARAMETRO DE SALIDA LA CLAVE
-- DEL ARTICULO QUE MAS SE VENDIO ESE AÑO Y CANTIDAD DE PIEZAS VENDIDAS
-- DE ESE PRODUCTO EN ESE AÑO.

-- CODIGO
CREATE PROC SP_MAS_VENDIDO 
@Año INT, @ClaveProducto INT OUTPUT, @CantPiezasVen INT OUTPUT AS
BEGIN
	SELECT TOP 1 @ClaveProducto = OD.ProductID, @CantPiezasVen = SUM(OD.Quantity)
	FROM [Order Details] OD
	INNER JOIN Orders O ON O.OrderID = OD.OrderID
	WHERE YEAR(O.OrderDate) = @Año
	GROUP BY OD.ProductID
	ORDER BY SUM(OD.Quantity) DESC
END
GO
-- EJECUCION
DECLARE @Clave INT, @Cantidad INT
EXEC SP_MAS_VENDIDO 1996,@Clave OUTPUT,@Cantidad OUTPUT
SELECT ClaveProducto = @Clave, CantidadVendida = @Cantidad
GO

-- 6.- FUNCION DE TABLA DE MULTISENTENCIA QUE RECIBA UN AÑO COMO PARAMETRO DE
-- ENTRADA, QUE REGRESE UNA TABLA CON DOS COLUMNAS: MES, FOLIOS QUE SE VENDIERON
-- ESE MES. NOTA: MOSTRAR TODOS LOS MESES.

-- CODIGO
CREATE PROC SP_MESES @Año INT AS
BEGIN
	CREATE TABLE #TABLA(Mes VARCHAR(20), Folios VARCHAR(500))
	DECLARE @Mes INT
	SELECT @Mes = 1
	WHILE @MES <= 12
	BEGIN
		DECLARE @Folios VARCHAR(500), @Orden INT
		SELECT @Folios = ''
		
		SELECT @Orden = MIN(OrderID)
		FROM Orders
		WHERE MONTH(OrderDate) = @Mes
		AND YEAR(OrderDate) = @Año

		WHILE @Orden IS NOT NULL
		BEGIN
			SELECT @Folios = @Folios + STR(OrderID,5) + ', '
			FROM Orders
			WHERE OrderID = @Orden

			SELECT @Orden = MIN(OrderID)
			FROM Orders
			WHERE MONTH(OrderDate) = @Mes
			AND @Orden < OrderID
			AND YEAR(OrderDate) = @Año
		END
		IF @Folios <> ''
			SELECT @Folios = SUBSTRING(@Folios,1,LEN(@Folios)-1)

		INSERT INTO #TABLA VALUES (DATENAME(MM,STR(@Año)+'/'+STR(@Mes)+'/01'),@Folios)
		SELECT @Mes = @Mes + 1
	END
	SELECT * FROM #TABLA
END
GO
-- EJECUCION
EXEC SP_MESES 1998
GO

-- 7.- SP QUE RECIBA LA CLAVE DEL EMPLEADO Y REGRESE COMO PARAMETRO DE SALIDA
-- TODOS LOS NOMBRES DE LOS TERRITORIOS QUE ATIENDEN EL EMPLEADO.

-- CODIGO
CREATE PROC SP_EMP_TERR @Emp INT, @Territorios VARCHAR(500) OUTPUT AS
BEGIN
	DECLARE @TerrID INT
	SELECT @Territorios = ''
	SELECT @TerrID = MIN(TerritoryID)
	FROM EmployeeTerritories
	WHERE EmployeeID = @Emp

	WHILE @TerrID IS NOT NULL
	BEGIN
		SELECT @Territorios = @Territorios + TRIM(TerritoryDescription) + ', '
		FROM Territories
		WHERE TerritoryID = @TerrID

		SELECT @TerrID = MIN(TerritoryID)
		FROM EmployeeTerritories
		WHERE EmployeeID = @Emp
		AND @TerrID < TerritoryID
	END
	SELECT @Territorios = SUBSTRING(@Territorios,1,LEN(@Territorios)-1)
END
GO
-- EJECUCION
DECLARE @Territorios VARCHAR(500)
EXEC SP_EMP_TERR 1,@Territorios OUTPUT
SELECT Territorios = @Territorios
GO

-- 8.- SP QUE REALICE UN PROCESO DONDE REGRESE LA SIGUIENTE TABLA:
-- NOMBRE DE EMPLEADO | NOMBRE DE JEFES | JEFE SUPERIOR

-- CODIGO
CREATE PROC SP_JEFES_EMP AS
BEGIN
	CREATE TABLE #TABLA(
	NombreEmpleado VARCHAR(50),
	NombreJefes VARCHAR(500), 
	JefeSuperior VARCHAR(50))

	DECLARE @Emp INT
	SELECT @Emp = MIN(EmployeeID)
	FROM Employees

	WHILE @Emp IS NOT NULL
	BEGIN
		DECLARE @NombreEmp VARCHAR(50), @Jefe INT, @NombreJefes VARCHAR(500), @JefeSuperior VARCHAR(50)
		SELECT @NombreEmp = FirstName + ' ' + LastName,
		@Jefe = ReportsTo
		FROM Employees
		WHERE EmployeeID = @Emp

 		SELECT @NombreJefes = ''

		WHILE @Jefe IS NOT NULL
		BEGIN
			SELECT @Jefe = ReportsTo,
			@NombreJefes = @NombreJefes + FirstName + ' ' + LastName + ', ',
			@JefeSuperior = FirstName + ' ' + LastName
			FROM Employees
			WHERE EmployeeID = @Jefe
		END
		IF @NombreJefes <> ''
			SELECT @NombreJefes = SUBSTRING(@NombreJefes,1,LEN(@NombreJefes)-1)
		
		INSERT INTO #TABLA VALUES(@NombreEmp,@NombreJefes,@JefeSuperior)

		SELECT @Emp = MIN(EmployeeID)
		FROM Employees
		WHERE @Emp < EmployeeID
	END
	SELECT * FROM #TABLA
END
-- EJECUCION
EXEC SP_JEFES_EMP
GO

-- 9.- PROCEDIMIENTO ALMACENADO QUE RECIBA EL NOMBRE DE UNA TABLA Y 
-- QUE EL PROCEDIMIENTO IMPRIMA EL CODIGO DE CREACIÓN DE DICHA TABLA.

-- CODIGO
CREATE PROC SP_TABLA @Tabla VARCHAR(50) AS
BEGIN
	DECLARE @Codigo VARCHAR(2000), @Fila INT
	SELECT @Codigo = 'CREATE TABLE ' + @Tabla + ' ('

	SELECT @Fila = MIN(colid)
	FROM syscolumns
	WHERE id = OBJECT_ID(@Tabla)
	AND name not like 'sysname'

	WHILE @Fila IS NOT NULL
	BEGIN
		DECLARE @Nombre VARCHAR(50), @TipoDato VARCHAR(50), @Longitud INT, @Scale INT, @Nullable BIT

		SELECT
		@Nombre = C.name,
		@TipoDato = T.name,
		@Scale = C.scale,
		@Longitud = C.prec,
		@Nullable = C.isnullable
		FROM syscolumns C
		INNER JOIN SYSTYPES T ON C.xtype = T.xtype
		WHERE colid = @Fila
		AND C.id = OBJECT_ID(@Tabla)
		AND T.name NOT LIKE 'sysname'

		SELECT @Codigo = @Codigo + @Nombre + ' ' + @TipoDato

		IF @Longitud IS NOT NULL AND @Scale IS NULL
			begin
			SELECT @Codigo = @Codigo + '('+TRIM(STR(@Longitud))+') '
			end
		ELSE
			SELECT @Codigo = @Codigo + ' '

		IF @Nullable = 0
			SELECT @Codigo = @Codigo + 'NOT NULL, '
		ELSE
			SELECT @Codigo = @Codigo + 'NULL, '

		SELECT @Fila = MIN(colid)
		FROM syscolumns
		WHERE id = OBJECT_ID(@Tabla)
		AND name not like 'sysname'
		AND @Fila < colid
	END

	SELECT @Codigo = SUBSTRING(@Codigo,1,LEN(@Codigo)-1)
	SELECT @Codigo = @Codigo + ')'

	SELECT Codigo = @Codigo
END
GO
-- EJECUCION
EXEC SP_TABLA 'Employees'

-- 10.- PROCEDIMIENTO ALMACENADO QUE AUMENTE EL PRECIO DE LOS PRODUCTOS
-- UN 10% SI SE HAN VENDIDO MENOS DE UN IMPORTE DE $2,000, 25% ENTRE 
-- $2,001 Y $3,000, 30% MAS DE UN IMPORTE DE $3,000.

-- CODIGO
CREATE PROC SP_AUMENTO AS
BEGIN
	DECLARE @Producto INT

	SELECT @Producto = MIN(ProductID)
	FROM Products

	WHILE @Producto IS NOT NULL
	BEGIN
		DECLARE @Importe MONEY

		SELECT @Importe = SUM(UnitPrice * Quantity)
		FROM [Order Details]
		WHERE ProductID = @Producto

		IF @Importe <= 2000
			UPDATE Products
			SET UnitPrice = UnitPrice * 1.10
			WHERE ProductID = @Producto
		ELSE IF @Importe BETWEEN 2001 AND 3000
			UPDATE Products
			SET UnitPrice = UnitPrice * 1.25
			WHERE ProductID = @Producto
		ELSE IF @Importe > 3000
			UPDATE Products
			SET UnitPrice = UnitPrice * 1.30
			WHERE ProductID = @Producto

		SELECT @Producto = MIN(ProductID)
		FROM Products
		WHERE @Producto < ProductID
	END
END
-- EJECUCION
SELECT * FROM Products
EXEC SP_AUMENTO
SELECT * FROM Products