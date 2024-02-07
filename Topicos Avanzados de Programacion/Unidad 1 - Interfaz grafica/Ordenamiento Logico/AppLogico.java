package unidad1;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * ORDENAMIENTO LOGICO
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class AppLogico extends JFrame {

	ListaLogica<Persona> lista;
	ArrayList<Nodo<Persona>> listaPersonas;

	public AppLogico() {
		super("Lista Lógica");
		lista = new ListaLogica<>();
		leerArchivo("PERSONAS.DAT");
		listaPersonas = lista.getLista();
		HazInterfaz();
		HazEscuchas();
	}

	private void HazEscuchas() {
	}

	private void HazInterfaz() {
		setSize(850, 650);
		setLocationRelativeTo(null);

		// Añadir etiqueta inicio
		JLabel etiquetaInicio = new JLabel("Inicio: ", SwingConstants.CENTER);
		add(etiquetaInicio, BorderLayout.NORTH);

		// Añadir panel de datos en el centro
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 4));
		panel.add(new JLabel("No.", SwingConstants.CENTER));
		panel.add(new JLabel("Nombre", SwingConstants.CENTER));
		panel.add(new JLabel("Edad", SwingConstants.CENTER));
		panel.add(new JLabel("Siguiente", SwingConstants.CENTER));
		for (int i = 0; i < listaPersonas.size(); i++) {
			panel.add(new JLabel((i) + "", SwingConstants.CENTER));
			panel.add(new JLabel(listaPersonas.get(i).getObjeto().getNombre(), SwingConstants.CENTER));
			panel.add(new JLabel(listaPersonas.get(i).getObjeto().getEdad() + "", SwingConstants.CENTER));
			panel.add(new JLabel(listaPersonas.get(i).getSiguiente() + "", SwingConstants.CENTER));
		}
		
		add(panel);
		etiquetaInicio.setText("Inicio: " + lista.getInicio());

		// Añadir panel en el sur
		JPanel panelSur = new JPanel();
		panelSur.setBackground(Color.WHITE);
		panelSur.setLayout(new GridLayout(0, 3));
		panelSur.setBorder(new LineBorder(Color.BLACK, 2));
		panelSur.add(new JLabel("CARLOS DANIEL BELTRÁN MEDINA", SwingConstants.LEFT));
		panelSur.add(new JLabel("TÓPICOS AVANZADOS DE PROGRAMACIÓN", SwingConstants.CENTER));
		panelSur.add(new JLabel("DR. CLEMENTE GARCIA GERARDO", SwingConstants.RIGHT));
		add(panelSur, BorderLayout.SOUTH);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void leerArchivo(String archivo) {
		try {
			RandomAccessFile input = new RandomAccessFile(archivo, "r");
			int cantidadPersonas = (int) input.length() / 56;
			for (int i = 0; i < cantidadPersonas; i++) {
				String nombre = input.readUTF().trim();
				int edad = input.readInt();
				lista.insertar(new Persona(nombre, edad));
			}
			input.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new AppLogico();
	}

}
