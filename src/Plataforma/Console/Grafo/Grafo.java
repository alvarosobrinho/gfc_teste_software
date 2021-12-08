package Plataforma.Console.Grafo;

import java.util.ArrayList;

public class Grafo {
	private ArrayList<Aresta> arestas;
	private ArrayList<No> nos;
	private boolean ultimoNoEhLoop;
	private boolean	desvioAberto;
	
	public Grafo() {
		this.arestas = new ArrayList<Aresta>();
		this.nos = new ArrayList<No>();
		this.ultimoNoEhLoop = false;
		this.desvioAberto = false;
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

	public void setDesvioAberto(boolean desvioAberto) {
		this.desvioAberto = desvioAberto;
	}

	public void addNo(String dado, String tipoComando, boolean emAberto) {
		No novo = new No(dado, tipoComando, emAberto);
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
	
	public String getUltimoNoLiteral() {
		if(!this.nos.isEmpty()) {
			return this.nos.get(this.nos.size()-1).getDado();
		}
		return null;
	}
	
	public String getUltimoNoCondicional() {
		for(int i = this.nos.size()-1; i >= 0; i--) {
			String t = this.nos.get(i).getComandoDesvio();
			if(t != null && (t.equals("if") || t.equals("else"))) {
				return this.nos.get(i).getDado();
			}
		}
		return null;
	}
	
	/////////////////////////////////
	public String getUltimoNo(String prioridade) {
		if(prioridade != null) {
			if((prioridade.equals("if") || prioridade.equals("else")) && this.desvioAberto) {
				for(int i = this.nos.size()-1; i >= 0; i--) {   // -1 ou -2 ?
					String t = this.nos.get(i).getComandoDesvio();
					System.out.println("t = " + t);
					if(t != null && (t.equals("if") || t.equals("else"))) {
						return this.nos.get(i).getDado();
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
	
	/////////////////////////////////
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
	
	public Aresta getAresta(String inicio, String fim) {
		for(int i=0; i<this.arestas.size(); i++) {
			Aresta a = this.arestas.get(i);
			if(a.getInicio().equals(inicio) && a.getFim().equals(fim)) {
				return a;
			}
		}
		return null;
	}
	
	boolean HaNoFilhoNaoVisitado(No no, ArrayList<No> visitados) {
		
		ArrayList<No> nosFilhos = gerarFilhos(no);
		
		if(nosFilhos.size() > 0) {
			for(int i=0; i<nosFilhos.size(); i++) {
				if(!visitados.contains(nosFilhos.get(i))) {
					return true;
				}else {
					return HaNoFilhoNaoVisitado(nosFilhos.get(i), visitados);
				}
			}			
		}
		return false;
	}
	
	public ArrayList<ArrayList<No>> todosNos() {
		ArrayList<No> visitados = new ArrayList<No>();
		ArrayList<ArrayList<No>> caminhos = new ArrayList<ArrayList<No>>();
		
		while(visitados.size() < this.nos.size()) {
			ArrayList<No> caminhoAtual = new ArrayList<No>();
			No noAtual = this.nos.get(0);
			caminhoAtual.add(noAtual);
			if(!visitados.contains(noAtual)) {
				visitados.add(noAtual);
			}
			while(noAtual != this.nos.get(this.nos.size()-1)) {
				ArrayList<No> nosFilhos = gerarFilhos(noAtual);
				for(int i=0; i<nosFilhos.size(); i++) {
					if(!visitados.contains(nosFilhos.get(i))) {
						noAtual = nosFilhos.get(i);
						caminhoAtual.add(noAtual);
						if(!visitados.contains(noAtual)) {
							visitados.add(noAtual);
						}
						break;
					}else if(HaNoFilhoNaoVisitado(nosFilhos.get(i), visitados)) {
						noAtual = nosFilhos.get(i);
						caminhoAtual.add(noAtual);
						if(!visitados.contains(noAtual)) {
							visitados.add(noAtual);
						}
						break;
					}
				}				
			}
			caminhos.add(caminhoAtual);
		}
		return caminhos;
	}
	
	public ArrayList<No> gerarFilhos(No no){
		ArrayList<Aresta> a = no.getArestasSaida();
		ArrayList<No> out = new ArrayList<No>();
		for(int i=0; i<a.size();i++) {
			String s = a.get(i).getFim().getDado();
			out.add(this.getNo(s));
		}
		return out;
	}
	
	boolean HaArestasFilhasNaoVisitado(Aresta aresta, ArrayList<Aresta> visitados) {
		
		No no = aresta.getFim();
		ArrayList<Aresta> arestas = no.getArestasSaida();
		
		for(int i=0; i<arestas.size(); i++) {
			if(!visitados.contains(arestas.get(i))) {
				return true;
			}else {
				return HaArestasFilhasNaoVisitado(arestas.get(i), visitados);
			}
		}
		return false;
	}
	
	public ArrayList<ArrayList<No>> todasArestas(){
		ArrayList<Aresta> visitadas = new ArrayList<Aresta>();
		ArrayList<ArrayList<No>> caminhos = new ArrayList<ArrayList<No>>();
		
		while(visitadas.size() < this.arestas.size()) {
			ArrayList<No> caminhoAtual = new ArrayList<No>();
			No noAtual = this.nos.get(0);
			caminhoAtual.add(noAtual);
			while(noAtual != this.nos.get(this.nos.size()-1)) {
				ArrayList<Aresta> arestas = noAtual.getArestasSaida();
				for(int i=0; i<arestas.size(); i++) {
					if(!visitadas.contains(arestas.get(i))) {
						visitadas.add(arestas.get(i));
						noAtual = this.getNo(arestas.get(i).getFim().getDado());
						caminhoAtual.add(noAtual);
						break;
					}else if(HaArestasFilhasNaoVisitado(arestas.get(i),visitadas)) {
						noAtual = this.getNo(arestas.get(i).getFim().getDado());
						caminhoAtual.add(noAtual);
						break;
					}
				}
			}
			caminhos.add(caminhoAtual);
		}
		return caminhos;
	}

	@Override
	public String toString() {
		return "Grafo \t arestas=" + arestas.toString() + ",\n\t nos=" + nos.toString();
	}
		
}
