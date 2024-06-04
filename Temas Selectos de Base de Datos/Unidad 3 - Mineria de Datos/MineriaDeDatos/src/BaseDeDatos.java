import java.sql.*;

public class BaseDeDatos {
    private Connection conexion;

    public BaseDeDatos(String servidor, String bd, String usuario, String contrasena) {
        String url = "jdbc:sqlserver://" + servidor + ":1433;database=" + bd
                + ";trustServerCertificate=true;loginTimeout=5";
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexion establecida");
        } catch (SQLException e) {
            System.err.println("Error en la conexion");
        }
    }

    public boolean crearTabla() {
        String query = "IF EXISTS (SELECT object_id FROM sys.tables WHERE name = 'creditos')\n" +
                "DROP TABLE creditos\n" +
                "CREATE TABLE creditos (\n" +
                "    ID INT PRIMARY KEY IDENTITY,\n" +
                "\tnivelRenta VARCHAR(5) NOT NULL,\n" +
                "\tpatrimonio VARCHAR(5) NOT NULL,\n" +
                "\ttamanoCredito INT NOT NULL,\n" +
                "\tedadPeticionario INT NOT NULL,\n" +
                "\tnumeroHijos INT NOT NULL,\n" +
                "\tfuncionario VARCHAR(2) NOT NULL,\n" +
                "\tnivelEstudios VARCHAR(12) NOT NULL,\n" +
                "\tautorizo VARCHAR(2) NOT NULL\n" +
                ")";
        try {
            Statement st = conexion.createStatement();
            st.execute(query);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean insertarTuplas(int cantidad) {
        for(int j=0;j<=cantidad/1000;j++){
            String query = "INSERT INTO creditos VALUES ";
            int N = 1000;
            if(j == cantidad/1000) N = cantidad%1000;
            if(N == 0) break;
            for (int i = 0; i < N; i++) {
                Ejemplo ejemplo = Generador.generarEjemplo();
                query += "('" + ejemplo.getNivelRenta() + "','" + ejemplo.getPatrimonio() + "'," + ejemplo.getTamanoCredito() + "," + ejemplo.getEdad() + ","
                        + ejemplo.getHijos() + ",'" + ejemplo.getFuncionario() + "','" + ejemplo.getNivelEstudios() + "','" + ejemplo.getAutorizo() + "'),";
            }
            query = query.substring(0, query.length() - 1);
            try {
                Statement st = conexion.createStatement();
                st.execute(query);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        return true;
    }
}
