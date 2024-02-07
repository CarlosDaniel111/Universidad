package unidad2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * COMPONENTES
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JCombosEMC extends JPanel implements ComponentListener, ItemListener {
	private Connection connection;
	private JComboBox<String> cmbEstados, cmbMunicipios, cmbCiudades;
	private JLabel lbEstados, lbMunicipios, lbCiudades;
	private ArrayList<String> estados, municipios, ciudades;

	public JCombosEMC() {
		estados = new ArrayList<>();
		municipios = new ArrayList<>();
		ciudades = new ArrayList<>();
		conectarBD();
		HazInterfaz();
		HazEscuchas();
		cargarEstados();
	}

	public JCombosEMC(String estado) {
		this();
		cmbEstados.setSelectedItem(estado);
	}

	public JCombosEMC(String estado, String municipio) {
		this(estado);
		cmbMunicipios.setSelectedItem(municipio);
	}

	public String getEstado() {
		String ret = "";
		if (cmbEstados.getSelectedIndex() != 0) {
			ret = (String) cmbEstados.getSelectedItem();
		}
		return ret;
	}

	public String getMunicipio() {
		String ret = "";
		if (cmbMunicipios.getSelectedIndex() != 0) {
			ret = (String) cmbMunicipios.getSelectedItem();
		}
		return ret;
	}

	public String getCiudad() {
		String ret = "";
		if (cmbCiudades.getSelectedIndex() != 0) {
			ret = (String) cmbCiudades.getSelectedItem();
		}
		return ret;
	}

	private void HazInterfaz() {
		setLayout(null);

		lbEstados = new JLabel("Estados");
		lbMunicipios = new JLabel("Municipios");
		lbCiudades = new JLabel("Ciudades");

		cmbEstados = new JComboBox<String>();
		cmbMunicipios = new JComboBox<String>();
		cmbCiudades = new JComboBox<String>();
	}

	private void HazEscuchas() {
		this.addComponentListener(this);
		cmbEstados.addItemListener(this);
		cmbMunicipios.addItemListener(this);
	}

	private void conectarBD() {
		try {
			String url = "jdbc:sqlserver://localhost:1433;databaseName=Empresa;integratedSecurity=true;"
					+ "encrypt=true;trustServerCertificate=true";
			connection = DriverManager.getConnection(url);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void cargarEstados() {
		try {
			String query = "SELECT * FROM Estados";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				estados.add(rs.getString("Nombre"));
			}
			st.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		cmbEstados.addItem("Seleccione");
		for (String estado : estados) {
			cmbEstados.addItem(estado);
		}
	}

	private void cargarMunicipios(String estado) {
		municipios.clear();
		try {
			String query = "SELECT M.Nombre FROM MUNICIPIOS M INNER JOIN ESTADOS E ON E.IdEstado = M.IdEstado WHERE E.Nombre = '"
					+ estado + "'";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				municipios.add(rs.getString("Nombre"));
			}
			st.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		cmbMunicipios.removeAllItems();
		cmbCiudades.removeAllItems();
		cmbMunicipios.addItem("Seleccione");
		for (String municipio : municipios) {
			cmbMunicipios.addItem(municipio);
		}
	}

	private void cargarCiudades(String estado, String municipio) {
		ciudades.clear();
		try {
			String query = "SELECT C.Nombre FROM CIUDADES C INNER JOIN MUNICIPIOS M ON M.IdMunicipio = C.IdMunicipio  INNER JOIN ESTADOS E ON E.IdEstado = C.IdEstado WHERE M.Nombre = '"
					+ municipio + "' AND E.Nombre = '" + estado + "'";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				ciudades.add(rs.getString("Nombre"));
			}
			st.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		cmbCiudades.removeAllItems();
		cmbCiudades.addItem("Seleccione");
		for (String ciudad : ciudades) {
			cmbCiudades.addItem(ciudad);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		int w = getWidth();
		int h = getHeight();

		lbEstados.setBounds((int) (w * 0.025), (int) (h * 0.02), (int) (w * 0.30), (int) (h * 0.05));
		lbMunicipios.setBounds((int) (w * 0.35), (int) (h * 0.02), (int) (w * 0.30), (int) (h * 0.05));
		lbCiudades.setBounds((int) (w * 0.675), (int) (h * 0.02), (int) (w * 0.30), (int) (h * 0.05));

		cmbEstados.setBounds((int) (w * 0.025), (int) (h * 0.09), (int) (w * 0.30), (int) (h * 0.1));
		cmbMunicipios.setBounds((int) (w * 0.35), (int) (h * 0.09), (int) (w * 0.30), (int) (h * 0.1));
		cmbCiudades.setBounds((int) (w * 0.675), (int) (h * 0.09), (int) (w * 0.30), (int) (h * 0.1));

		add(lbEstados);
		add(lbMunicipios);
		add(lbCiudades);

		add(cmbEstados);
		add(cmbMunicipios);
		add(cmbCiudades);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent evt) {
		if (evt.getStateChange() != ItemEvent.SELECTED)
			return;

		if (evt.getSource() == cmbEstados) {
			if (cmbEstados.getSelectedIndex() == 0) {
				cmbMunicipios.removeAllItems();
				cmbCiudades.removeAllItems();
				return;
			}
			cargarMunicipios(estados.get(cmbEstados.getSelectedIndex() - 1));
			return;
		}

		if (evt.getSource() == cmbMunicipios) {
			if (cmbMunicipios.getSelectedIndex() == 0) {
				cmbCiudades.removeAllItems();
				return;
			}
			cargarCiudades(estados.get(cmbEstados.getSelectedIndex() - 1),
					municipios.get(cmbMunicipios.getSelectedIndex() - 1));
			return;
		}

	}
}
