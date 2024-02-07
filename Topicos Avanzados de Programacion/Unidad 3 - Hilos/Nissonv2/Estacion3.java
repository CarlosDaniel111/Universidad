package nissonv2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V2
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Estacion3 extends Thread {

	private static Semaforo sRobots = null;
	private Semaforo sEntrada, sSalida;
	private Estacion4 estacionSiguiente;
	private Vista vista;
	private int linea;

	public Estacion3(int linea, Semaforo sEntrada, Vista vista) {
		this.linea = linea;
		this.sEntrada = sEntrada;
		this.vista = vista;
		if (sRobots == null) {
			sRobots = new Semaforo(3);
		}
		sSalida = new Semaforo(0);
		estacionSiguiente = new Estacion4(linea, sSalida, vista);
		estacionSiguiente.start();
	}

	public void run() {
		while (true) {
			sEntrada.Espera();

			sRobots.Espera();
			System.out.println("Linea #" + (linea+1) + ", Robot trabajando en la E3 ");
			vista.trabajar(linea, 3, true);
			dormir(1000);
			vista.trabajar(linea, 3, false);
			sRobots.Libera();

			sSalida.Libera();
		}
	}

	private void dormir(int t) {
		try {
			sleep(t);
		} catch (Exception e) {
		}
	}
}
