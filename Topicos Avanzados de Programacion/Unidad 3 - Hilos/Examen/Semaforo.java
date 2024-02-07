package examen3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * EXAMEN UNIDAD 3 HILOS
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Semaforo {
	private int recursos;

	public Semaforo(int recursos) {
		this.recursos = recursos;
	}

	public synchronized void Espera() {
		while (recursos < 1) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		recursos--;

	}

	public synchronized void Libera() {
		recursos++;
		notifyAll();
	}
}