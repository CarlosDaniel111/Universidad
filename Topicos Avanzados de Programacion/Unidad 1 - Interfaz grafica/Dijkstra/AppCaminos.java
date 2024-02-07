package proyecto1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class AppCaminos {

	public static void main(String[] args) {
		System.out.println("PROYECTO 1");
		System.out.println("TOPICOS AVANZADOS DE PROGRAMACION");
		System.out.println("ALUMNO: BELTRAN MEDINA CARLOS DANIEL");
		System.out.println("DOCENTE: GARCIA GERARDO CLEMENTE");

		Grafo grafo = new Grafo(7);
		List<int[]> contenido = leerArchivo("Caminos.cvs");
		for (int valores[] : contenido) {
			grafo.añadirArista(valores[0], valores[1], valores[2]);
		}

		for (int i = 1; i <= 7; i++) {
			Nodo[] resultado = grafo.dijkstra(i);
			System.out.println("\nNodo de origen: " + i);
			System.out.println("==============================================================");
			System.out.println("Nodo  |  Visitado  |  Nodo Anterior  |  Distancia del origen  ");
			System.out.println("==============================================================");
			for (int j = 1; j <= 7; j++) {
				System.out.println(resultado[j]);
			}
			System.out.println("==============================================================");
		}
	}

	public static List<int[]> leerArchivo(String archivo) {
		List<int[]> contenido = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] campos = line.split(",");
				int[] valores = new int[campos.length];
				for (int i = 0; i < campos.length; i++)
					valores[i] = Integer.parseInt(campos[i]);
				contenido.add(valores);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return contenido;
	}

}
