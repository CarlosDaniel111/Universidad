-- 1
SELECT FirstName + CHAR(13) + LastName
FROM Employees

-- 2
SELECT *
FROM Employees
WHERE DATEDIFF(year, BirthDate, HireDate) BETWEEN 30 AND 50

-- 3
SELECT FirstName + ' ' + LastName 
+ ' ENTRO A TRABAJAR UN ' 
+ DATENAME(DW,HireDate) + ' ' 
+ DATENAME(DD,HireDate) + ' DE ' 
+ DATENAME(MM,HireDate) + ' DE ' 
+ DATENAME(YY,HIREDATE) + ' CON UNA EDAD DE ' 
+ STR(DATEDIFF(YEAR, BirthDate, HireDate),2) + ' AÑOS '
FROM Employees

-- 4
SELECT OrderID, OrderDate
FROM Orders
WHERE DATEPART(DW, OrderDate) = 2
AND DATEPART(MM, OrderDate) <= 6

-- 5
SELECT OrderID, OrderDate
FROM Orders
WHERE DATEPART(DW, OrderDate) = 6
AND EmployeeID IN (1,3,5)

-- 6
SELECT 'La orden '+ STR(OrderID,5) 
+ ' fue realizada por el cliente ' + CustomerID 
+ ' y atendida por el empleado ' + STR(EmployeeID,1)
FROM Orders

-- 7
SELECT *
FROM Customers
WHERE ContactName LIKE '[^AEIOU]%' 
AND LEN(ContactName) > 10

-- 8
SELECT *
FROM Products
WHERE ProductName LIKE '[ABN]%N'

-- 9
SELECT *
FROM Employees
WHERE FirstName LIKE '%[^AEIOU]' 
AND LastName LIKE '%[^AEIOU]'

-- 10
SELECT *
FROM Orders
WHERE DATENAME(MM,OrderDate) LIKE '[AEIOU]%'

-- 11
SELECT *
FROM Products
WHERE LEN(ProductName) -
LEN(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(ProductName,'A',''),'E',''),'I',''),'O',''),'U','')) = 3

-- 12
SELECT OrderDate
FROM Orders
WHERE YEAR(OrderDate) % 3 = 0

-- 13
SELECT *
FROM Orders
WHERE DATEPART(DW,OrderDate) IN (7,1)
AND EmployeeID IN (1,2,5)

-- 14
SELECT *
FROM Orders
WHERE ShipVia IS NULL
OR MONTH(OrderDate) = 1

-- 15
SELECT TOP 10 *
FROM Orders
WHERE YEAR(OrderDate) = 1997
ORDER BY OrderDate DESC

-- 16
SELECT TOP 10 *
FROM Products
WHERE SupplierID IN (1,2,5)
ORDER BY UnitPrice DESC

-- 17
SELECT TOP 4 *
FROM Employees
ORDER BY HireDate ASC

-- 18
SELECT *
FROM Employees
WHERE (DATEDIFF(YEAR,HireDate,GETDATE()) IN (10,20,30) AND DATEDIFF(YEAR,BirthDate,GETDATE()) > 30)
OR (Address LIKE '%blvd%' AND Region IS NULL)

-- 19
SELECT *
FROM Orders
WHERE ShipPostalCode NOT LIKE '%[^A-Z]%'

-- 20
SELECT *
FROM Orders
WHERE YEAR(OrderDate) = 1996 
AND DATENAME(MM,OrderDate) LIKE '%R%'