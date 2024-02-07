USE ventas
GO
ALTER PROC SP_Articulos
@artid INT OUTPUT,  @artnombre VARCHAR( 50 ),
@artdescripcion VARCHAR( 500 ),@artprecio numeric(12,2), @famid int AS
BEGIN 
	IF Exists(Select * From articulos WHERE artid = @artid)
	BEGIN
		UPDATE articulos Set artnombre = @artnombre, artdescripcion = @artdescripcion,
		artprecio = @artprecio, famid = @famid
		WHERE artid = @artid
		IF @@Error <>0
		BEGIN
			RaisError('Error al Actualizar en la tabla articulos',16,10)
		END
	END
	ELSE
	BEGIN
		-- SI LA LLAVE PRIMARIA NO ES IDENTITY , SE BUSCA LA ULTIMA CLAVE MAS UNO
		SELECT @artid = 1
		WHILE Exists(Select * From articulos WHERE artid = @artid)
		BEGIN
			SELECT @artid = @artid+1
		END
		INSERT articulos VALUES(@artid,@artnombre,@artdescripcion,@artprecio,@famid)
		IF @@Error <>0
		BEGIN
			RaisError('Error al Actualizar en la tabla articulos',16,10)
		END
	END
END
go
DECLARE @ArtId INT
SELECT @ArtId = 9
EXEC SP_Articulos @ArtId OUTPUT,'Cebolla','Cebolla cambray',20.50,2
SELECT ArtId = @ArtId

GO
CREATE TRIGGER TR_NOINSERTAR
ON Articulos 
FOR INSERT AS
BEGIN
	ROLLBACK TRAN 
	RAISERROR('No es posible insertar datos',16,10)
END
GO

drop trigger tr_noinsertar