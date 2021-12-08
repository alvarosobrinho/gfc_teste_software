package Plataforma.Console.Grafo;

public class Aresta {
	
	private No inicio;
	private No fim;
	
	public Aresta(No inicio, No fim) {
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public No getInicio() {
		return inicio;
	}
	public void setInicio(No inicio) {
		this.inicio = inicio;
	}
	public No getFim() {
		return fim;
	}
	public void setFim(No fim) {
		this.fim = fim;
	}

	@Override
	public String toString() {
		return "[inicio=" + inicio.toString() + ", fim=" + fim.toString() + "]";
		//return "Aresta [inicio=" + inicio.toString() + ", fim=" + fim.toString() + "]";
	}
	
	
}
