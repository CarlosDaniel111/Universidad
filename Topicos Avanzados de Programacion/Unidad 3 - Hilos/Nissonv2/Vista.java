package nissonv2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V2
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Vista extends JFrame {

	private ImageIcon[] imagenes;
	private ImageIcon robot;
	private JLabel[] nAutos;
	private JPanel[][] trabajo;
	private JLabel[] lblRobots;
	private int lineas;

	public Vista(int lineas) {
		super("NISSON V2");
		this.lineas = lineas;
		ajustarImagenes();
		HazInterfaz();
	}

	private void HazInterfaz() {
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		// Encabezado
		JPanel panelNorte = new JPanel();
		panelNorte.setLayout(new GridLayout(0, 8));
		panelNorte.add(new JLabel("", JLabel.CENTER));
		panelNorte.add(new JLabel("Estación 1", JLabel.CENTER));
		panelNorte.add(new JLabel("Estación 2", JLabel.CENTER));
		panelNorte.add(new JLabel("", JLabel.CENTER));
		panelNorte.add(new JLabel("Estación 3", JLabel.CENTER));
		panelNorte.add(new JLabel("Estación 4", JLabel.CENTER));
		panelNorte.add(new JLabel("Estación 5", JLabel.CENTER));
		panelNorte.add(new JLabel("Estación 6", JLabel.CENTER));
		add(panelNorte, BorderLayout.NORTH);

		// Etiquetas de auto
		nAutos = new JLabel[lineas];
		for (int i = 0; i < lineas; i++)
			nAutos[i] = new JLabel("");

		trabajo = new JPanel[lineas][7];
		lblRobots = new JLabel[lineas];

		// Panel central
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(0, 8));
		for (int i = 0; i < lineas; i++) {
			for (int j = 0; j < 8; j++) {
				if (j == 0) {
					Box caja = Box.createVerticalBox();
					caja.add(new JLabel("Linea #" + (i + 1)));
					caja.add(nAutos[i]);
					panelCentral.add(caja);
					continue;
				}
				trabajo[i][j - 1] = new JPanel();
				trabajo[i][j - 1].setVisible(false);
				trabajo[i][j - 1].setLayout(new GridLayout(0, 2));
				trabajo[i][j - 1].setBorder(new LineBorder(Color.BLACK, 2));
				trabajo[i][j - 1].add(new JLabel(imagenes[j - 1]));
				if (j == 1) {
					JPanel caja = new JPanel();
					caja.setLayout(new BorderLayout());
					caja.add(new JLabel(robot));
					lblRobots[i] = new JLabel("", JLabel.CENTER);
					caja.add(lblRobots[i], BorderLayout.SOUTH);
					trabajo[i][j - 1].add(caja);
				} else {
					trabajo[i][j - 1].add(new JLabel(robot));
				}
				panelCentral.add(trabajo[i][j - 1]);
			}
		}
		add(panelCentral);

		setVisible(true);
	}

	public void trabajar(int linea, int estacion, boolean b) {
		trabajo[linea][estacion].setVisible(b);
	}

	public void autoTrabajando(int linea, int auto) {
		nAutos[linea].setText("Auto #" + auto);
	}

	public void setRobot(int linea, String text) {
		lblRobots[linea].setText(text);
	}

	private void ajustarImagenes() {
		imagenes = new ImageIcon[7];
		int aux = 35 + (15 - lineas) * 5;
		imagenes[0] = Rutinas.AjustarImagen("nissonImg/chasis.jpg", aux, aux);
		imagenes[1] = Rutinas.AjustarImagen("nissonImg/motor.jpg", aux, aux);
		imagenes[2] = Rutinas.AjustarImagen("nissonImg/transmision.png", aux, aux);
		imagenes[3] = Rutinas.AjustarImagen("nissonImg/carroceria.jpg", aux, aux);
		imagenes[4] = Rutinas.AjustarImagen("nissonImg/interiores.jpg", aux, aux);
		imagenes[5] = Rutinas.AjustarImagen("nissonImg/llantas.jpg", aux, aux);
		imagenes[6] = Rutinas.AjustarImagen("nissonImg/pruebas.jpg", aux, aux);
		robot = Rutinas.AjustarImagen("nissonImg/robot.jpg", aux, aux);
	}
}
