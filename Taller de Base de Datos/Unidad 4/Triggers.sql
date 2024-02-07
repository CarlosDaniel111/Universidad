USE Northwind
GO
-- 1.-  En la tabla suppliers se agregó el campo TotalPiezas (es continuación del 
-- ejercicio de 1 de SP), realizar un trigger que actualice automáticamente dicho campo.
CREATE TRIGGER TR_TotalPiezas_INS
ON [Order Details] FOR INSERT AS
BEGIN
	DECLARE @Proveedor INT, @Total INT
	SELECT @Proveedor = P.SupplierID
	FROM INSERTED OD
	INNER JOIN Products P ON OD.ProductID = P.ProductID

	SELECT @Total = SUM(OD.Quantity)
	FROM [Order Details] OD
	INNER JOIN Products P ON P.ProductID = OD.ProductID
	WHERE @Proveedor = P.SupplierID

	UPDATE Suppliers
	SET TotalPiezas = @Total
	WHERE SupplierID = @Proveedor
END
GO
CREATE TRIGGER TR_TotalPiezas_DEL
ON [Order Details] FOR DELETE AS
BEGIN
	DECLARE @Proveedor INT, @Total INT
	SELECT @Proveedor = P.SupplierID
	FROM DELETED OD
	INNER JOIN Products P ON OD.ProductID = P.ProductID

	SELECT @Total = SUM(OD.Quantity)
	FROM [Order Details] OD
	INNER JOIN Products P ON P.ProductID = OD.ProductID
	WHERE @Proveedor = P.SupplierID

	UPDATE Suppliers
	SET TotalPiezas = @Total
	WHERE SupplierID = @Proveedor
END
GO
CREATE TRIGGER TR_TotalPiezas_UPD
ON [Order Details] FOR UPDATE AS
BEGIN
	DECLARE @Proveedor INT, @Total INT
	SELECT @Proveedor = P.SupplierID
	FROM INSERTED OD
	INNER JOIN Products P ON OD.ProductID = P.ProductID

	SELECT @Total = SUM(OD.Quantity)
	FROM [Order Details] OD
	INNER JOIN Products P ON P.ProductID = OD.ProductID
	WHERE @Proveedor = P.SupplierID

	UPDATE Suppliers
	SET TotalPiezas = @Total
	WHERE SupplierID = @Proveedor
END
GO
-- 2.- Realizar un procedimiento almacenado que genere el código para que 
-- no se puedan realizar actualizaciones masivas en todas las tablas de cualquier 
CREATE PROC SP_TRIGGERS AS
BEGIN
	DECLARE @TablaID INT

	SELECT @TablaID = MIN(object_id)
	FROM sys.tables
	WHERE name NOT LIKE 'sysdiagrams'

	WHILE @TablaID IS NOT NULL
	BEGIN
		DECLARE @Tabla NVARCHAR(50)
		SELECT @Tabla = name
		FROM sys.tables
		WHERE object_id = @TablaID

		PRINT 'CREATE TRIGGER TR_'+@Tabla+'_UPD'+char(10)+
		'ON '+@Tabla+' FOR UPDATE AS'+char(10)+
		'	DECLARE @CONTA INT '+char(10)+
		'	SELECT @CONTA = COUNT(*) FROM INSERTED'+char(10)+
		char(10)+
		'	IF @CONTA > 1'+char(10)+
		'	BEGIN'+char(10)+
	 	'		ROLLBACK TRAN'+char(10)+
		'		RAISERROR( ''NO SE PERMITEN ACTUALIZACIONES MASIVAS'' ,16 ,  1 )'+char(10)+
		'	END'+char(10)+
		'GO'+char(10)

		SELECT @TablaID = MIN(object_id)
		FROM sys.tables
		WHERE name NOT LIKE 'sysdiagrams'
		AND @TablaID < object_id
	END
END
GO
EXEC SP_TRIGGERS
GO
-- 3.- Es necesario llevar el registro Historico de los precios de los productos, es 
-- necesario conocer la fecha y hora cuando se realiza la actualización, el nuevo valor
-- del precio, el inicio de sesión que está realizando el cambio.
CREATE TABLE Historico(
Producto INT,
Fecha DATETIME,
NuevoPrecio MONEY,
Usuario NVARCHAR(500))
GO
CREATE TRIGGER TR_HISTORICO_INS
ON Products FOR INSERT AS
BEGIN
	DECLARE @Producto INT, @NuevoPrecio MONEY
	SELECT @Producto = ProductID,
	@NuevoPrecio = UnitPrice
	FROM inserted
	INSERT INTO Historico VALUES(@Producto,GETDATE(),@NuevoPrecio,CURRENT_USER)
END
GO
CREATE TRIGGER TR_HISTORICO_UPD
ON Products FOR UPDATE AS
BEGIN
	IF UPDATE(UnitPrice)
	BEGIN
		DECLARE @Producto INT, @NuevoPrecio MONEY
		SELECT @Producto = ProductID,
		@NuevoPrecio = UnitPrice
		FROM inserted
		INSERT INTO Historico VALUES(@Producto,GETDATE(),@NuevoPrecio,CURRENT_USER)
	END
END
GO
-- 4.- Utilizando trigger, validar que solo se vendan ordenes de lunes a viernes.
CREATE TRIGGER TR_ORDSEMANA_INS
ON Orders FOR INSERT AS
BEGIN
	DECLARE @Dia INT
	
	SELECT @Dia = DATEPART(DW,OrderDate)
	FROM inserted

	IF @Dia IN (1,7)
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITEN ORDENES EN FIN DE SEMANA' ,16, 1)
	END
END
GO
CREATE TRIGGER TR_ORDSEMANA_UPD
ON Orders FOR UPDATE AS
BEGIN
	DECLARE @Dia INT
	
	SELECT @Dia = DATEPART(DW,OrderDate)
	FROM inserted

	IF @Dia IN (1,7)
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITEN ORDENES EN FIN DE SEMANA' ,16 ,  1 )
	END
END
GO
-- 5.- Validar que no se vendan mas de 20 ordenes por empleado en una semana.
CREATE TRIGGER TR_ORD20_INS
ON Orders FOR INSERT AS
BEGIN
	DECLARE @Emp INT, @Semana INT, @Año INT, @Ordenes INT

	SELECT @Emp = EmployeeID,
	@Semana = DATEPART(WW,OrderDate),
	@Año = YEAR(OrderDate)
	FROM inserted

	SELECT @Ordenes = COUNT(*)
	FROM Orders
	WHERE @Emp = EmployeeID 
	AND @Semana = DATEPART(WW,OrderDate)
	AND @Año = YEAR(OrderDate)

	IF @Ordenes > 20
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITEN MAS DE 20 ORDENES POR EMPLEADO POR SEMANA' ,16 ,  1 )
	END
END
GO
CREATE TRIGGER TR_ORD20_UPD
ON Orders FOR UPDATE AS
BEGIN
	DECLARE @Emp INT, @Semana INT, @Año INT, @Ordenes INT

	SELECT @Emp = EmployeeID,
	@Semana = DATEPART(WW,OrderDate),
	@Año = YEAR(OrderDate)
	FROM inserted

	SELECT @Ordenes = COUNT(*)
	FROM Orders
	WHERE @Emp = EmployeeID 
	AND @Semana = DATEPART(WW,OrderDate)
	AND @Año = YEAR(OrderDate)

	IF @Ordenes > 20
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITEN MAS DE 20 ORDENES POR EMPLEADO POR SEMANA' ,16 ,  1 )
	END
END
GO
-- 6.- Validar que el campo firstname en la tabla employees solamente
-- tenga nombres que inicien con vocal.
CREATE TRIGGER TR_VOCAL_INS
ON Employees FOR INSERT AS
BEGIN
	DECLARE @Name NVARCHAR(50)
	SELECT @Name = FirstName
	FROM inserted

	IF @Name NOT LIKE '[aeiou]%'
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITEN FirstName QUE NO INICIEN CON VOCAL' ,16 ,  1 )
	END
END
GO
CREATE TRIGGER TR_VOCAL_UPD
ON Employees FOR UPDATE AS
BEGIN
	IF UPDATE(FirstName)
	BEGIN
		DECLARE @Name NVARCHAR(50)
		SELECT @Name = FirstName
		FROM inserted

		IF @Name NOT LIKE '[aeiou]%'
		BEGIN
			ROLLBACK TRAN
			RAISERROR('NO SE PERMITEN FirstName QUE NO INICIEN CON VOCAL' ,16 ,  1 )
		END
	END
END
GO
-- 7.- validar que el importe de venta de cada orden no sea mayor a $10,000.
CREATE TRIGGER TR_ORDMAYOR_INS
ON [Order Details] FOR INSERT AS
BEGIN
	DECLARE @Importe MONEY, @Orden CHAR(5)

	SELECT @Orden = OrderID
	FROM inserted

	SELECT @Importe = SUM(Quantity * UnitPrice)
	FROM [Order Details]
	WHERE OrderID = @Orden

	IF @Importe > 10000
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITEN ORDENES MAYORES A $10,000' ,16 ,  1 )
	END
END
GO
CREATE TRIGGER TR_ORDMAYOR_UPD
ON [Order Details] FOR UPDATE AS
BEGIN
	DECLARE @Importe MONEY, @Orden CHAR(5)

	SELECT @Orden = OrderID
	FROM inserted

	SELECT @Importe = SUM(Quantity * UnitPrice)
	FROM [Order Details]
	WHERE OrderID = @Orden

	IF @Importe > 10000
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITEN ORDENES MAYORES A $10,000' ,16 ,  1 )
	END
END
GO
-- 8.- Validar que solo se pueda actualizar una sola vez el nombre del cliente.
ALTER TABLE Customers ADD Contador INT
GO
UPDATE Customers SET Contador = 0
GO
ALTER TRIGGER TR_CLIENTE_UPD
ON Customers FOR UPDATE AS
BEGIN
	IF UPDATE(CompanyName)
	BEGIN
		DECLARE @Contador INT, @ID CHAR(5)

		SELECT @Contador = Contador,
		@ID = CustomerID
		FROM inserted

		IF @Contador = 1
		BEGIN
			ROLLBACK TRAN
			RAISERROR('NO SE PERMITE ACTUALIZAR EL NOMBRE DEL CLIENTE MAS DE UNA VEZ' ,16 ,  1 )
		END
		ELSE
		BEGIN
			UPDATE Customers SET Contador = 1 WHERE CustomerID = @ID
		END
	END
END
GO
-- 9.- Validar que no se puedan eliminar categorías que tengan una clave impar.
CREATE TRIGGER TR_CATEGORIA_DEL
ON Categories FOR DELETE AS
BEGIN
	DECLARE @Clave INT

	SELECT @Clave = CategoryID
	FROM deleted

	IF @CLAVE % 2 = 1
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITE ELIMINAR CATEGORIAS CON CLAVE IMPAR' ,16 ,  1 )
	END
END
GO
-- 10.- Validar que no se puedan insertar ordenes que se realicen en domingo.
CREATE TRIGGER TR_ORDDOMINGO_INS
ON Orders FOR INSERT AS
BEGIN
	DECLARE @Dia INT

	SELECT @Dia = DATEPART(DW,OrderDate)
	FROM inserted

	IF @Dia = 1
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITE INSERTAR ORDENES EN DOMINGO' ,16 ,  1 )
	END
END
GO
CREATE TRIGGER TR_ORDDOMINGO_UPD
ON Orders FOR UPDATE AS
BEGIN
	DECLARE @Dia INT

	SELECT @Dia = DATEPART(DW,OrderDate)
	FROM inserted

	IF @Dia = 1
	BEGIN
		ROLLBACK TRAN
		RAISERROR('NO SE PERMITE INSERTAR ORDENES EN DOMINGO' ,16 ,  1 )
	END
END