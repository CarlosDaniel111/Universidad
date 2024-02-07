package domino;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * DOMINO
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;

public class Ficha extends JButton {
	private int valor1, valor2;
	private boolean mostrar;
	private String imagen;
	private ImageIcon imagenInicialIcon, imagenIcon;

	public Ficha(int valor1, int valor2, String imagen) {
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.imagen = imagen;
		imagenInicialIcon = Rutinas.AjustarImagen("0.png", 35,60);
		imagenIcon = Rutinas.AjustarImagen(imagen, 35,60);
		mostrar = false;
		setEnabled(false);
		setIcon(imagenInicialIcon);
		setDisabledIcon(this.getIcon());
	}

	public void voltear() {
		mostrar = !mostrar;
		setIcon(mostrar ? imagenIcon : imagenInicialIcon);
		setDisabledIcon(this.getIcon());
	}

	public boolean getMostrar() {
		return mostrar;
	}

	public int getValor1() {
		return valor1;
	}

	public int getValor2() {
		return valor2;
	}

	public String getImagen() {
		return imagen;
	}

}
