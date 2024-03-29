-- 1.- CONSULTA CON EL FOLIO, FECHA DE LA ORDEN, NOMBRE DE LA COMPA��A DE ENVIO, 
-- MOSTRAR LOS REGISTROS CUYO A�O SEA MULTIPLO DE 3 Y EL MES CONTENGA LA LETRA R.
SELECT O.OrderID,O.OrderDate,S.CompanyName
FROM Orders O
INNER JOIN Shippers S ON S.ShipperID = O.ShipVia
WHERE YEAR(O.OrderDate) % 3 = 0
AND DATENAME(MM,O.OrderDate) LIKE '%R%'

-- 2.-  CONSULTA CON EL FOLIO DE LA ORDEN, FECHA Y NOMBRE DEL CLIENTE QUE SE HAYAN 
-- REALIZADO LOS DIAS LUNES, MI�RCOLES Y VIERNES Y QUE EL CLIENTE VIVA EN UN AVENIDA.
SELECT O.OrderID,O.OrderDate,C.ContactName
FROM Orders O
INNER JOIN Customers C ON C.CustomerID = O.CustomerID
WHERE DATEPART(DW,O.OrderDate) IN (2,4,6)
AND C.Address LIKE '%AV%'

-- 3.- CONSULTA CON LAS PRIMERAS 10 ORDENES DE 1997 DEL EMPLEADO 
--QUE NACIERON EN LA DECADA DE 1960.
SELECT TOP 10 O.*
FROM Orders O
INNER JOIN Employees E ON E.EmployeeID = O.EmployeeID
WHERE YEAR(E.BirthDate) BETWEEN 1960 AND 1969
AND YEAR(O.OrderDate) = 1997
ORDER BY O.OrderDate ASC

-- 4.- CONSULTA CON EL NOMBRE DEL EMPLEADOS Y NOMBRE DE SU JEFE, MOSTRAR LOS
-- EMPLEADOS QUE SU NOMBRE INICIE CON VOCAL Y TENGAN UNA REGION ASIGNADA.
SELECT NombreEmpleado = E.FirstName,NombreJefe = J.FirstName
FROM Employees E
JOIN Employees J ON E.ReportsTo = J.EmployeeID
WHERE E.FirstName LIKE '[AEIOU]%'
AND E.Region IS NOT NULL

-- 5.- CONSULTA CON EL NOMBRE DEL PRODUCTO, NOMBRE DEL PROVEEDOR Y NOMBRE DE LA 
-- CATEGORIA. MOSTRAR SOLO LOS PROVEEDORES QUE SU TELEFONO INICIE CON 0,4 O 5 Y 
-- QUE NO TENGAN HOMEPAGE.
SELECT P.ProductName,S.ContactName,C.CategoryName
FROM Products P
INNER JOIN Suppliers S ON S.SupplierID = P.SupplierID
INNER JOIN Categories C ON C.CategoryID = P.CategoryID
WHERE S.Phone LIKE '[045]%'
AND S.HomePage IS NULL

-- 6.- CONSULTA CON EL NOMBRE DEL EMPLEADO Y NOMBRE DEL TERRITORIO QUE ATIENDE. 
-- MOSTRAR SOLO LOS NOMBRE DE EMPLEADO Y TERRITORIOS QUE EMPIECEN 
-- Y TERMINEN CON VOCAL.
SELECT E.FirstName,T.TerritoryDescription
FROM Employees E
INNER JOIN EmployeeTerritories ET ON ET.EmployeeID = E.EmployeeID
INNER JOIN Territories T ON T.TerritoryID = ET.TerritoryID
WHERE E.FirstName LIKE '[AEIOU]%[AEIOU]'
AND T.TerritoryDescription LIKE '[AEIOU]%[AEIOU]'

-- 7.- CONSULTA CON EL FOLIO DE LA ORDEN, MESES TRANSCURRIDOS DE LA ORDEN, NOMBRE 
-- DEL EMPLEADO QUE HIZO LA ORDEN. MOSTRAR SOLO LAS ORDENES DE LOS EMPLEADOS 	
-- QUE VIVAN EN EL PAIS �USA� Y QUE EL CODIGO POSTAL CONTENGA UN 2.
SELECT O.OrderID, MESES = DATEDIFF(MM,O.OrderDate,GETDATE()),E.FirstName
FROM Orders O
INNER JOIN Employees E ON E.EmployeeID = O.EmployeeID
WHERE E.Country = 'USA'
AND E.PostalCode LIKE '%2%'

-- 8.- CONSULTA CON EL FOLIO DE LA ORDEN, NOMBRE DEL PRODUCTO E IMPORTE DE VENTA. 
-- MOSTRAR SOLO LAS ORDENES DE LOS PRODUCTOS CUYA CATEGERIA CONTENGA DOS 
-- VOCALES SEGUIDAS O QUE SU PENULTIMA LETRA SEA CONSONANTE.
SELECT O.OrderID, P.ProductName,Importe = OD.UnitPrice*OD.Quantity
FROM Orders O
INNER JOIN [Order Details] OD ON OD.OrderID = O.OrderID
INNER JOIN Products P ON P.ProductID = OD.ProductID
INNER JOIN Categories C ON C.CategoryID = P.CategoryID
WHERE C.CategoryName LIKE '%[AEIOU][AEIOU]%'
OR C.CategoryName LIKE '%[^AEIOU]_'

-- 9.- CONSULTA CON EL NOMBRE DEL EMPLEADO, NOMBRE DEL TERRITORIO QUE ATIENDE. 
-- MOSTRAS SOLO LOS EMPLEADOS QUE EL TERRITORIO ESTE EN UNA REGION SU SEGUNDA 
-- LETRA SEA LA LETRA O.
SELECT E.FirstName,T.TerritoryDescription
FROM Employees E
INNER JOIN EmployeeTerritories ET ON ET.EmployeeID = E.EmployeeID
INNER JOIN Territories T ON T.TerritoryID = ET.TerritoryID
INNER JOIN Region R ON R.RegionID = T.RegionID
WHERE R.RegionDescription LIKE '_O%'

-- 10.- CONSULTA CON EL FOLIO DE LA ORDEN, FECHA DE LA ORDEN, NOMBRE DEL EMPLEADO, 
-- EDAD QUE TENIA EL EMPLEADO CUANDO HIZO LA ORDEN.
SELECT O.OrderID,O.OrderDate,E.FirstName,Edad = DATEDIFF(YY,E.BirthDate,O.OrderDate)
FROM Orders O
INNER JOIN Employees E ON E.EmployeeID = O.EmployeeID