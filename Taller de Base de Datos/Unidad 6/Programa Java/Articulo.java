public class Articulo {
    private String id,nombre,descripcion,precio,famid,famnombre;

    public Articulo(String id, String nombre, String descripcion, String precio, String famid) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.famid = famid;
    }

    public Articulo(String id, String nombre, String descripcion, String precio, String famid,String famnombre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.famid = famid;
        this.famnombre = famnombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getFamid() {
        return famid;
    }

    public String getFamnombre() {
        return famnombre;
    }
}
