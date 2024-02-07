package hanoi;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * TORRES DE HANOI
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Movimiento {
	private int disco;
	private char fuente, destino;

	public Movimiento(int disco, char fuente, char destino) {
		this.disco = disco;
		this.fuente = fuente;
		this.destino = destino;
	}

	public int getDisco() {
		return disco;
	}

	public char getFuente() {
		return fuente;
	}

	public char getDestino() {
		return destino;
	}
}
