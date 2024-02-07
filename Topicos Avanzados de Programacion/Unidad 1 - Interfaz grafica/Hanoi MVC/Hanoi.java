package hanoi;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * TORRES DE HANOI
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.util.ArrayList;

public class Hanoi {

	private ArrayList<Movimiento> movimientos;
	private Disco[] discos;
	private Movimiento movimientoActual;
	private int idxMov;
	private boolean bSubir, bDesp, bBajar;
	private final int LIMITE_Y = 230;
	private final int[] LIMITE_X = { 270, 600, 930 };
	private int[] topeTorre;

	public void comenzar(int cantidadDiscos) {
		movimientos = new ArrayList<>();
		hanoi(cantidadDiscos, 'A', 'C', 'B');
		discos = new Disco[cantidadDiscos];
		for (int i = 0; i < cantidadDiscos; i++) {
			discos[i] = new Disco(235 - i * 25, 670 - (cantidadDiscos - i) * 35, 70 + i * 50, 35);
		}
		topeTorre = new int[3];
		topeTorre[0] = 635 - cantidadDiscos * 35;
		topeTorre[1] = 635;
		topeTorre[2] = 635;
		idxMov = 0;
	}

	private void hanoi(int disco, char fuente, char destino, char aux) {
		if (disco == 0) {
			return;
		}
		hanoi(disco - 1, fuente, aux, destino);
		movimientos.add(new Movimiento(disco, fuente, destino));
		hanoi(disco - 1, aux, destino, fuente);
	}

	public boolean mover() {
		int discoActual = movimientoActual.getDisco() - 1;
		if (bSubir) {
			discos[discoActual].setY(discos[discoActual].getY() - 20);
			if (discos[discoActual].getY() <= LIMITE_Y) {
				discos[discoActual].setY(LIMITE_Y);
				bSubir = false;
			}
			return false;
		}
		int torreFuente = movimientoActual.getFuente() - 'A';
		int torreDestino = movimientoActual.getDestino() - 'A';
		if (bDesp) {
			if (torreFuente < torreDestino) {
				discos[discoActual].setX(discos[discoActual].getX() + 20);
				if (discos[discoActual].getX() + discos[discoActual].getWidth() / 2 >= LIMITE_X[torreDestino]) {
					discos[discoActual].setX(LIMITE_X[torreDestino] - discos[discoActual].getWidth() / 2);
					bDesp = false;
				}
			} else {
				discos[discoActual].setX(discos[discoActual].getX() - 20);
				if (discos[discoActual].getX() + discos[discoActual].getWidth() / 2 <= LIMITE_X[torreDestino]) {
					discos[discoActual].setX(LIMITE_X[torreDestino] - discos[discoActual].getWidth() / 2);
					bDesp = false;
				}
			}
			return false;
		}
		if (bBajar) {
			discos[discoActual].setY(discos[discoActual].getY() + 20);
			if (discos[discoActual].getY() >= topeTorre[torreDestino]) {
				discos[discoActual].setY(topeTorre[torreDestino]);
				topeTorre[torreDestino] -= 35;
				topeTorre[torreFuente] += 35;
				bBajar = false;
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean siguienteMovimiento() {
		if (idxMov == movimientos.size()) {
			return true;
		}
		movimientoActual = movimientos.get(idxMov++);
		bSubir = bDesp = bBajar = true;
		return false;
	}

	public ArrayList<Movimiento> getMovimientos() {
		return movimientos;
	}

	public Disco[] getDiscos() {
		return discos;
	}
}
