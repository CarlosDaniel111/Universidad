USE NORTHWIND
GO
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


SELECT * FROM Suppliers
INSERT INTO [Order Details] VALUES('10248',1,10,5,0)
SELECT * FROM Suppliers

SELECT * FROM Suppliers
UPDATE [Order Details] SET Quantity = 10
WHERE OrderID = '10248' AND
ProductID = 1
SELECT * FROM Suppliers

SELECT * FROM Suppliers
DELETE FROM [Order Details]
WHERE OrderID = '10248' AND
ProductID = 1
SELECT * FROM Suppliers