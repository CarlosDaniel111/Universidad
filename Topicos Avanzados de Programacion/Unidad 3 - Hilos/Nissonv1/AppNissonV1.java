package nissonv1;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V1
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class AppNissonV1 {

	public static void main(String[] args) {
		int lineas = Rutinas.nextInt(8,15);
		Vista vista = new Vista(lineas);
		Linea [] arreglo = new Linea[lineas];
		for(int i=0 ; i<arreglo.length ; i++) {
			arreglo[i]=new Linea(i,vista);
		}
		for(int i=0 ; i<arreglo.length ; i++)
			arreglo[i].start();
	}

}
