USE CONGRESOS
GO
CREATE VIEW VW_ESTUDIANTES AS
SELECT EST.ESTID,EST.ESTNOMBRE,EST.ESTAPELLIDOS,EST.ESTDOMICILIO,EST.ESTCORREO,EST.ESTCELULAR,
ESC.ESCID,ESC.ESCNOMBRE,ESC.ESCDOMICILIO,
M.MUNID,M.MUNNOMBRE
FROM ESTUDIANTES EST
INNER JOIN ESCUELAS ESC ON ESC.ESCID = EST.ESCID
INNER JOIN MUNICIPIOS M ON M.MUNID = ESC.MUNID
GO
CREATE VIEW VW_EVENTOS AS
SELECT E.EVEID,E.EVENOMBRE,E.EVEDESCRIPCION,E.EVEFECHA,E.EVELUGAR,E.EVECOSTO,
EX.EXPID,EX.EXPNOMBRE,EX.EXPAPELLIDOS,EX.EXPCORREO,EX.EXPCELULAR
FROM EVENTOS E
INNER JOIN EXPOSITORES EX ON EX.EXPID = E.EXPID
GO
CREATE VIEW VW_REGISTRO AS
SELECT R.FOLIO,R.FECHA,C.*,E.*
FROM REGISTRO R
INNER JOIN CONGRESOS C ON C.CONID = R.CONID
INNER JOIN VW_ESTUDIANTES E ON E.ESTID = R.ESTID
GO
CREATE VIEW VW_EVENTOSXREG AS
SELECT E.*,R.*
FROM EVENTOSXREG EXR
INNER JOIN VW_EVENTOS E ON E.EVEID = EXR.EVEID
INNER JOIN VW_REGISTRO R ON R.FOLIO = EXR.FOLIO