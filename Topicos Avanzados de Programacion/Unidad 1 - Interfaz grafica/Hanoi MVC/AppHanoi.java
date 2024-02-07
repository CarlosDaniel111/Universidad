package hanoi;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * TORRES DE HANOI
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class AppHanoi {

	public static void main(String[] args) {
		Vista vista = new Vista();
		Hanoi modelo = new Hanoi();
		Controlador controlador = new Controlador(vista, modelo);
		vista.setEscuchador(controlador);
	}

}
