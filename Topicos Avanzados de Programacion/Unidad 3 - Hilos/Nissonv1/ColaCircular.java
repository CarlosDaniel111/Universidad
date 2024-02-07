package nissonv1;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * NISSON V1
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

public class ColaCircular <T>{
	
	private int frente,fin, tamano;
	private T [] cc;
	private T dr;
	
	public ColaCircular() {
		this(10);
	}
	public ColaCircular(int tamano) {
		this.tamano=tamano;
		cc=(T[]) new Object[tamano];
		frente=fin=-1;
	}
	
	public boolean Insertar(T dato) {
		if( Llena() )
			return false;
		if( fin == tamano-1 )
			fin=0;
		else 
			fin++;
		cc[fin]=dato;
		if(frente==-1)
			frente=0;
		return true;
	}
	public boolean Retirar() {
		if( Vacia())
			return false;
		dr=cc[frente];
		cc[frente]=null;
		if(frente==fin)
			frente=fin=-1;
		else
			if(frente==tamano-1)
				frente=0;
			else
				frente++;
		return true;
	}
	public boolean Vacia() {
		return frente==-1;
	}
	public boolean Llena() {
		return frente==0 && fin == tamano-1 || fin+1==frente ;
	}
	
	public T getDr() {
		return dr;
	}

}
