package nissonv2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V2
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Estacion6 extends Thread {

	private static Semaforo sRobots = null;
	private Semaforo sEntrada;
	private Vista vista;
	private int linea;

	public Estacion6(int linea, Semaforo sEntrada, Vista vista) {
		this.linea = linea;
		this.sEntrada = sEntrada;
		this.vista = vista;
		if (sRobots == null) {
			sRobots = new Semaforo(5);
		}
	}

	public void run() {
		while (true) {
			sEntrada.Espera();

			sRobots.Espera();
			System.out.println("Linea #" + (linea+1) + ", Robot trabajando en la E6 ");
			vista.trabajar(linea, 6, true);
			dormir(1000);
			vista.trabajar(linea, 6, false);

			sRobots.Libera();
		}
	}

	private void dormir(int t) {
		try {
			sleep(t);
		} catch (Exception e) {
		}
	}
}
