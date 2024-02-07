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
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class JCajaMultiple extends JPanel implements ComponentListener, ActionListener, KeyListener {

	private JRadioButton rdCorreo, rdRfc, rdTelefono;
	private ButtonGroup grupo;
	private JButton btnNuevo;
	private ArrayList<JTextField> listaCajas;
	private ArrayList<JButton> listaEliminar;

	private final String regexpCorreo = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
	private final String regexpRfc = "^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$";
	private final String regexpTel = "[0-9]{10}|[0-9]{2}\\s[0-9]{2}\\s[0-9]{2}\\s[0-9]{2}\\s[0-9]{2}";

	public JCajaMultiple() {
		HazInterfaz();
		HazEscuchas();
	}

	public String getElement(int id) {
		String text = "";
		if (id >= 0 && id < listaCajas.size()) {
			String aux = listaCajas.get(id).getText();
			// Verifica que cumpla la expresion regular
			if ((rdCorreo.isSelected() && Pattern.matches(regexpCorreo, aux))
					|| (rdRfc.isSelected() && Pattern.matches(regexpRfc, aux))
					|| (rdTelefono.isSelected() && Pattern.matches(regexpTel, aux))) {
				text = listaCajas.get(id).getText();
			}
		}
		return text;
	}

	public ArrayList<String> getElements() {
		ArrayList<String> elements = new ArrayList<>();
		for (int i = 0; i < listaCajas.size(); i++) {
			String text = listaCajas.get(i).getText();
			// Verifica que cumpla la expresion regular
			if ((rdCorreo.isSelected() && Pattern.matches(regexpCorreo, text))
					|| (rdRfc.isSelected() && Pattern.matches(regexpRfc, text))
					|| (rdTelefono.isSelected() && Pattern.matches(regexpTel, text))) {
				elements.add(text);
			}
		}
		return elements;
	}

	private void HazInterfaz() {
		setLayout(null);

		// RadioButtons
		rdCorreo = new JRadioButton("Correos");
		rdRfc = new JRadioButton("RFC");
		rdTelefono = new JRadioButton("Teléfonos");
		grupo = new ButtonGroup();
		grupo.add(rdCorreo);
		grupo.add(rdRfc);
		grupo.add(rdTelefono);

		// Boton
		btnNuevo = new JButton("Nueva caja");

		listaCajas = new ArrayList<>();
		listaEliminar = new ArrayList<>();
	}

	private void HazEscuchas() {
		this.addComponentListener(this);
		btnNuevo.addActionListener(this);
	}

	private void actualizarLista() {
		int w = getWidth();
		int h = getHeight();
		for (int i = 0; i < listaCajas.size(); i++) {
			listaCajas.get(i).setBounds((int) (w * 0.05), (int) (h * 0.21 + i * ((h * 0.075))), (int) (w * 0.5),
					(int) (h * 0.07));
			listaEliminar.get(i).setBounds((int) (w * 0.56), (int) (h * 0.21 + i * ((h * 0.075))), (int) (w * 0.1),
					(int) (h * 0.07));
			add(listaEliminar.get(i));
			add(listaCajas.get(i));
		}
		repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		int w = getWidth();
		int h = getHeight();

		rdCorreo.setBounds((int) (w * 0.05), (int) (h * 0.01), (int) (w * 0.25), (int) (h * 0.1));
		rdRfc.setBounds((int) (w * 0.30), (int) (h * 0.01), (int) (w * 0.25), (int) (h * 0.1));
		rdTelefono.setBounds((int) (w * 0.55), (int) (h * 0.01), (int) (w * 0.25), (int) (h * 0.1));
		btnNuevo.setBounds((int) (w * 0.05), (int) (h * 0.12), (int) (w * 0.30), (int) (h * 0.08));
		actualizarLista();

		add(rdCorreo);
		add(rdRfc);
		add(rdTelefono);
		add(btnNuevo);
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
	public void actionPerformed(ActionEvent evt) {
		// Añadir campo de texto
		if (evt.getSource() == btnNuevo) {
			// Checar si ya hay 10 cajas
			if (listaCajas.size() == 10) {
				JOptionPane.showMessageDialog(null, "No se puede añadir mas campos de texto");
				return;
			}

			// Checar si se selecciono un radiobutton
			if (grupo.getSelection() == null) {
				JOptionPane.showMessageDialog(null, "Debes seleccionar una opción");
				return;
			}

			// Añadir caja y boton
			listaCajas.add(new JTextField());
			listaEliminar.add(new JButton("X"));
			listaEliminar.get(listaEliminar.size() - 1).addActionListener(this);
			listaCajas.get(listaCajas.size() - 1).addKeyListener(this);
			actualizarLista();
			listaCajas.get(listaCajas.size() - 1).requestFocus();
			;

			// Desabilitar radiobuttons
			rdCorreo.setEnabled(false);
			rdRfc.setEnabled(false);
			rdTelefono.setEnabled(false);
			return;
		}

		// Elimnar campo de texto
		for (int i = 0; i < listaEliminar.size(); i++) {
			if (listaEliminar.get(i) == evt.getSource()) {
				remove(listaCajas.get(i));
				remove(listaEliminar.get(i));
				listaCajas.remove(i);
				listaEliminar.remove(i);
				actualizarLista();

				if (listaCajas.size() == 0) {
					rdCorreo.setEnabled(true);
					rdRfc.setEnabled(true);
					rdTelefono.setEnabled(true);
				}

				return;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent evt) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent evt) {
		JTextField txt = (JTextField) evt.getSource();
		if (rdCorreo.isSelected() && Pattern.matches(regexpCorreo, txt.getText())) {
			txt.setBorder(new LineBorder(Color.GREEN, 2));
			return;
		}
		if (rdRfc.isSelected() && Pattern.matches(regexpRfc, txt.getText())) {
			txt.setBorder(new LineBorder(Color.GREEN, 2));
			return;
		}
		if (rdTelefono.isSelected() && Pattern.matches(regexpTel, txt.getText())) {
			txt.setBorder(new LineBorder(Color.GREEN, 2));
			return;
		}
		txt.setBorder(new LineBorder(Color.RED, 2));
	}
}
