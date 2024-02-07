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
import java.awt.*;
import java.awt.event.*;

public class AppDomino extends JFrame {
	private LogicaDomino logica;
	private PanelJugador[] panelesJugador;
	private PanelTablero tablero;

	public AppDomino() {
		super("Domino");
		panelesJugador = new PanelJugador[4];
		for (int i = 0; i < 4; i++) {
			panelesJugador[i] = new PanelJugador(i);
		}
		tablero = new PanelTablero();
		logica = new LogicaDomino(tablero, panelesJugador);
		HazInterfaz();
	}

	private void HazInterfaz() {
		setSize(1400, 850);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		// Panel central
		tablero.setBounds(350, 250, 700, 300);
		add(tablero);

		// Panel Jugador 1
		panelesJugador[0].setBounds(50, 50, 300, 200);
		add(panelesJugador[0]);

		// Panel Jugador 2
		panelesJugador[1].setBounds(1050, 50, 300, 200);
		add(panelesJugador[1]);

		// Panel Jugador 3
		panelesJugador[2].setBounds(1050, 550, 300, 200);
		add(panelesJugador[2]);

		// Panel Jugador 4
		panelesJugador[3].setBounds(50, 550, 300, 200);
		add(panelesJugador[3]);

		setVisible(true);
	}

	public static void main(String[] args) {
		new AppDomino();
	}
}
