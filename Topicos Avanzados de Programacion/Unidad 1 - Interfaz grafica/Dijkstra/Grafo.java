package proyecto1;

import java.util.*;

public class Grafo {

	class Arista {
		int origen, destino, peso;

		public Arista(int origen, int destino, int peso) {
			this.origen = origen;
			this.destino = destino;
			this.peso = peso;
		}
	}

	List<List<Arista>> listaAdj;
	int n;

	public Grafo(int n) {
		this.n = n;
		listaAdj = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			listaAdj.add(new ArrayList<>());
		}
	}

	public void añadirArista(int origen, int destino, int peso) {
		listaAdj.get(origen).add(new Arista(origen, destino, peso));
		listaAdj.get(destino).add(new Arista(destino, origen, peso));
	}

	public Nodo[] dijkstra(int origen) {
		Nodo[] resultado = new Nodo[n + 1];
		for (int i = 1; i <= n; i++) {
			if (i == origen) {
				resultado[i] = new Nodo(i, true, i, 0);
			} else {
				resultado[i] = new Nodo(i, false, -1, Integer.MAX_VALUE);
			}
		}

		PriorityQueue<Nodo> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.getDistancia()));
		pq.add(new Nodo(origen, 0));

		while (!pq.isEmpty()) {
			Nodo nodo = pq.poll();
			int u = nodo.getVertice();
			for (Arista arista : listaAdj.get(u)) {
				int v = arista.destino;
				int peso = arista.peso;

				if (!resultado[v].isVisitado() && (resultado[u].getDistancia() + peso) < resultado[v].getDistancia()) {
					resultado[v].setDistancia(resultado[u].getDistancia() + peso);
					resultado[v].setAnterior(u);
					pq.add(new Nodo(v, resultado[v].getDistancia()));
				}
			}
			resultado[u].setVisitado(true);
		}

		return resultado;
	}
}
