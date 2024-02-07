package domino;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * DOMINO
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.util.ArrayList;

public class Jugador {
	private ArrayList<Ficha> fichasJugador;
	private int puntos;

	public Jugador() {
		puntos = 0;
		fichasJugador = new ArrayList<>();
	}

	public void darFicha(Ficha ficha) {
		fichasJugador.add(ficha);
		puntos += ficha.getValor1();
		puntos += ficha.getValor2();
	}

	public void quitarPuntos(int menosPuntos) {
		puntos -= menosPuntos;
	}

	public ArrayList<Ficha> getFichasJugador() {
		return fichasJugador;
	}

	public int getPuntos() {
		return puntos;
	}
}
