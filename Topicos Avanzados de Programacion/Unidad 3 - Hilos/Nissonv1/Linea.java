package nissonv1;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V1
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

class Linea extends Thread {
	private static int noCarros;
	private static Semaforo[] semRobots;
	private static Semaforo s, sCola;
	private static ColaCircular<String> cola;
	private Vista vista;
	private int id;

	public Linea(int id, Vista vista) {
		this.id = id;
		this.vista = vista;
		noCarros = 0;
		if (semRobots == null) {
			semRobots = new Semaforo[7];
			semRobots[0] = new Semaforo(5);
			semRobots[1] = new Semaforo(4); // motor
			semRobots[2] = new Semaforo(2);
			semRobots[3] = new Semaforo(3);
			semRobots[4] = new Semaforo(3);
			semRobots[5] = new Semaforo(2);
			semRobots[6] = new Semaforo(5);
			s = new Semaforo(1);
			sCola = new Semaforo(1);
			cola = new ColaCircular<String>(5);
			for (int i = 1; i <= 5; i++) {
				cola.Insertar("Robot " + i);
			}
		}
	}

	public void run() {

		while (true) {
			s.Espera();
			if (noCarros == 100) {
				s.Libera();
				break;
			}
			noCarros++;
			System.out.println("Producción del carro # " + noCarros);
			vista.autoTrabajando(id, noCarros);
			s.Libera();

			semRobots[0].Espera();
			sCola.Espera();
			cola.Retirar();
			String robot = cola.getDr();
			sCola.Libera();
			vista.setRobot(id, robot);
			System.out.println("Linea #" + (id+1) + ", " + robot + " trabajando en la E1");
			vista.trabajar(id, 0, true);
			dormir(2000);
			vista.trabajar(id, 0, false);
			sCola.Espera();
			cola.Insertar(robot);
			sCola.Libera();
			semRobots[0].Libera();

			// estacion 2
			semRobots[1].Espera();
			System.out.println("Linea #" + (id+1) + ", Robot trabajando en la E2");
			vista.trabajar(id, 1, true);
			dormir(600);
			vista.trabajar(id, 1, false);
			semRobots[2].Espera();

			semRobots[1].Libera();
			System.out.println("Linea #" + (id+1) + ", Robot trabajando en la E2 transmision ");
			vista.trabajar(id, 2, true);
			dormir(400);
			vista.trabajar(id, 2, false);
			semRobots[2].Libera();

			// estacion #3
			semRobots[3].Espera();
			System.out.println("Linea #" + (id+1) + ", Robot trabajando en la E3 ");
			vista.trabajar(id, 3, true);
			dormir(1000);
			vista.trabajar(id, 3, false);
			semRobots[3].Libera();

			// estacion #4
			semRobots[4].Espera();
			System.out.println("Linea #" + (id+1) + ", Robot trabajando en la E4 ");
			vista.trabajar(id, 4, true);
			dormir(500);
			vista.trabajar(id, 4, false);
			semRobots[4].Libera();

			// estacion #5
			semRobots[5].Espera();
			System.out.println("Linea #" + (id+1) + ", Robot trabajando en la E5 ");
			vista.trabajar(id, 5, true);
			dormir(500);
			vista.trabajar(id, 5, false);
			semRobots[5].Libera();

			// estacion #6
			semRobots[6].Espera();
			System.out.println("Linea #" + (id+1) + ", Robot trabajando en la E6 ");
			vista.trabajar(id, 6, true);
			dormir(1000);
			vista.trabajar(id, 6, false);
			semRobots[6].Libera();
		}
	}

	private void dormir(int t) {
		try {
			sleep(t);
		} catch (Exception e) {
		}
	}

}