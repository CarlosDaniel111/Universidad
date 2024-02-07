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

public class AppExamen2 extends JFrame implements ActionListener {

	private JButton btnGrabar;
	private CajaHint caja;

	public AppExamen2() {
		super("Examen Componentes");
		HazInterfaz();
		HazEscuchas();
	}

	private void HazInterfaz() {
		//setSize(300, 250);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		String[] arregloImagenes = { "perro", "elefante", "pato", "caballo" };

		caja = new CajaHint("Animal", arregloImagenes);
		add(caja);

		btnGrabar = new JButton("Grabar");
		add(btnGrabar, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void HazEscuchas() {
		btnGrabar.addActionListener(this);
	}

	public static void main(String[] args) {
		new AppExamen2();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnGrabar) {
			System.out.println(caja.getContenido());
			return;
		}

	}

}
