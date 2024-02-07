package unidad3;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * FILOSOFOS COMENSALES
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Vista extends JFrame {

	private Graphics g;
	private Image myImagen = null;
	private int[] palilloColor;
	private Color[] platoColor;
	private boolean[][] intento;
	private final int[][] coordXIntento = { { 205, 205 }, { 300, 475 }, { 568, 568 }, { 475, 300 } };
	private final int[][] coordYIntento = { { 315, 500 }, { 595, 595 }, { 500, 315 }, { 230, 230 } };
	private final Color[] coloresFilosofos = { Color.GREEN, Color.RED, Color.CYAN, Color.ORANGE, Color.BLACK };

	public Vista() {
		super("Filosofo Comensales");
		HazInterfaz();
		myImagen = createImage(getWidth(), getHeight());
		g = myImagen.getGraphics();
		palilloColor = new int[4];
		platoColor = new Color[4];
		intento = new boolean[4][2];
		Arrays.fill(palilloColor, 4);
		Arrays.fill(platoColor, Color.WHITE);
		repaint();
	}

	private void HazInterfaz() {
		setSize(800, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		setVisible(true);
	}

	public void paint(Graphics gl) {
		Dibuja();
		gl.drawImage(myImagen, 0, 0, getWidth(), getHeight(), this);
	}

	private void Dibuja() {
		if (g == null)
			return;
		super.paint(g);

		// Mesa
		g.setColor(new Color(199, 246, 212));
		g.fillRect(240, 240, 320, 320);
		g.setColor(Color.BLACK);
		g.drawRect(240, 240, 320, 320);

		// Sillas
		g.setColor(coloresFilosofos[0]);
		g.fillRect(170, 340, 50, 100);
		g.fillRect(170, 325, 90, 15);
		g.fillRect(170, 440, 90, 15);

		g.setColor(coloresFilosofos[1]);
		g.fillRect(350, 580, 100, 50);
		g.fillRect(335, 540, 15, 90);
		g.fillRect(450, 540, 15, 90);

		g.setColor(coloresFilosofos[2]);
		g.fillRect(580, 340, 50, 100);
		g.fillRect(540, 325, 90, 15);
		g.fillRect(540, 440, 90, 15);

		g.setColor(coloresFilosofos[3]);
		g.fillRect(350, 170, 100, 50);
		g.fillRect(335, 170, 15, 90);
		g.fillRect(450, 170, 15, 90);

		g.setColor(Color.BLACK);
		g.drawRect(170, 340, 50, 100);
		g.drawRect(170, 324, 90, 15);
		g.drawRect(170, 440, 90, 15);

		g.drawRect(350, 170, 100, 50);
		g.drawRect(335, 170, 15, 90);
		g.drawRect(450, 170, 15, 90);

		g.drawRect(580, 340, 50, 100);
		g.drawRect(540, 325, 90, 15);
		g.drawRect(540, 440, 90, 15);

		g.drawRect(350, 580, 100, 50);
		g.drawRect(335, 540, 15, 90);
		g.drawRect(450, 540, 15, 90);

		// Filosofos
		g.fillOval(190, 360, 60, 60);
		g.fillOval(370, 190, 60, 60);
		g.fillOval(545, 360, 60, 60);
		g.fillOval(370, 545, 60, 60);

		// Platos
		g.setColor(platoColor[0]);
		g.fillOval(260, 355, 75, 75);
		g.setColor(platoColor[1]);
		g.fillOval(363, 455, 75, 75);
		g.setColor(platoColor[2]);
		g.fillOval(460, 355, 75, 75);
		g.setColor(platoColor[3]);
		g.fillOval(363, 258, 75, 75);

		// Numeros
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("1", 285, 406);
		g.drawString("2", 389, 508);
		g.drawString("3", 488, 406);
		g.drawString("4", 389, 310);

		// Intento
		for (int i = 0; i < 4; i++) {
			if (intento[i][0])
				g.drawString("?", coordXIntento[i][0], coordYIntento[i][0]);
			if (intento[i][1])
				g.drawString("?", coordXIntento[i][1], coordYIntento[i][1]);
		}

		// Palillo 1
		g.setColor(coloresFilosofos[palilloColor[0]]);
		g.fillRect(288, 268, 20, 60);
		// Palillo 2
		g.setColor(coloresFilosofos[palilloColor[1]]);
		g.fillRect(288, 465, 20, 60);
		// Palillo 3
		g.setColor(coloresFilosofos[palilloColor[2]]);
		g.fillRect(488, 465, 20, 60);
		// Palillo 4
		g.setColor(coloresFilosofos[palilloColor[3]]);
		g.fillRect(488, 268, 20, 60);

	}

	public void cambiarColorPalillo(int id, int color) {
		palilloColor[id] = color;
		repaint();
	}

	public void cambiarColorPlato(int id, Color color) {
		platoColor[id] = color;
		repaint();
	}

	public void intentarPalillo(int id, int lado) {
		intento[id][lado] = !intento[id][lado];
		repaint();
	}
}
