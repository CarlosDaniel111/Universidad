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

public class AppExamen3 {

	public static void main(String[] args) {
		Random rand = new Random();
		int filas = 10 + rand.nextInt(6) * 2;
		int colum = rand.nextInt(20, 51);

		Butaca[][] butacas = new Butaca[filas][colum];
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < colum; j++) {
				butacas[i][j] = new Butaca();
			}
		}
		Semaforo sButacas = new Semaforo(filas * colum);
		Lado ladoIzq = new Lado();
		Lado ladoDer = new Lado();

		TorniqueteEntrada tornEntradaIzq = new TorniqueteEntrada(sButacas, ladoIzq);
		tornEntradaIzq.setName("Torniquete entrada izquierdo");
		TorniqueteEntrada tornEntradaDer = new TorniqueteEntrada(sButacas, ladoDer);
		tornEntradaDer.setName("Torniquete entrada derecho");

		Acomodador acomodador = new Acomodador(butacas, ladoIzq, ladoDer);

		TorniqueteSalida tornSalidaIzq = new TorniqueteSalida(sButacas, butacas);
		tornSalidaIzq.setName("Torniquete salida izquierdo");
		TorniqueteSalida tornSalidaDer = new TorniqueteSalida(sButacas, butacas);
		tornSalidaDer.setName("Torniquete salida derecho");

		tornEntradaIzq.start();
		tornEntradaDer.start();
		acomodador.start();
		tornSalidaIzq.start();
		tornSalidaDer.start();
	}

}
