package examen3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * EXAMEN UNIDAD 3 HILOS
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Butaca {
	private Semaforo s;
	private boolean ocupado;

	public Butaca() {
		s = new Semaforo(1);
		ocupado = false;
	}

	public Semaforo getS() {
		return s;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
}
