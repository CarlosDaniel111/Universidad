package nissonv2;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V2
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class Estacion2 extends Thread {

	private static Semaforo sMotor = null, sTransmision;
	private Semaforo sEntrada, sSalida;
	private Estacion3 estacionSiguiente;
	private Vista vista;
	private int linea;

	public Estacion2(int linea, Semaforo sEntrada, Vista vista) {
		this.linea = linea;
		this.sEntrada = sEntrada;

		this.vista = vista;
		if (sMotor == null) {
			sMotor = new Semaforo(4);
			sTransmision = new Semaforo(2);
		}

		sSalida = new Semaforo(0);
		estacionSiguiente = new Estacion3(linea, sSalida, vista);
		estacionSiguiente.start();

	}

	public void run() {
		while (true) {

			sEntrada.Espera();

			sMotor.Espera();
			System.out.println("Linea #" + (linea+1) + ", Robot trabajando en la E2");
			vista.trabajar(linea, 1, true);
			dormir(600);
			vista.trabajar(linea, 1, false);
			sTransmision.Espera();
			sMotor.Libera();

			System.out.println("Linea #" + (linea+1) + ", Robot trabajando en la E2 transmision ");
			vista.trabajar(linea, 2, true);
			dormir(400);
			vista.trabajar(linea, 2, false);
			sTransmision.Libera();

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