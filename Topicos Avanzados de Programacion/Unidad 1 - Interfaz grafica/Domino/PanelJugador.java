package domino;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * DOMINO
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class PanelJugador extends JPanel {
	private JButton btnPasar;
	private JPanel panelFichas;

	public PanelJugador(int id) {
		this.setLayout(new BorderLayout());

		// Etiqueta de jugador
		JLabel etiquetaJugador = new JLabel("Jugador " + (id + 1), JLabel.CENTER);
		add(etiquetaJugador, BorderLayout.NORTH);

		// Panel de fichas de jugador
		panelFichas = new JPanel();
		panelFichas.setLayout(new GridLayout(0, 7, 5, 10));
		panelFichas.setBorder(new EmptyBorder(47, 10, 47, 10));
		add(panelFichas);

		// Boton de pasar
		btnPasar = new JButton("Pasar");
		btnPasar.setEnabled(false);
		add(btnPasar, BorderLayout.SOUTH);
	}

	public JPanel getPanelFichas() {
		return panelFichas;
	}

	public JButton getBtnPasar() {
		return btnPasar;
	}

}
