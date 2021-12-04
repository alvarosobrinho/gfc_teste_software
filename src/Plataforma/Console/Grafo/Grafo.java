package Plataforma.Console.Grafo;

import java.util.ArrayList;

public class Grafo {
	private ArrayList<Aresta> arestas;
	private ArrayList<No> nos;
	private boolean ultimoNoEhLoop;
	private boolean	desvioAberto;
	private boolean	desvioFechado;
	
	public Grafo() {
		this.arestas = new ArrayList<Aresta>();
		this.nos = new ArrayList<No>();
		this.ultimoNoEhLoop = false;
		this.desvioAberto = false;
		this.desvioFechado = false;
	}
	
	public void addNo(String dado) {
		No novo = new No(dado);
		this.nos.add(novo);
	}
	
	public ArrayList<No> getNos() {
		return nos;
	}

	public boolean isDesvioAberto() {
		return desvioAberto;
	}
	public boolean isDesvioFechado() {
		return desvioFechado;
	}

	public void setDesvioAberto(boolean desvioAberto) {
		this.desvioAberto = desvioAberto;
	}
	public void setDesvioFechado(boolean desvioFechado) {
		this.desvioFechado = desvioFechado;
	}

	public void addNo(String dado, String tipoComando) {
		No novo = new No(dado, tipoComando);
		this.nos.add(novo);
	}
	
	public void addAresta(String i, String f) {
		No inicio = getNo(i);
		No fim = getNo(f);
		Aresta aresta = new Aresta(inicio, fim);
		fim.addArestaEntrada(aresta);
		inicio.addArestaSaida(aresta);
		this.arestas.add(aresta);
	}
	
	public No getNo(String dado) {
		for(int i =0; i<this.nos.size(); i++) {
			if(this.nos.get(i).getDado().equals(dado)) {
				return this.nos.get(i);
			}
		}
		return null;
	}
	
	public String getUltimoNo(String prioridade) {
		if(prioridade != null) {
			if((prioridade.equals("if") || prioridade.equals("else") || prioridade.equals("else if")) && this.desvioAberto) {
				for(int i = this.nos.size()-1; i >= 0; i--) {   // -1 ou -2 ?
					String t = this.nos.get(i).getComandoDesvio();
					if(t != null && (t.equals(prioridade))) {
						if(!this.nos.get(i).getDesvioNoFechado()) {
							return this.nos.get(i).getDado();
						}
					}
				}
				return null;
			} else if((prioridade.equals("for") || prioridade.equals("while")) && this.ultimoNoEhLoop) {
				for(int i = this.nos.size()-1; i >= 0; i--) {
					String t = this.nos.get(i).getComandoDesvio();
					if(t != null && (t.equals("while") || t.equals("for"))) {
						this.ultimoNoEhLoop = false;
						return this.nos.get(i).getDado();
					}
				}
				return null;
			}else {
				if(!this.nos.isEmpty()) {
					return this.nos.get(this.nos.size()-1).getDado();
				}
				return null;
			}
		} else {
			if(!this.nos.isEmpty()) {
				return this.nos.get(this.nos.size()-1).getDado();
			}
			return null;
		}
		
	}
	
	public String getUltimaOcorrenciaNo(String tipo) {
		for(int i = this.nos.size()-1; i >= 0; i--) {
			String t = this.nos.get(i).getComandoDesvio();
			if(t != null && t.equals(tipo)) {
				if(t.equals("while") || t.equals("for")) {
					this.ultimoNoEhLoop = true;
				}
				return this.nos.get(i).getDado();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Grafo \t arestas=" + arestas.toString() + ",\n\t nos=" + nos.toString();
	}
		
}
