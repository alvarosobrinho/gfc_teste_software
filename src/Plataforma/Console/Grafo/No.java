package Plataforma.Console.Grafo;

import java.util.ArrayList;

public class No {

	private String dado;
	private String tipoComandoDesvio;
	private boolean emAberto;
	private ArrayList<Aresta> arestasEntrata;
	private ArrayList<Aresta> arestasSaida;
	
	public No(String dado) {
		this.dado = dado;
		this.arestasEntrata = new ArrayList<Aresta>();
		this.arestasSaida = new ArrayList<Aresta>();
		this.tipoComandoDesvio = null;
	}
	
	public No(String dado, String tipoComando, boolean emAberto) {
		this.dado = dado;
		this.arestasEntrata = new ArrayList<Aresta>();
		this.arestasSaida = new ArrayList<Aresta>();
		this.tipoComandoDesvio = tipoComando;
		this.emAberto = emAberto;
	}
	
	public String getDado() {
		return dado;
	}

	public void setDado(String dado) {
		this.dado = dado;
	}
	
	public String getComandoDesvio() {
		return tipoComandoDesvio;
	}

	public void setComandoDesvio(String comandoDesvio) {
		this.tipoComandoDesvio = comandoDesvio;
	}
	
	public ArrayList<Aresta> getArestasSaida() {
		return arestasSaida;
	}

	public void addArestaEntrada(Aresta a) {
		this.arestasEntrata.add(a);
	}
	
	public void addArestaSaida(Aresta a) {
		this.arestasSaida.add(a);
	}
	
	public boolean temFilho() {
		return !this.arestasSaida.isEmpty();
	}

	@Override
	public String toString() {
		return dado;
		//return "No [dado=" + dado + "]";
	}
	
}
