package unidad1;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * ORDENAMIENTO LOGICO
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.util.ArrayList;

class Nodo<T> {
	private T objeto;
	private int siguiente;

	public Nodo(T objeto) {
		this.objeto = objeto;
		siguiente = -1;
	}

	public T getObjeto() {
		return objeto;
	}

	public int getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(int siguiente) {
		this.siguiente = siguiente;
	}
}

public class ListaLogica<T extends Comparable<T>> {

	private ArrayList<Nodo<T>> lista;
	private int inicio, ultimo;

	public ListaLogica() {
		inicio = -1;
		ultimo = -1;
		lista = new ArrayList<>();
	}

	public void insertar(T objeto) {
		lista.add(new Nodo<T>(objeto));

		// Caso en el que la lista esta vacia
		if (inicio == -1) {
			inicio = 0;
			ultimo = 0;
			return;
		}

		// Caso en el que el nuevo dato va en el inicio
		if (objeto.compareTo(lista.get(inicio).getObjeto()) < 0) {
			int tmp = inicio;
			inicio = lista.size() - 1;
			lista.get(lista.size() - 1).setSiguiente(tmp);
			return;
		}

		// Caso en el que el nuevo dato va despues del ultimo
		if (objeto.compareTo(lista.get(ultimo).getObjeto()) > 0) {
			int tmp = ultimo;
			ultimo = lista.size() - 1;
			lista.get(tmp).setSiguiente(ultimo);
			return;
		}

		// Caso en el que el nuevo dato se encuentra en medio
		int actual = inicio;
		int anterior = -1;

		while (objeto.compareTo(lista.get(actual).getObjeto()) > 0) {
			anterior = actual;
			actual = lista.get(actual).getSiguiente();
		}
		lista.get(anterior).setSiguiente(lista.size() - 1);
		lista.get(lista.size() - 1).setSiguiente(actual);
	}

	public ArrayList<Nodo<T>> getLista() {
		return lista;
	}

	public int getInicio() {
		return inicio;
	}
}
