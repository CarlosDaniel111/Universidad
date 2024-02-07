package proyecto1;

public class Nodo {
	private int vertice, distancia, anterior;
	private boolean visitado;

	public Nodo(int vert, boolean visit, int ant, int dist) {
		this.vertice = vert;
		this.visitado = visit;
		this.anterior = ant;
		this.distancia = dist;
	}

	public Nodo(int vert, int dist) {
		this.vertice = vert;
		this.distancia = dist;
	}

	public int getVertice() {
		return vertice;
	}

	public void setVertice(int vertice) {
		this.vertice = vertice;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public int getAnterior() {
		return anterior;
	}

	public void setAnterior(int anterior) {
		this.anterior = anterior;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	@Override
	public String toString() {
		return vertice + "         " + visitado + "             " + (anterior == vertice ? "*" : anterior)
				+ "                   " + distancia;
	}
}
