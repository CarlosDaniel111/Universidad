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

public class AppComponentes extends JFrame {

	public AppComponentes() {
		super("Componentes");
		HazInterfaz();
	}

	private void HazInterfaz() {
		setSize(1400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new GridLayout(0, 3));

		JCajaMultiple caja = new JCajaMultiple();
		add(caja);
		JMarcas marcas = new JMarcas();
		add(marcas);
		JCombosEMC combo = new JCombosEMC("Sinaloa", "Culiacán");
		add(combo);

		setVisible(true);
	}

	public static void main(String[] args) {
		new AppComponentes();

	}

}
