package examen3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * EXAMEN UNIDAD 3 HILOS
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.util.Random;

public class TorniqueteSalida extends Thread {
	private Semaforo sButacas;
	private Butaca[][] butacas;

	public TorniqueteSalida(Semaforo sButacas, Butaca[][] butacas) {
		this.sButacas = sButacas;
		this.butacas = butacas;
	}

	public void run() {
		Random rand = new Random();
		while (true) {
			int fila = rand.nextInt(butacas.length);
			int colum = rand.nextInt(butacas[0].length);

			butacas[fila][colum].getS().Espera();
			if (!butacas[fila][colum].isOcupado()) {
				butacas[fila][colum].getS().Libera();
				continue;
			}
			butacas[fila][colum].setOcupado(false);
			butacas[fila][colum].getS().Libera();
			System.out.println("Sale la persona de la butaca de la fila: " + fila + " en la columna: " + colum + " por "
					+ getName());

			sButacas.Libera();

			try {
				sleep(rand.nextInt(2000, 5000));
			} catch (Exception e) {
			}
		}
	}
}
