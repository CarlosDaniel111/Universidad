package unidad3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * FILOSOFOS COMENSALES
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class Filosofo extends Thread {

	static private Semaforo[] s;
	static private Semaforo sVista;
	static private boolean[] bandPalillos = null;
	private Vista vista;
	private int id;

	public Filosofo(int id, Vista vista) {
		this.id = id;
		this.vista = vista;

		if (bandPalillos == null) {
			bandPalillos = new boolean[4];
			Arrays.fill(bandPalillos, false);
			sVista = new Semaforo(1);
			s = new Semaforo[4];
			for (int i = 0; i < 4; i++) {
				s[i] = new Semaforo(1);
			}
		}
	}

	public void run() {
		Random rand = new Random();
		while (true) {
			dormir(rand.nextInt(1000, 2000));

			// Pregunta por el palillo izquierdo
			sVista.Espera();
			vista.intentarPalillo(id, 0);
			sVista.Libera();
			System.out.println(getName() + ": intenta levantar el palillo izquierdo");
			dormir(rand.nextInt(500, 1000));

			s[id].Espera();
			if (bandPalillos[id]) {
				s[id].Libera();

				// Deja de preguntar
				sVista.Espera();
				vista.intentarPalillo(id, 0);
				sVista.Libera();
				System.out.println(getName() + ": no puede levantar el palillo izquierdo");
				continue;
			}
			bandPalillos[id] = true;
			// Pinta el palillo a verde
			sVista.Espera();
			vista.intentarPalillo(id, 0);
			vista.cambiarColorPalillo(id, id);
			sVista.Libera();
			System.out.println(getName() + ": levanto el palillo izquierdo");
			s[id].Libera();

			int idSig = (id + 1) % 4;

			// Pregunta por el palillo derecho
			sVista.Espera();
			vista.intentarPalillo(id, 1);
			sVista.Libera();
			System.out.println(getName() + ": intenta levantar el palillo derecho");
			dormir(rand.nextInt(500, 1000));

			s[idSig].Espera();
			if (bandPalillos[idSig]) {
				s[idSig].Libera();

				// Deja de preguntar por el palillo derecho
				sVista.Espera();
				vista.intentarPalillo(id, 1);
				sVista.Libera();
				System.out.println(getName() + ": no puede levantar el palillo derecho");

				// Deja el palillo izquierdo
				s[id].Espera();
				bandPalillos[id] = false;
				sVista.Espera();
				vista.cambiarColorPalillo(id, 4);
				sVista.Libera();
				System.out.println(getName() + ": deja el palillo izquierdo");
				s[id].Libera();
				continue;
			}

			bandPalillos[idSig] = true;
			// Pinta el palillo a verde
			sVista.Espera();
			vista.intentarPalillo(id, 1);
			vista.cambiarColorPalillo(idSig, id);
			sVista.Libera();
			System.out.println(getName() + ": levanto el palillo derecho");
			s[idSig].Libera();

			// COME
			System.out.println(getName() + ": se puso a comer");
			sVista.Espera();
			vista.cambiarColorPlato(id, Color.ORANGE);
			sVista.Libera();
			dormir(rand.nextInt(5000, 6000));

			// Deja de comer
			sVista.Espera();
			vista.cambiarColorPlato(id, Color.WHITE);
			sVista.Libera();

			// Deja el palillo izquierdo
			s[id].Espera();
			bandPalillos[id] = false;
			s[id].Libera();

			sVista.Espera();
			vista.cambiarColorPalillo(id, 4);
			sVista.Libera();
			System.out.println(getName() + ": deja el palillo izquierdo");

			// Deja el palillo derecho
			s[idSig].Espera();
			bandPalillos[idSig] = false;
			s[idSig].Libera();

			sVista.Espera();
			vista.cambiarColorPalillo(idSig, 4);
			sVista.Libera();
			System.out.println(getName() + ": deja el palillo derecho");
		}
	}

	private void dormir(int t) {
		try {
			sleep(t);
		} catch (Exception e) {
		}
	}
}
