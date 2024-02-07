Use Northwind
GO
-- CREACION DE VISTAS
CREATE VIEW vw_products AS
SELECT
p.productid, p.productname, p.quantityperunit, produnitprice = p.unitprice,
p.unitsinstock, p.unitsonorder, p.reorderlevel, p.discontinued,
s.supplierid, s.companyname, s.contactname, s.contacttitle, s.address,
s.city, s.region, s.postalcode, s.country, s.phone, s.fax, s.homepage,
c.categoryid, c.categoryname, c.description, c.picture
FROM products p
INNER JOIN suppliers s ON p.supplierid=s.supplierid
INNER JOIN categories c ON p.categoryid=c.categoryid
GO
CREATE VIEW vw_orders AS
SELECT
o.orderid, o.orderdate,
c.customerid, nomcliente=c.companyname,
e.employeeid, e.lastname, e.firstname,
s.shipperid, nomcomenvio=s.companyname
FROM orders o
INNER JOIN employees e ON o.employeeid=e.employeeid
INNER JOIN customers c ON o.customerid=c.customerid
INNER JOIN shippers s ON o.shipvia=s.shipperid
GO
CREATE VIEW vw_orderdetails AS
SELECT d.orderid, d.productid, d.quantity, d.unitprice, d.discount,
o.orderdate, o.shipperid, o.nomcomenvio, o.customerid, o.nomcliente, o.lastname, o.firstname,
p.productname, p.categoryid, p.categoryname, p.supplierid, p.companyname
FROM [order details] d
INNER JOIN vw_orders o ON o.orderid=d.orderid
INNER JOIN vw_products p ON d.productid=p.productid
GO
-- VISTAS SUPLEMENTARIAS
CREATE VIEW vw_territories AS 
SELECT t.TerritoryID, t.RegionID, t.TerritoryDescription, r.RegionDescription
FROM territories t
INNER JOIN region r ON t.regionid=r.regionid
GO
CREATE VIEW vw_employeeterritories AS
SELECT e.EmployeeID, e.FirstName, e.LastName, e.Region, e.Country,
et.TerritoryID
FROM EmployeeTerritories et
INNER JOIN employees e ON e.EmployeeID=et.EmployeeID
INNER JOIN vw_territories t ON t.TerritoryID=et.TerritoryID