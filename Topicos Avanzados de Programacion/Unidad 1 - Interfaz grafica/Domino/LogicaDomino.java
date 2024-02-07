package domino;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * DOMINO
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class LogicaDomino implements ActionListener {
	private Jugador[] jugadores;
	private PanelJugador[] panelesJugador;
	private PanelTablero tablero;
	private Ficha[] fichas;
	private int turnoActual, pasados;

	public LogicaDomino(PanelTablero tablero, PanelJugador[] panelesJugador) {
		this.panelesJugador = panelesJugador;
		this.tablero = tablero;

		jugadores = new Jugador[4];
		for (int i = 0; i < 4; i++) {
			jugadores[i] = new Jugador();
		}

		// Escuchas
		tablero.getBtnVoltear().addActionListener(this);
		tablero.getBtnMezclar().addActionListener(this);
		tablero.getBtnRepartir().addActionListener(this);

		fichas = tablero.getFichas();
		for (int i = 0; i < 28; i++) {
			fichas[i].addActionListener(this);
		}
		for (int i = 0; i < 4; i++) {
			panelesJugador[i].getBtnPasar().addActionListener(this);
		}
	}

	public void voltearFichas() {
		for (int i = 0; i < 28; i++) {
			fichas[i].voltear();
		}
		tablero.revalidate();
	}

	public void mezclarFichas() {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			int numeroRandom = random.nextInt(28);
			int numeroRandom2 = random.nextInt(28);
			Ficha tmp = fichas[numeroRandom2];
			fichas[numeroRandom2] = fichas[numeroRandom];
			fichas[numeroRandom] = tmp;
		}
		tablero.actualizarFichas();
	}

	public void repartirFichas() {
		if (fichas[0].getMostrar() == false) {
			voltearFichas();
		}
		for (int i = 0; i < 28; i++) {
			// Checar si es la mula de 6
			if (fichas[i].getValor1() == 6 && fichas[i].getValor2() == 6) {
				turnoActual = i % 4;
				fichas[i].setEnabled(true);
			}
			fichas[i].setDisabledIcon(null);
			jugadores[i % 4].darFicha(fichas[i]);
			panelesJugador[i % 4].getPanelFichas().add(fichas[i]);
		}
		panelesJugador[0].revalidate();
		panelesJugador[1].revalidate();
		panelesJugador[2].revalidate();
		panelesJugador[3].revalidate();
		panelesJugador[turnoActual].setBorder(new LineBorder(Color.BLACK, 2));
		tablero.empezarJuego();
	}

	public void ponerFicha(Ficha ficha) {
		// Actualiza Jugador y PanelJugador
		ArrayList<Ficha> fichasJugador = jugadores[turnoActual].getFichasJugador();
		fichasJugador.remove(ficha);
		panelesJugador[turnoActual].getPanelFichas().removeAll();
		for (int i = 0; i < fichasJugador.size(); i++) {
			fichasJugador.get(i).setEnabled(false);
			panelesJugador[turnoActual].getPanelFichas().add(fichasJugador.get(i));
		}
		panelesJugador[turnoActual].setBorder(null);
		panelesJugador[turnoActual].revalidate();
		panelesJugador[turnoActual].repaint();

		// Checa si gano
		if (fichasJugador.size() == 0) {
			JOptionPane.showMessageDialog(null, "Gano Jugador " + (turnoActual + 1));
			return;
		}

		pasados = 0;
		// Mandar ficha a panel
		tablero.ponerFicha(ficha);
		// Restar Puntos
		jugadores[turnoActual].quitarPuntos(ficha.getValor1() + ficha.getValor2());
		// Siguiente Turno
		siguienteTurno();
	}

	public void siguienteTurno() {
		turnoActual++;
		turnoActual %= 4;

		// Habilitar las fichas
		boolean pasar = true;
		ArrayList<Ficha> fichasJugador = jugadores[turnoActual].getFichasJugador();
		for (int i = 0; i < fichasJugador.size(); i++) {
			if (fichasJugador.get(i).getValor1() == tablero.getLado1()
					|| fichasJugador.get(i).getValor1() == tablero.getLado2()
					|| fichasJugador.get(i).getValor2() == tablero.getLado1()
					|| fichasJugador.get(i).getValor2() == tablero.getLado2()) {
				fichasJugador.get(i).setEnabled(true);
				pasar = false;
			}
		}
		panelesJugador[turnoActual].setBorder(new LineBorder(Color.BLACK, 2));
		panelesJugador[turnoActual].getBtnPasar().setEnabled(pasar);
	}

	public void pasar() {
		panelesJugador[turnoActual].setBorder(null);
		panelesJugador[turnoActual].getBtnPasar().setEnabled(false);
		pasados++;
		if (pasados >= 4) {
			empate();
			return;
		}
		siguienteTurno();
	}

	private void empate() {
		int ganador = 0;
		int menorPuntos = jugadores[0].getPuntos();
		for (int i = 1; i < 4; i++) {
			if (jugadores[i].getPuntos() < menorPuntos) {
				ganador = i;
				menorPuntos = jugadores[i].getPuntos();
			}
		}
		JOptionPane.showMessageDialog(null, "Gano Jugador " + (ganador + 1));
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// Evento de voltear
		if (evt.getSource() == tablero.getBtnVoltear()) {
			voltearFichas();
			return;
		}
		// Evento de mezclar
		if (evt.getSource() == tablero.getBtnMezclar()) {
			mezclarFichas();
			return;
		}
		// Evento de repartir
		if (evt.getSource() == tablero.getBtnRepartir()) {
			repartirFichas();
			return;
		}

		// Evento de poner una ficha
		if (evt.getSource() instanceof Ficha) {
			ponerFicha((Ficha) evt.getSource());
			return;
		}

		// Evento de pasar
		if (evt.getSource() == panelesJugador[turnoActual].getBtnPasar()) {
			pasar();
			return;
		}

	}
}
