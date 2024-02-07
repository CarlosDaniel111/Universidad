USE Northwind
GO
-- 1.- FUNCION ESCALAR QUE RECIBA DOS CLAVES DE CLIENTES,UN A�O Y UN MES, 
-- Y REGRESE EL NOMBRE DEL CLIENTE QUE MAS HA VENDIDO PIEZAS DE LOS DOS EN
-- ESE A�O-MES Y EL TOTAL DE PIEZAS VENDIDAS. POR EJEMPLO, DEBE REGRESAR: 
-- EL CLIENTE JUAN PEREZ VENDIO 450 PIEZAS.

-- CODIGO
CREATE FUNCTION DBO.MAYOR_CLIENTE (
@Cliente1 NVARCHAR(5), @Cliente2 NVARCHAR(5), @A�o INT, @Mes INT)
RETURNS NVARCHAR(100)
AS
BEGIN
	DECLARE @Nombre NVARCHAR(50), @Total1 INT, @Total2 INT, @R NVARCHAR(100)
	SELECT @Total1 = SUM(OD.Quantity)
	From [Order Details] OD
	INNER JOIN Orders O ON O.OrderID = OD.OrderID
	WHERE O.CustomerID = @Cliente1
	AND YEAR(O.OrderDate) = @A�o
	AND MONTH(O.OrderDate) = @Mes

	SELECT @Total2 = SUM(OD.Quantity)
	From [Order Details] OD
	INNER JOIN Orders O ON O.OrderID = OD.OrderID
	WHERE O.CustomerID = @Cliente2
	AND YEAR(O.OrderDate) = @A�o
	AND MONTH(O.OrderDate) = @Mes

	IF @Total1 > @Total2
		BEGIN
			SELECT @Nombre = ContactName
			FROM Customers
			WHERE CustomerID = @Cliente1
			SELECT @R = 'EL CLIENTE '+@Nombre+' VENDIO '+TRIM(STR(@Total1))+' PIEZAS'
		END
	ELSE
		BEGIN
			SELECT @Nombre = ContactName
			FROM Customers
			WHERE CustomerID = @Cliente2
			SELECT @R = 'EL CLIENTE '+@Nombre+' VENDIO '+TRIM(STR(@Total2))+' PIEZAS'
		END

	RETURN @r
END
GO
-- EJECUCION
SELECT Resultado = dbo.MAYOR_CLIENTE('TOMSP','VICTE',1996,7)
GO

-- 2.- FUNCION ESCALAR QUE RECIBA LA CLAVE DEL TERRITORIO Y REGRESE UNA CADENA 
-- CON LOS NOMBRE DE LOS EMPLEADOS QUE SURTEN DICHO TERRITORIO.

-- CODIGO
CREATE FUNCTION DBO.TERRITORIO_EMP(@Terr NVARCHAR(5))
RETURNS NVARCHAR(1000) 
AS
BEGIN
	DECLARE @Nombres NVARCHAR(1000), @Emp INT

	SELECT @Emp = MIN(EmployeeID)
	FROM EmployeeTerritories
	WHERE TerritoryID = @Terr
	SELECT @Nombres = ''

	WHILE @Emp IS NOT NULL
	BEGIN
		SELECT @Nombres = @Nombres + FirstName + ' ' + LastName + ', '
		FROM Employees 
		WHERE EmployeeID = @Emp
		
		SELECT @Emp = MIN(EmployeeID)
		FROM EmployeeTerritories
		WHERE TerritoryID = @Terr
		AND @Emp < EmployeeID
	END
	SELECT @Nombres = SUBSTRING(@Nombres,1,LEN(@Nombres)-1)
	RETURN @Nombres
END
GO
-- EJECUCION
SELECT Nombres = dbo.TERRITORIO_EMP('31406')
GO

-- 3.- FUNCION DE TABLA EN LINEA QUE RECIBA LA CLAVE DE UN PROVEEDOR Y REGRESE UNA
-- TABLA CON EL NOMBRE DE TODOS LOS PRODUCTOS QUE HA VENDIDO ESE PROVEEDOR, EL
-- TOTAL DE PRODUCTOS VENDIDOS Y EL TOTAL DE ORDENES EN LAS QUE SE HAN VENDIDO.

-- CODIGO
CREATE FUNCTION DBO.PROVEEDOR_PROD(@Prov INT)
RETURNS TABLE 
AS
RETURN (
	SELECT 'NombreProducto' = P.ProductName,
	'TotalProductos' = SUM(OD.Quantity),
	'TotalOrdenes' = COUNT(O.OrderID)
	FROM [Order Details] OD
	INNER JOIN Orders O ON O.OrderID = OD.OrderID
	INNER JOIN Products P ON OD.ProductID = P.ProductID
	INNER JOIN Suppliers S ON S.SupplierID = P.SupplierID
	WHERE S.SupplierID = @Prov 
	GROUP BY P.ProductName
)
GO
-- EJECUCION
SELECT * FROM dbo.PROVEEDOR_PROD(1)
GO

-- 4.- FUNCION DE TABLA EN LINEA QUE RECIBA LA CLAVE DEL EMPLEADO, A�O Y MES, REGRESE
-- EN UNA CONSULTA EL NOMBRE DEL PRODUCTO Y TOTAL DE PRODUCTOS VENDIDOS POR ESE
-- EMPLEADO Y ESE A�O-MES.

-- CODIGO
CREATE FUNCTION DBO.PRODUCTOS_EMP(@Emp INT,@A�o INT,@Mes INT)
RETURNS TABLE 
AS
RETURN(
	SELECT 'NombreProducto' = P.ProductName,
	'TotalPiezas' = SUM(OD.Quantity)
	FROM [Order Details] OD
	INNER JOIN Orders O ON O.OrderID = OD.OrderID
	INNER JOIN Employees E ON E.EmployeeID = O.EmployeeID
	INNER JOIN Products P ON P.ProductID = OD.ProductID
	WHERE YEAR(O.OrderDate) = @A�o
	AND MONTH(O.OrderDate) = @Mes
	AND E.EmployeeID = @Emp
	GROUP BY P.ProductName
)
GO
-- EJECUCION
SELECT * FROM dbo.PRODUCTOS_EMP(1,1997,5)
GO

-- 5.- UTILIZANDO LA FUNCION ANTERIOR MOSTRAR UNA CONSULTA SIGUIENTE:
-- NombreProducto|TotalPiezas 96-Ene|TotalPiezas 97-Ene|TotalPiezas 98-Ene

-- EJECUCION
SELECT 'NombreProducto' = P.ProductName,
'TotalPiezas 96-Ene' = ISNULL(A.TotalPiezas,0),
'TotalPiezas 97-Ene' = ISNULL(B.TotalPiezas,0),
'TotalPiezas 98-Ene' = ISNULL(C.TotalPiezas,0)
FROM Products P
LEFT JOIN dbo.PRODUCTOS_EMP(1,1996,1) A ON P.ProductName = A.NombreProducto
LEFT JOIN dbo.PRODUCTOS_EMP(1,1997,1) B ON P.ProductName = B.NombreProducto
LEFT JOIN dbo.PRODUCTOS_EMP(1,1998,1) C ON P.ProductName = C.NombreProducto
GO

-- 6.- FUNCION DE TABLA DE MULTISENTENCIA (NO LLEVA PARAMETROS DE ENTRADA) QUE 
-- REGRESE UNA TABLA CON EL NOMBRE DE LA CATEGORIA Y LOS NOMBRES DE LOS 
-- PRODUCTOS QUE PERTENECEN A LA CATEGORIA Y EL TOTAL DE PIEZAS QUE SE 
-- HAN VENDIDO DE ESA CATEGORIA

-- CODIGO
CREATE FUNCTION DBO.CATEGORIA_PROD()
RETURNS @Tabla TABLE(
Categoria NVARCHAR(30), 
Productos NVARCHAR(500), 
PiezasVendidas INT)
AS
BEGIN
	DECLARE @CatID INT

	SELECT @CatID = MIN(CategoryID)
	FROM Categories

	WHILE @CatID IS NOT NULL
	BEGIN
		DECLARE @NomCate NVARCHAR(30), @NomProd NVARCHAR(500), @Piezas INT, @Prod INT
		SELECT @NomCate = CategoryName
		FROM Categories
		WHERE CategoryID = @CatID

		SELECT @Prod = MIN(P.ProductID)
		FROM Products P
		WHERE P.CategoryID = @CatID 

		SELECT @NomProd = ''

		WHILE @Prod IS NOT NULL
		BEGIN
			SELECT @NomProd = @NomProd + ProductName + ', ' 
			FROM Products
			WHERE ProductID = @Prod

			SELECT @Prod = MIN(P.ProductID)
			FROM Products P
			WHERE P.CategoryID = @CatID 
			AND @Prod < P.ProductID
		END
		SELECT @NomProd = SUBSTRING(@NomProd,1,LEN(@NomProd)-1)

		SELECT @Piezas = SUM(OD.Quantity)
		FROM [Order Details] OD
		INNER JOIN Products P ON P.ProductID = OD.ProductID
		WHERE P.CategoryID = @CatID

		INSERT INTO @Tabla VALUES(@NomCate,@NomProd,@Piezas)

		SELECT @CatID = MIN(CategoryID)
		FROM Categories
		WHERE @CatID < CategoryID
	END
	RETURN
END
GO
-- EJECUCION
SELECT * FROM dbo.CATEGORIA_PROD()
GO

-- 7.- FUNCION DE TABLA DE MULTISENTENCIA QUE RECIBA UN A�O COMO PARAMETRO DE
-- ENTRADA, QUE REGRESE UNA TABLA CON DOS COLUMNAS: DIA DE LA SEMANA, FOLIOS
-- QUE SE VENDIERON ESE D�A DE SEMANA. NOTA, DEBE MOSTRAR TODOS LOS DIAS DE
-- LA SEMANA, AUNQUE NO SE HAYAN REALIZADO ORDENES.

-- CODIGO
CREATE FUNCTION DBO.FOLIOS_SEMANA(@A�o INT)
RETURNS @Tabla TABLE ([DIA SEMANA] NVARCHAR(20),FOLIOS NVARCHAR(500))
AS
BEGIN
	DECLARE @DiaSemana TABLE(DIAID INT,DIA NVARCHAR(20))
	INSERT INTO @DiaSemana VALUES
	(1,'DOMINGO'),
	(2,'LUNES'),
	(3,'MARTES'),
	(4,'MIERCOLES'),
	(5,'JUEVES'),
	(6,'VIERNES'),
	(7,'SABADO')

	DECLARE @Dia INT
	SELECT @Dia = MIN(DIAID)
	FROM @DiaSemana

	WHILE @Dia IS NOT NULL
	BEGIN
		DECLARE @Folio NVARCHAR(5), @Folios NVARCHAR(500)
		SELECT @Folio = MIN(OrderID)
		FROM Orders
		WHERE YEAR(OrderDate) = @A�o
		AND DATEPART(DW,OrderDate) = @Dia

		SELECT @Folios = ''

		WHILE @Folio IS NOT NULL
		BEGIN
			SELECT @Folios = @Folios + @Folio + ', '
			SELECT @Folio = MIN(OrderID)
			FROM Orders
			WHERE YEAR(OrderDate) = @A�o
			AND DATEPART(DW,OrderDate) = @Dia
			AND @Folio < OrderID
		END
		IF @Folios <> ''
			SELECT @Folios = SUBSTRING(@Folios,1,LEN(@Folios)-1)
		
		DECLARE @NomDia NVARCHAR(20)
		SELECT @NomDia = DIA
		FROM @DiaSemana
		WHERE DIAID = @Dia

		INSERT INTO @Tabla VALUES(@NomDia,@Folios)

		SELECT @Dia = MIN(DIAID)
		FROM @DiaSemana
		WHERE @Dia < DIAID
	END
	RETURN
END
GO
-- EJECUCION
SELECT * FROM dbo.FOLIOS_SEMANA(1997)
GO

-- 8.- FUNCION DE TABLA DE MULTISENTENCIA QUE RECIBA LA CLAVE DE UN EMPLEADO Y 
-- REGRESE UNA TABLA LOS DIAS DE LA SEMANA Y LOS CUMPLEA�OS QUE SE HA FESTEJADO 
-- ESE DIA DE LA SEMANA

-- CODIGO
CREATE FUNCTION DBO.CUMPLEA�OS_SEMANA(@Emp INT)
RETURNS @Tabla TABLE ([DIA SEMANA] NVARCHAR(20),FESTEJOS NVARCHAR(500))
AS
BEGIN
	DECLARE @DiaSemana TABLE(DIAID INT,DIA NVARCHAR(20))
	INSERT INTO @DiaSemana VALUES
	(1,'DOMINGO'),
	(2,'LUNES'),
	(3,'MARTES'),
	(4,'MIERCOLES'),
	(5,'JUEVES'),
	(6,'VIERNES'),
	(7,'SABADO')

	DECLARE @Dia INT
	SELECT @Dia = MIN(DIAID)
	FROM @DiaSemana

	WHILE @Dia IS NOT NULL
	BEGIN
		DECLARE @Cumplea�os DATETIME, @Festejos NVARCHAR(500)
		SELECT @Cumplea�os = BirthDate
		FROM Employees
		WHERE EmployeeID = @Emp

		SELECT @Festejos = ''

		WHILE @Cumplea�os < GETDATE()
		BEGIN 
			DECLARE @D INT
			SELECT @D = DATEPART(DW,@Cumplea�os)
			IF @D = @Dia
				SELECT @Festejos = @Festejos + TRIM(STR(YEAR(@Cumplea�os))) + ', '

			SELECT @Cumplea�os = DATEADD(YY,1,@Cumplea�os)
		END
		IF @Festejos <> ''
			SELECT @Festejos = SUBSTRING(@Festejos,1,LEN(@Festejos)-1)

		DECLARE @NomDia NVARCHAR(20)
		SELECT @NomDia = DIA
		FROM @DiaSemana
		WHERE DIAID = @Dia

		INSERT INTO @Tabla VALUES(@NomDia,@Festejos)

		SELECT @Dia = MIN(DIAID)
		FROM @DiaSemana
		WHERE @Dia < DIAID
	END
	RETURN
END
GO
-- EJECUCION
SELECT * FROM DBO.CUMPLEA�OS_SEMANA(1)