USE HOSPITALES2
GO
-- CONSULTAS
-- 1. NOMBRE DE LA ZONA Y TOTAL DE HOSPITALES DE LA ZONA.
SELECT Z.NOMBRE, TOTAL_HOSPITALES = COUNT(H.HOSPID)
FROM ZONAS Z
LEFT OUTER JOIN VW_HOSPITALES H ON H.ZONAID = Z.ZONAID
GROUP BY Z.NOMBRE
GO
-- 2. NOMBRE DEL CONSULTORIO Y TOTAL DE CITAS REALIZADAS.
SELECT C.CONSNOMBRE, TOTAL_CITAS = COUNT(CI.CITA)
FROM VW_CONSULTORIOS C
LEFT OUTER JOIN VW_CITAS CI ON CI.CONID = C.CONID 
GROUP BY C.CONSNOMBRE
GO
-- 3. A�O Y TOTAL DE CITAS REALIZADAS.
SELECT A�O = YEAR(FECHA), TOTAL_CITAS = COUNT(*)
FROM VW_CITAS
GROUP BY YEAR(FECHA)
ORDER BY YEAR(FECHA)
GO
-- 4. MES Y TOTAL DE CITAS REALIZADAS. MOSTRAR TODOS LOS MESES, SI NO TIENE CITAS, MOSTAR EN CERO.
CREATE VIEW VW_MESES AS
SELECT CLAVE = 1, NOMBRE = 'ENERO'
UNION
SELECT 2, NOMBRE = 'FEBRERO'
UNION
SELECT 3, NOMBRE = 'MARZO'
UNION
SELECT 4, NOMBRE = 'ABRIL'
UNION
SELECT 5, NOMBRE = 'MAYO'
UNION
SELECT 6, NOMBRE = 'JUNIO'
UNION
SELECT 7, NOMBRE = 'JULIO'
UNION
SELECT 8, NOMBRE = 'AGOSTO'
UNION
SELECT 9, NOMBRE = 'SEPTIEMBRE'
UNION
SELECT 10, NOMBRE = 'OCTUBRE'
UNION
SELECT 11, NOMBRE = 'NOVIEMBRE'
UNION
SELECT 12, NOMBRE = 'DICIEMBRE'
GO
SELECT M.NOMBRE, TOTAL_CITAS = COUNT(C.CITA)
FROM VW_CITAS C
RIGHT OUTER JOIN VW_MESES M ON M.CLAVE = MONTH(C.FECHA)
GROUP BY M.CLAVE,M.NOMBRE
ORDER BY M.CLAVE
GO
-- 5. NOMBRE DEL HOSPITAL Y TOTAL DE CONSULTORIOS QUE CONTIENE.
SELECT H.HOSPNOMBRE,TOTAL_CONSULTORIOS = COUNT(C.CONID)
FROM VW_HOSPITALES H
LEFT OUTER JOIN VW_CONSULTORIOS C ON C.HOSPID = H.HOSPID
GROUP BY H.HOSPNOMBRE
GO
-- 6. NOMBRE DEL CONSULTORIO Y PESO TOTAL DE LOS PACIENTES ATENDIDOS EN LAS CITAS.
SELECT C.CONSNOMBRE, PESO_TOTAL = ISNULL(SUM(PESO),0)
FROM VW_CONSULTORIOS C
LEFT OUTER JOIN VW_CITAS CI ON CI.CONID = C.CONID
GROUP BY C.CONSNOMBRE
GO
-- 7. NOMBRE DEL HOSPITAL Y TOTAL DE CITAS REALIZADAS POR MES DEL A�O 2020.
SELECT H.HOSPNOMBRE,
ENE = COUNT(CASE WHEN MONTH(C.FECHA) = 1 AND YEAR(FECHA) = 2020 THEN 1 END),
FEB = COUNT(CASE WHEN MONTH(C.FECHA) = 2 AND YEAR(FECHA) = 2020 THEN 1 END),
MAR = COUNT(CASE WHEN MONTH(C.FECHA) = 3 AND YEAR(FECHA) = 2020 THEN 1 END),
ABR = COUNT(CASE WHEN MONTH(C.FECHA) = 4 AND YEAR(FECHA) = 2020 THEN 1 END),
MAY = COUNT(CASE WHEN MONTH(C.FECHA) = 5 AND YEAR(FECHA) = 2020 THEN 1 END),
JUN = COUNT(CASE WHEN MONTH(C.FECHA) = 6 AND YEAR(FECHA) = 2020 THEN 1 END),
JUL = COUNT(CASE WHEN MONTH(C.FECHA) = 7 AND YEAR(FECHA) = 2020 THEN 1 END),
AGO = COUNT(CASE WHEN MONTH(C.FECHA) = 8 AND YEAR(FECHA) = 2020 THEN 1 END),
SEP = COUNT(CASE WHEN MONTH(C.FECHA) = 9 AND YEAR(FECHA) = 2020 THEN 1 END),
OCT = COUNT(CASE WHEN MONTH(C.FECHA) = 10 AND YEAR(FECHA) = 2020 THEN 1 END),
NOV = COUNT(CASE WHEN MONTH(C.FECHA) = 11 AND YEAR(FECHA) = 2020 THEN 1 END),
DIC = COUNT(CASE WHEN MONTH(C.FECHA) = 12 AND YEAR(FECHA) = 2020 THEN 1 END)
FROM VW_CITAS C
RIGHT OUTER JOIN VW_HOSPITALES H ON H.HOSPID = C.HOSPID
GROUP BY H.HOSPNOMBRE
GO
-- 8. A�O, Y TOTAL DE CITAS REALIZADAS POR DIA DE LA SEMANA.
SELECT A�O = YEAR(FECHA),
DOMINGO = COUNT(CASE WHEN DATEPART(DW,FECHA) = 1 THEN 1 END),
LUNES = COUNT(CASE WHEN DATEPART(DW,FECHA) = 2 THEN 1 END),
MARTES = COUNT(CASE WHEN DATEPART(DW,FECHA) = 3 THEN 1 END),
MIERCOLES = COUNT(CASE WHEN DATEPART(DW,FECHA) = 4 THEN 1 END),
JUEVES = COUNT(CASE WHEN DATEPART(DW,FECHA) = 5 THEN 1 END),
VIERNES = COUNT(CASE WHEN DATEPART(DW,FECHA) = 6 THEN 1 END),
SABADO = COUNT(CASE WHEN DATEPART(DW,FECHA) = 7 THEN 1 END)
FROM VW_CITAS
GROUP BY YEAR(FECHA)
GO
-- 9. A�O Y TOTAL DE CITAS POR ZONA.
SELECT A�O = YEAR(FECHA),
CENTRO = COUNT(CASE WHEN ZONAID = 1 THEN 1 END),
NORTE = COUNT(CASE WHEN ZONAID = 2 THEN 1 END),
SUR = COUNT(CASE WHEN ZONAID = 3 THEN 1 END)
FROM VW_CITAS
GROUP BY YEAR(FECHA)
GO
-- 10. NOMBRE DE LA ZONA, TOTAL DE HOSPITALES QUE EXISTEN, TOTAL DE CONSULTORIOS QUE EXISTEN EN LA ZONA.
SELECT Z.NOMBRE, 
TOTAL_HOSPITALES = COUNT(DISTINCT H.HOSPID), 
TOTAL_CONSULTORIOS = COUNT(DISTINCT C.CONID)
FROM ZONAS Z
LEFT OUTER JOIN VW_HOSPITALES H ON H.ZONAID = Z.ZONAID
LEFT OUTER JOIN VW_CONSULTORIOS C ON C.HOSPID = H.HOSPID
GROUP BY Z.NOMBRE