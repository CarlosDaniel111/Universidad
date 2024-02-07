package nissonv2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V2
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Estacion1 extends Thread {

	private static Semaforo sRobots = null, sCola, sAutos;
	private static ColaCircular<String> cola;
	private static int noCarros;
	private Estacion2 estacionSiguiente;
	private Semaforo sSalida;
	private Vista vista;
	private int linea;

	public Estacion1(int linea, Vista vista) {
		this.linea = linea;
		this.vista = vista;
		if (sRobots == null) {
			sRobots = new Semaforo(5);
			sCola = new Semaforo(1);
			sAutos = new Semaforo(1);
			cola = new ColaCircular<String>(5);
			for (int i = 1; i <= 5; i++) {
				cola.Insertar("Robot " + i);
			}
			noCarros = 0;
		}
		sSalida = new Semaforo(0);
		estacionSiguiente = new Estacion2(linea, sSalida, vista);
		estacionSiguiente.start();
	}

	public void run() {
		int auxCarros;
		while (true) {
			sAutos.Espera();
			if (noCarros == 1000) {
				sAutos.Libera();
				break;
			}
			noCarros++;
			System.out.println("Producción del carro # " + noCarros);
			auxCarros = noCarros;
			sAutos.Libera();

			sRobots.Espera();

			// Sacar de la cola
			sCola.Espera();
			cola.Retirar();
			String robot = cola.getDr();
			sCola.Libera();

			// Actualizar vista
			vista.setRobot(linea, robot);
			vista.autoTrabajando(linea, auxCarros);
			System.out.println("Linea #" + (linea+1) + ", " + robot + " trabajando en la E1");
			vista.trabajar(linea, 0, true);
			dormir(2000);
			vista.trabajar(linea, 0, false);

			// Meter a la cola
			sCola.Espera();
			cola.Insertar(robot);
			sCola.Libera();

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
