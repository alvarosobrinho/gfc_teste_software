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
				boolean flag = false;
				for(int i=0; i<nosFilhos.size(); i++) {
					if(!visitados.contains(nosFilhos.get(i))) {
						noAtual = nosFilhos.get(i);
						caminhoAtual.add(noAtual);
						if(!visitados.contains(noAtual)) {
							visitados.add(noAtual);
						}
						flag = true;
						break;
					}
				}
				if(!flag) {
					noAtual = nosFilhos.get(nosFilhos.size()-1);
					caminhoAtual.add(noAtual);
					if(!visitados.contains(noAtual)) {
						visitados.add(noAtual);
					}
				}
				
			}
			caminhos.add(caminhoAtual);
		}
		return caminhos;
	}
	
	public ArrayList<No> gerarFilhos(No no){
		ArrayList<No> filhos = new ArrayList<No>();
		for(int i =0; i < no.getArestasSaida().size(); i++) {
			filhos.add(no.getArestasSaida().get(i).getFim());			
		}
		return filhos;
	}

	@Override
	public String toString() {
		return "Grafo \t arestas=" + arestas.toString() + ",\n\t nos=" + nos.toString();
	}
		
}
