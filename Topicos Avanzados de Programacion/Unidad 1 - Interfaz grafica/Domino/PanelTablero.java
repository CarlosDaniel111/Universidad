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
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.ArrayDeque;

public class PanelTablero extends JPanel {
	private JPanel panelFichas;
	private JPanel panelJuego;
	private Ficha[] fichas;
	private JButton btnVoltear, btnMezclar, btnRepartir;
	private int lado1, lado2;
	private ArrayDeque<JLabel> fichasPuestas;

	public PanelTablero() {
		setLayout(null);

		// Panel para las fichas
		panelFichas = new JPanel();
		panelFichas.setLayout(new GridLayout(0, 10, 15, 10));
		panelFichas.setBounds(100, 0, 500, 200);

		// Se crean las 28 fichas
		final int valores[][] = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 }, { 0, 6 }, { 1, 1 },
				{ 1, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 }, { 1, 6 }, { 2, 2 }, { 2, 3 }, { 2, 4 }, { 2, 5 }, { 2, 6 },
				{ 3, 3 }, { 3, 4 }, { 3, 5 }, { 3, 6 }, { 4, 4 }, { 4, 5 }, { 4, 6 }, { 5, 5 }, { 5, 6 }, { 6, 6 } };
		fichas = new Ficha[28];
		for (int i = 0; i < 28; i++) {
			fichas[i] = new Ficha(valores[i][0], valores[i][1],(i+1) + ".png");
			panelFichas.add(fichas[i]);
		}

		// Boton de voltear
		btnVoltear = new JButton("Mostrar");
		btnVoltear.setBounds(450, 225, 100, 40);

		// Boton de Mezclar
		btnMezclar = new JButton("Mezclar");
		btnMezclar.setBounds(150, 225, 100, 40);

		// Boton de Repartir
		btnRepartir = new JButton("Repartir");
		btnRepartir.setBounds(300, 225, 100, 40);

		add(panelFichas);
		add(btnVoltear);
		add(btnMezclar);
		add(btnRepartir);
	}

	public void actualizarFichas() {
		panelFichas.removeAll();
		for (int i = 0; i < 28; i++) {
			panelFichas.add(fichas[i]);
		}
		revalidate();
	}

	public void empezarJuego() {
		removeAll();
		revalidate();
		repaint();
		setLayout(new BorderLayout());

		// Crea el panel del juego y pone el scroll
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridBagLayout());

		panelJuego.setBackground(new Color(21, 93, 84));
		JScrollPane scroll = new JScrollPane(panelJuego);
		add(scroll);

		// Inicializa la partida
		lado1 = -1;
		lado2 = -1;
		fichasPuestas = new ArrayDeque<>();

	}

	public void ponerFicha(Ficha ficha) {
		JLabel fichaPoner;
		boolean esMula = ficha.getValor1() == ficha.getValor2();
		// Checa si es la primera ficha
		if (lado1 == -1) {
			lado1 = 6;
			lado2 = 6;
			fichaPoner = new JLabel(Rutinas.rotarImagen(ficha.getImagen(), 0, 30, 60));
			fichasPuestas.addFirst(fichaPoner);
		} else if (lado1 == ficha.getValor1()) {
			// Checa si es mula
			if (esMula) {
				fichaPoner = new JLabel(Rutinas.rotarImagen(ficha.getImagen(), 0, 30, 60));
			} else {
				fichaPoner = new JLabel(Rutinas.rotarImagen(ficha.getImagen(), 90, 60, 30));
			}
			fichasPuestas.addFirst(fichaPoner);
			lado1 = ficha.getValor2();
		} else if (lado1 == ficha.getValor2()) {
			fichaPoner = new JLabel(Rutinas.rotarImagen(ficha.getImagen(), -90, 60, 30));
			fichasPuestas.addFirst(fichaPoner);
			lado1 = ficha.getValor1();
		} else if (lado2 == ficha.getValor1()) {
			// Checa si es mula
			if (esMula) {
				fichaPoner = new JLabel(Rutinas.rotarImagen(ficha.getImagen(), 0, 30, 60));
			} else {
				fichaPoner = new JLabel(Rutinas.rotarImagen(ficha.getImagen(), -90, 60, 30));
			}
			fichasPuestas.addLast(fichaPoner);
			lado2 = ficha.getValor2();
		} else {
			fichaPoner = new JLabel(Rutinas.rotarImagen(ficha.getImagen(), 90, 60, 30));
			fichasPuestas.addLast(fichaPoner);
			lado2 = ficha.getValor1();
		}

		getPanelJuego().removeAll();
		for (JLabel etiFicha : fichasPuestas) {
			panelJuego.add(etiFicha);
		}
		revalidate();
		repaint();
	}

	public Ficha[] getFichas() {
		return fichas;
	}

	public JButton getBtnVoltear() {
		return btnVoltear;
	}

	public JButton getBtnMezclar() {
		return btnMezclar;
	}

	public JButton getBtnRepartir() {
		return btnRepartir;
	}

	public int getLado1() {
		return lado1;
	}

	public int getLado2() {
		return lado2;
	}

	public JPanel getPanelJuego() {
		return panelJuego;
	}
}
