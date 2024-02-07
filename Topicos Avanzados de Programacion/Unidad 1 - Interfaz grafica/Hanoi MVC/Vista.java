package hanoi;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * TORRES DE HANOI
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {

	final int LIMITE_DISCOS = 10;
	private final Color[] colores = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE };
	private JTextField txtDiscos;
	private JButton btnEmpezar;
	private Disco[] discos;
	private Timer t;
	private Graphics g;
	private Image myImagen = null;

	public Vista() {
		super("Torres de Hanoi");
		HazInterfaz();

		myImagen = createImage(getWidth(), getHeight());
		g = myImagen.getGraphics();
		repaint();
	}

	private void HazInterfaz() {
		setSize(1200, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new FlowLayout());

		txtDiscos = new JTextField("Cantidad de Discos", 20);
		txtDiscos.selectAll();
		btnEmpezar = new JButton("Empezar");

		panelMenu.add(txtDiscos);
		panelMenu.add(btnEmpezar);
		add(panelMenu, BorderLayout.NORTH);

		setVisible(true);
	}

	public void setEscuchador(Controlador c) {
		btnEmpezar.addActionListener(c);
		t = new Timer(10, c);
	}

	public void paint(Graphics gl) {
		Dibuja();
		gl.drawImage(myImagen, 0, 0, getWidth(), getHeight(), this);
	}

	private void Dibuja() {
		if (g == null)
			return;
		super.paint(g);
		g.setColor(new Color(104, 71, 5));
		g.fillRect(80, this.getHeight() - 130, this.getWidth() - 160, 25);
		g.fillRoundRect(255, 280, 30, 400, 20, 20);
		g.fillRoundRect(585, 280, 30, 400, 20, 20);
		g.fillRoundRect(915, 280, 30, 400, 20, 20);
		if (discos != null) {
			for (int i = 0; i < discos.length; i++) {
				g.setColor(colores[i % 5]);
				g.fillRoundRect(discos[i].getX(), discos[i].getY(), discos[i].getWidth(), discos[i].getHeight(), 25,
						25);
			}
		}
	}

	public void actualizarDiscos(Disco[] discos) {
		this.discos = discos;
		repaint();
	}

	public void mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	public String getDiscos() {
		return txtDiscos.getText();
	}

	public JButton getBtnEmpezar() {
		return btnEmpezar;
	}

	public Timer getT() {
		return t;
	}

}
