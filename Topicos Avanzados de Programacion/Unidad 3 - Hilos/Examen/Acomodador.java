package examen3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * EXAMEN UNIDAD 3 HILOS
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Acomodador extends Thread {
	private Butaca[][] butacas;
	private Lado ladoIzq, ladoDer;

	public Acomodador(Butaca[][] butacas, Lado ladoIzq, Lado ladoDer) {
		this.butacas = butacas;
		this.ladoIzq = ladoIzq;
		this.ladoDer = ladoDer;
	}

	public void run() {
		while (true) {
			// Intenta acomodar alguien de la izquierda
			ladoIzq.getS().Espera();
			if (ladoIzq.getEntrando() != 0) {
				// Acomoda
				if (!acomodarIzquierda()) {
					acomodarDerecha();
				}
				ladoIzq.acomodado();
			}
			ladoIzq.getS().Libera();

			// Intenta acomodar alguien de la derecha
			ladoDer.getS().Espera();
			if (ladoDer.getEntrando() != 0) {
				// Acomoda
				if (!acomodarDerecha()) {
					acomodarIzquierda();
				}
				ladoDer.acomodado();
			}
			ladoDer.getS().Libera();

		}
	}

	private boolean acomodarIzquierda() {
		for (int i = 0; i < butacas.length / 2; i++) {
			for (int j = 0; j < butacas[0].length; j++) {
				butacas[i][j].getS().Espera();
				if (!butacas[i][j].isOcupado()) {
					butacas[i][j].setOcupado(true);
					System.out.println("Persona acomodada en la butaca de la fila: " + i + " y la columna: " + j);
					butacas[i][j].getS().Libera();
					return true;
				}
				butacas[i][j].getS().Libera();
			}
		}
		return false;
	}

	private boolean acomodarDerecha() {
		for (int i = butacas.length / 2; i < butacas.length; i++) {
			for (int j = 0; j < butacas[0].length; j++) {
				butacas[i][j].getS().Espera();
				if (!butacas[i][j].isOcupado()) {
					butacas[i][j].setOcupado(true);
					System.out.println("Persona acomodada en la butaca de la fila: " + i + " y la columna: " + j);
					butacas[i][j].getS().Libera();
					return true;
				}
				butacas[i][j].getS().Libera();
			}
		}
		return false;
	}
}
