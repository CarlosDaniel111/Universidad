import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ConexionBD {
    private Connection connection;
    public boolean conectarBD(String servidor, String bd, String usuario, String password){
        String connectionUrl =
                "jdbc:sqlserver://"+servidor+";"
                        + "database="+ bd +";"
                        + "user="+usuario+";"
                        + "password="+password+";"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;";

        try {
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Base de datos conectada correctamente");
            return true;
        }
        catch (SQLException e) {
            System.out.println("Error al conectar a la bd");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Vector<String> getFamilias(){
        Vector<String> familias = new Vector<>();
        try {
            String query = "SELECT * FROM Familias";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                familias.add(rs.getString("famnombre"));
            }
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return familias;
    }

    public Vector<Articulo> getArticulos(Articulo articulo){
        Vector<Articulo> articulos = new Vector<>();
        String query = "SELECT * FROM Articulos A INNER JOIN Familias F ON F.famid = A.famid";
        if(!articulo.getId().isEmpty() || !articulo.getNombre().isEmpty() || !articulo.getDescripcion().isEmpty()
                || !articulo.getPrecio().isEmpty() || !articulo.getFamid().isEmpty()){
            query+=" WHERE";
            boolean primero = true;
            if(!articulo.getId().isEmpty()){
                query += (primero ? "" : " AND") + " artid = "+articulo.getId();
                primero = false;
            }
            if(!articulo.getNombre().isEmpty()){
                query+= (primero ? "" : " AND")+" artnombre LIKE '%"+articulo.getNombre()+"%'";
                primero = false;
            }
            if(!articulo.getDescripcion().isEmpty()){
                query+= (primero ? "" : " AND")+" artdescripcion LIKE '%"+articulo.getDescripcion()+"%'";
                primero = false;
            }
            if(!articulo.getPrecio().isEmpty()){
                query+= (primero ? "" : " AND")+" artprecio LIKE '%"+articulo.getPrecio()+"%'";
                primero = false;
            }
            if(!articulo.getFamid().isEmpty()){
                query+= (primero ? "" : " AND")+" A.famid = "+articulo.getFamid();
                primero = false;
            }
        }
        try {

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                articulos.add(new Articulo(rs.getString("artid"),rs.getString("artnombre"),
                        rs.getString("artdescripcion"),rs.getString("artprecio"),
                        rs.getString(5),rs.getString("famnombre")));
            }
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return articulos;
    }
}
