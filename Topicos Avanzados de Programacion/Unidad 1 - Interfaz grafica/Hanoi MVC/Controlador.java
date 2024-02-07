package hanoi;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * TORRES DE HANOI
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.awt.event.*;

public class Controlador implements ActionListener {

	private Vista vista;
	private Hanoi modelo;

	public Controlador(Vista vista, Hanoi modelo) {
		this.vista = vista;
		this.modelo = modelo;
	}

	private boolean validarTexto(String text) {
		try {
			int valorEntero = Integer.parseInt(text);
			if (valorEntero < 3) {
				vista.mensaje("Deben ser mas de 3 discos!");
				return false;
			}
			if (valorEntero > vista.LIMITE_DISCOS) {
				vista.mensaje("Solo se permite menos de " + vista.LIMITE_DISCOS + " discos!");
				return false;
			}
		} catch (NumberFormatException e) {
			vista.mensaje("Debes ingresar un numero!");
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == vista.getBtnEmpezar()) {
			if (validarTexto(vista.getDiscos())) {
				int valor = Integer.parseInt(vista.getDiscos());
				modelo.comenzar(valor);
				vista.actualizarDiscos(modelo.getDiscos());
				vista.getT().start();
				modelo.siguienteMovimiento();
			}
		}
		if (evt.getSource() == vista.getT()) {
			if (modelo.mover() && modelo.siguienteMovimiento()) {
				vista.getT().stop();
			}
			vista.actualizarDiscos(modelo.getDiscos());
		}

	}

}
