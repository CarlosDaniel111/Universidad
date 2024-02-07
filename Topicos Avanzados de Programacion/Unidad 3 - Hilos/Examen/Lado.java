package examen3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * EXAMEN UNIDAD 3 HILOS
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Lado {
	private Semaforo s;
	private int entrando;

	public Lado() {
		s = new Semaforo(1);
		entrando = 0;
	}

	public Semaforo getS() {
		return s;
	}

	public int getEntrando() {
		return entrando;
	}

	public void entrar() {
		entrando++;
	}

	public void acomodado() {
		entrando--;
	}

}
