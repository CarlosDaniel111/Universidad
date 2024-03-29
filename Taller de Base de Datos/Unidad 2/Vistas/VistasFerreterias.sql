USE FERRETERIAS
GO
CREATE VIEW VW_CLIENTES AS
SELECT C.CTEID,C.CTENOMBRE,C.CTEAPEPAT,C.CTEAPEMAT,C.CTEDOMICILIO,C.CTETELEFONO,
C.CTECELULAR,C.CTERFC,C.CTECURP,C.CTEFECHANACIMIENTO,C.CTESEXO,
CO.COLID,CO.COLNOMBRE,CO.COLCP,
M.MUNID,M.MUNNOMBRE
FROM CLIENTES C
INNER JOIN COLONIAS CO ON CO.COLID = C.COLID
INNER JOIN MUNICIPIOS M ON M.MUNID = CO.MUNID
GO
CREATE VIEW VW_EMPLEADOS AS
SELECT E.EMPID,E.EMPNOMBRE,E.EMPAPEPAT,E.EMPAPEMAT,E.EMPDOMICILIO,
E.EMPTELEFONO,E.EMPCELULAR,E.EMPRFC,E.EMPCURP,E.EMPFECHAINGRESO,E.EMPFECHANACIMIENTO,
E.JEFEID,NOMBREJEFE = J.EMPNOMBRE,APELLIDOJEFE=J.EMPAPEPAT,
Z.ZONAID,Z.ZONANOMBRE
FROM EMPLEADOS E
INNER JOIN EMPLEADOS J ON J.EMPID = E.JEFEID
INNER JOIN ZONAS Z ON Z.ZONAID = E.ZONAID
GO
CREATE VIEW VW_VENTAS AS
SELECT V.FOLIO,V.FECHA,C.*,F.*,E.*
FROM VENTAS V
INNER JOIN VW_CLIENTES C ON C.CTEID = V.CTEID
INNER JOIN FERRETERIAS F ON F.FERRID = V.FERRID
INNER JOIN VW_EMPLEADOS E ON E.EMPID = V.EMPID
GO
CREATE VIEW VW_ARTICULOS AS
SELECT A.ARTID,A.ARTNOMBRE,A.ARTDESCRIPCION,A.ARTPRECIO,
F.FAMID,F.FAMNOMBRE,F.FAMDESCRIPCION
FROM ARTICULOS A
INNER JOIN FAMILIAS F ON F.FAMID = A.FAMID
GO
CREATE VIEW VW_DETALLE AS
SELECT A.*,V.*,D.CANTIDAD,D.PRECIO
FROM DETALLE D
INNER JOIN VW_ARTICULOS A ON A.ARTID = D.ARTID
INNER JOIN VW_VENTAS V ON V.FOLIO = D.FOLIO
