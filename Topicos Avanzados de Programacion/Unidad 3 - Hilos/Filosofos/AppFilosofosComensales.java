package unidad3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * FILOSOFOS COMENSALES
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class AppFilosofosComensales {

	public static void main(String[] args) {
		Vista vista = new Vista();
		Filosofo[] filosofos = new Filosofo[4];
		for (int i = 0; i < 4; i++) {
			filosofos[i] = new Filosofo(i, vista);
			filosofos[i].setName("Filosofo " + (i + 1));
		}
		for (int i = 0; i < 4; i++) {
			filosofos[i].start();
		}
	}

}
