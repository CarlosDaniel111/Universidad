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

public class TorniqueteEntrada extends Thread {
	private Semaforo sButacas;
	private Lado lado;

	public TorniqueteEntrada(Semaforo sButacas, Lado lado) {
		this.sButacas = sButacas;
		this.lado = lado;
	}

	public void run() {
		Random rand = new Random();
		while (true) {
			sButacas.Espera();
			System.out.println("Entra alguien por " + getName());
			lado.getS().Espera();
			lado.entrar();
			lado.getS().Libera();

			try {
				sleep(rand.nextInt(1000));
			} catch (Exception e) {
			}
		}
	}
}
