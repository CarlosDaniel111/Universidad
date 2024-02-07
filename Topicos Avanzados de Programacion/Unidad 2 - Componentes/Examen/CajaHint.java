package examen2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * EXAMEN COMPONENTES
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CajaHint extends JPanel implements ComponentListener, KeyListener, FocusListener {
	private String hint;
	private String[] palabras;
	private JTextField campo;
	private JLabel etiqueta;
	private boolean modificado;

	public CajaHint(String hint, String[] palabras) {
		this.hint = hint;
		this.palabras = palabras;
		modificado = false;

		HazInterfaz();
		HazEscuchas();
	}

	private void HazInterfaz() {
		setLayout(null);

		campo = new JTextField(hint);
		campo.requestFocus();
		campo.selectAll();

		etiqueta = new JLabel();

		add(campo);
		add(etiqueta);
	}

	private void HazEscuchas() {
		addComponentListener(this);
		campo.addKeyListener(this);
		campo.addFocusListener(this);
	}

	public String getContenido() {
		String ret = "";
		if (modificado) {
			ret = campo.getText();
		}
		return ret;
	}

	private void acomodarElementos() {
		int w = this.getWidth();
		int h = this.getHeight();

		if (modificado) {
			etiqueta.setText(hint);
			etiqueta.setBounds((int) (w * 0.1), (int) (h * 0.1), (int) (w * 0.8), (int) (h * 0.2));
			campo.setBounds((int) (w * 0.1), (int) (h * 0.3), (int) (w * 0.8), (int) (h * 0.6));
			return;
		}
		etiqueta.setText("");
		campo.setBounds((int) (w * 0.1), (int) (h * 0.1), (int) (w * 0.8), (int) (h * 0.8));
		campo.setText(hint);
		campo.selectAll();
	}

	private ImageIcon AjustarImagen(String ico, int Ancho, int Alto) {
		ImageIcon tmpIconAux = new ImageIcon(ico);
		// Escalar Imagen
		ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(Ancho, Alto, Image.SCALE_SMOOTH));// SCALE_DEFAULT
		return tmpIcon;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		acomodarElementos();
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		modificado = (campo.getText().length() != 0);
		acomodarElementos();

		for (int i = 0; i < palabras.length; i++) {
			if (campo.getText().compareToIgnoreCase(palabras[i]) == 0) {
				etiqueta.setIcon(
						AjustarImagen(palabras[i] + ".jpg", (int) (etiqueta.getWidth() * 0.25), etiqueta.getHeight()));
				return;
			}
		}

		etiqueta.setIcon(null);
	}

	@Override
	public void focusGained(FocusEvent e) {
		campo.selectAll();

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}
