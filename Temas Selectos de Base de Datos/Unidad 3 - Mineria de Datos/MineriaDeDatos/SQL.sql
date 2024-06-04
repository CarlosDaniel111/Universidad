CREATE DATABASE MineriaDeDatos
go
USE MineriaDeDatos
go
IF EXISTS (SELECT object_id FROM sys.tables WHERE name = 'creditos')
DROP TABLE creditos
GO
CREATE TABLE creditos (
    ID INT PRIMARY KEY IDENTITY,
	NivelRenta VARCHAR(5) NOT NULL,
	Patrimonio VARCHAR(5) NOT NULL,
	TamanoCredito INT NOT NULL,
	EdadPeticionario INT NOT NULL,
	Hijos INT NOT NULL,
	Funcionario VARCHAR(2) NOT NULL,
	NivelEstudios VARCHAR(12) NOT NULL,
	Autorizo VARCHAR(2) NOT NULL
)
select * from creditos

SELECT nivelRenta,patrimonio,tamanoCredito,edadPeticionario,numeroHijos,funcionario,nivelEstudios,autorizo FROM creditos