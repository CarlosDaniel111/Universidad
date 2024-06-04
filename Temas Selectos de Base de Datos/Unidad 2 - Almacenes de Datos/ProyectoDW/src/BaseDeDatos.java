import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
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

    public boolean insertarImportaciones(String archivo1,String archivo2){
        Extractor extractor1 = new Extractor(archivo1);
        extractor1.nextTupla();
        String query = "INSERT INTO Importaciones VALUES (?,?,?,?)";

        try {
            PreparedStatement st = conexion.prepareStatement(query);
            while (extractor1.quedanTuplas()) {
                String[] tupla = extractor1.nextTupla();
                if(tupla[1].equals("Exports")) continue;
                if(!tupla[3].equals("Mexico")) continue;
                tupla[5] = Rutinas.corregirFechaImportaciones(tupla[5]);
                double importe = Rutinas.convertirImporte(Double.parseDouble(tupla[9]),tupla[2]);
                tupla[2] = Rutinas.traducirPais(tupla[2]);
                tupla[7] = Rutinas.traducirTransporte(tupla[7]);
                st.setString(1,tupla[2]);
                st.setString(2,tupla[7]);
                st.setString(3,tupla[5]);
                st.setDouble(4,importe);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        Extractor extractor2 = new Extractor((archivo2));
        extractor2.nextTupla();
        try{
            PreparedStatement st = conexion.prepareStatement(query);
            while(extractor2.quedanTuplas()){
                String[] tupla = extractor2.nextTupla();
                tupla[5] = Rutinas.corregirFechaImportaciones(tupla[5]);
                String paisOrigen = Rutinas.obtenerPaisOrigen(tupla[2]);
                String medioTransporte = Rutinas.obtenerMedioTransporte(Integer.parseInt(tupla[4])+1,tupla[0]);
                double importe = Rutinas.convertirImporte(Double.parseDouble(tupla[6]),paisOrigen);
                st.setString(1,paisOrigen);
                st.setString(2,medioTransporte);
                st.setString(3,tupla[5]);
                st.setDouble(4,importe);
                st.executeUpdate();
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean insertarVentas(String archivo1, String archivo2) {
        Extractor extractorTicketH = new Extractor(archivo1);
        extractorTicketH.nextTupla();
        String query = "INSERT INTO TicketsH VALUES (?,?,?,?,?,?)";
        PreparedStatement st;
        try {
            st = conexion.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        while (extractorTicketH.quedanTuplas()) {
            String[] tupla = extractorTicketH.nextTupla();
            tupla[1] = Rutinas.corregirFechaVentas(tupla[1]);
            try {
                for (int i = 0; i < 6; i++)
                    st.setString(i + 1, tupla[i]);
                st.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }

        Extractor extractorTicketD = new Extractor(
                archivo2);
        extractorTicketD.nextTupla();

        query = "IF EXISTS(SELECT TICKET FROM TICKETSD WHERE TICKET = ? AND IDPRODUCTO = ?) " +
                "UPDATE TICKETSD SET UNIDADES = UNIDADES + ? WHERE TICKET = ? AND IDPRODUCTO = ? " +
                "ELSE " +
                "INSERT INTO TICKETSD VALUES(?,?,?,?)";
        try {
            st = conexion.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        while (extractorTicketD.quedanTuplas()) {
            String[] tupla = extractorTicketD.nextTupla();
            for (int i = 0; i < 5; i++) {
                tupla[i] = Rutinas.limpiarCampo(tupla[i]);
            }
            tupla[3] = String.valueOf(Integer.parseInt(tupla[4]) / Integer.parseInt(tupla[2]));
            try {
                st.setString(1, tupla[0]);
                st.setString(2, tupla[1]);
                st.setString(3, tupla[2]);
                st.setString(4, tupla[0]);
                st.setString(5, tupla[1]);
                for (int i = 0; i < 4; i++) {
                    st.setString(i + 6, tupla[i]);
                }
                st.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

}
