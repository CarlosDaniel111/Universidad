package nissonv2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V2
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class AppNissonV2 {

	public static void main(String[] args) {
		int lineas = Rutinas.nextInt(8,15);
		Vista vista = new Vista(lineas);
		Estacion1[] estaciones = new Estacion1[lineas];
		for(int i=0;i<lineas;i++)
			estaciones[i] = new Estacion1(i,vista);
		
		for(int i=0;i<lineas;i++)
			estaciones[i].start();
	}
}
