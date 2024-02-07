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

public class JMarcas extends JPanel implements ComponentListener, KeyListener, ActionListener {

	private JTextField txtBuscar;
	private JButton btnLimpiar;
	private ArrayList<String> marcas;
	private JPanel panelMarcas;
	private JScrollPane scroll;
	private ArrayList<JCheckBox> checks;

	public JMarcas() {
		marcas = new ArrayList<>();
		checks = new ArrayList<>();
		traerDatos();
		HazInterfaz();
		HazEscuchas();
	}

	public ArrayList<String> getMarcas() {
		ArrayList<String> marcados = new ArrayList<>();
		for (JCheckBox marca : checks) {
			if (marca.isSelected()) {
				marcados.add(marca.getText());
			}
		}
		return marcados;
	}

	private void traerDatos() {
		Connection connection = null;
		try {
			String url = "jdbc:sqlserver://localhost:1433;databaseName=Empresa;integratedSecurity=true;"
					+ "encrypt=true;trustServerCertificate=true";
			connection = DriverManager.getConnection(url);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		try {
			String query = "SELECT * FROM Marcas WHERE Vig = 'T' ORDER BY Marca";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				marcas.add(rs.getString("Marca"));
			}
			st.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void HazInterfaz() {
		setLayout(null);

		txtBuscar = new JTextField();

		for (String marca : marcas) {
			checks.add(new JCheckBox(marca));
		}

		panelMarcas = new JPanel();
		panelMarcas.setLayout(new GridLayout(0, 1));
		scroll = new JScrollPane(panelMarcas);
		actualizarLista("");

		btnLimpiar = new JButton("LIMPIAR");

	}

	private void HazEscuchas() {
		this.addComponentListener(this);
		txtBuscar.addKeyListener(this);
		btnLimpiar.addActionListener(this);
	}

	private void actualizarLista(String pat) {
		panelMarcas.removeAll();
		for (JCheckBox check : checks) {
			if (check.getText().toLowerCase().startsWith(pat.toLowerCase())) {
				panelMarcas.add(check);
			}
		}
		repaint();
		revalidate();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		int w = getWidth();
		int h = getHeight();

		txtBuscar.setBounds((int) (w * 0.02), (int) (h * 0.02), (int) (w * 0.6), (int) (h * 0.08));
		scroll.setBounds((int) (w * 0.02), (int) (h * 0.11), (int) (w * 0.97), (int) (h * 0.78));
		btnLimpiar.setBounds((int) (w * 0.02), (int) (h * 0.9), (int) (w * 0.4), (int) (h * 0.08));

		add(txtBuscar);
		add(scroll);
		add(btnLimpiar);
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
	public void keyTyped(KeyEvent evt) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		actualizarLista(txtBuscar.getText());
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnLimpiar) {
			for (JCheckBox check : checks) {
				check.setSelected(false);
			}
			txtBuscar.setText("");
			actualizarLista("");
			return;
		}

	}

}
