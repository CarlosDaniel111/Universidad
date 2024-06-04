import java.sql.*;
import javax.swing.*;

public class BaseDeDatos {
    private Connection conexion;

    public BaseDeDatos(String servidor, String bd, String usuario, String contrasena) {
        String url = "jdbc:sqlserver://" + servidor + ":1433;database=" + bd
                + ";trustServerCertificate=true;loginTimeout=5";
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);

        } catch (SQLException e) {
            System.err.println("Error en la conexion");
        }

    }

    public void llenarCombo(JComboBox<String> combo, String campo) {
        try {
            combo.removeAllItems();
            combo.addItem("Seleccione");
            String query = "SELECT DISTINCT " + campo + " FROM TicketsH ORDER BY " + campo;
            PreparedStatement st = conexion.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getString(1));
            }
            st.close();
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    public boolean insertarRegistros(String archivo1, String archivo2) {
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
            tupla[1] = Rutinas.corregirFecha(tupla[1]);
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

    public boolean actualizarPrecio(String criterio, String id, int cantidad) {
        try {
            String query = "SELECT TICKET FROM TICKETSH " + criterio + " = ?";
            PreparedStatement st = conexion.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            conexion.setAutoCommit(false);
            String query2 = "UPDATE TICKETSD SET PRECIO = PRECIO + ? where TICKET = ?";
            PreparedStatement st2 = conexion.prepareStatement(query2);
            while (rs.next()) {
                st2.setInt(1, cantidad);
                st2.setString(2, rs.getString(1));
                st2.executeUpdate();
            }

            conexion.commit();
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                conexion.rollback();
                conexion.setAutoCommit(true);
            } catch (SQLException e2) {
                System.err.println(e.getMessage());
                return false;
            }
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

}
