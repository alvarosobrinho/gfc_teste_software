package Plataforma.Console;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Plataforma.Console.Grafo.Grafo;
import Plataforma.Console.Grafo.No;
import Plataforma.Console.Pilha.Pilha;

public class GFC2 {
	
	ArrayList<String> arquivo;
	Grafo g;
	Pilha p;
	String ultimoTopoDaPilha;
	
	//Construtor da classe
	public GFC2(String caminho) {
		lerArquivo(caminho);
		this.g = new Grafo();
		this.p = new Pilha();
		this.ultimoTopoDaPilha = null;
	}
	
	//Função raiz para a criação do grafo
	public Grafo gerarGrafo(){
		ArrayList<Integer> listaIndiceLinhas = new ArrayList<Integer>(); //Lista que armazena todas as linhas de código de cada nó
		
		//Percorrendo todas as linhas do código
		//for(int i = 0; i < this.arquivo.size(); i++) { ////////////////////////////////////////////////////////////
		for(int i = 0; i < this.arquivo.size(); i++) {
			
			//Armazenando linha atual
			String linha = this.arquivo.get(i);
			
			//Quando é uma linha de comandos simples
			if(!contemDesvioInicial(linha) && !linha.contains("}")) {
				listaIndiceLinhas.add(i+1);
				continue;
			}else if(!linha.contains("else") && !linha.contains("else if") && linha.contains("{")){
				this.ultimoTopoDaPilha = p.getTopo();
				if(g.isDesvioFechado()){
					String aux = p.popPilha();
					fecharFluxoDecisao(i+1, aux);
					p.addPilha(aux);
					if(aux == "if"){
						g.setDesvioAberto(true);
					}
					continue;
				}
				if(!listaIndiceLinhas.isEmpty()) {
					this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
					ArrayList<Integer> l = new ArrayList<Integer>();
					l.add(i+1);
					this.g.addNo(l.toString(), p.getTopo()); // Crio um nó no grafo com a linha atual que representa o desvio, e sinaliza qual o desvio
					this.g.addAresta(listaIndiceLinhas.toString(), l.toString()); // Crio uma aresta ligando os dois nós criados
					listaIndiceLinhas.clear(); // Limpo a lista de linhas anteriotes
					if(p.getTopo() == "if"){
						g.setDesvioAberto(true);
					}
					System.out.println(g.isDesvioAberto());
				}
				// Quando a lista está vazia, apenas crio um novo nó com a linha atual e em seguida crio uma aresta com o nó anterior caso haja
				else {
					String no = this.g.getUltimoNo(this.ultimoTopoDaPilha);
					ArrayList<Integer> l = new ArrayList<Integer>();
					l.add(i+1);
					this.g.addNo(l.toString(), p.getTopo()); // Crio um nó no grafo com a linha atual que representa o desvio
					//Caso haja algum nó anterior faço essa conexao com a aresta
					if(no != null) {
						this.g.addAresta(no, l.toString()); // Crio uma aresta ligando os dois nós criados
					}
					if(p.getTopo() == "if"){
						g.setDesvioAberto(true);
					}
					System.out.println(no + "  " + l.toString() + "  " +this.ultimoTopoDaPilha);
					System.out.println(g.isDesvioAberto());
				}
			}else if((linha.contains("}") && (linha.contains("else") || linha.contains("else if")))) {
				String aux = p.popPilha();
				this.ultimoTopoDaPilha = p.getTopo();
				String no = this.g.getUltimoNo(this.ultimoTopoDaPilha); // Verifico qual o nó anterior
				this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
				this.g.addAresta(no, listaIndiceLinhas.toString()); // Crio uma aresta ligando os dois nós criados
				p.addPilha(aux);
				ArrayList<Integer> l = new ArrayList<Integer>();
				l.add(i+1);
				this.g.addNo(l.toString(), p.getTopo());
				No anterior = g.getNo(no);
				for(int j = 0; j < anterior.getArestasEntrata().size(); j++){
					String noAux = anterior.getArestasEntrata().get(j).getInicio().getDado();
					this.g.addAresta(noAux, l.toString());
				}
				this.ultimoTopoDaPilha = aux;
				listaIndiceLinhas.clear(); // Limpa a lista
			}else if((linha.contains("}") && (!linha.contains("else") && !linha.contains("else if")) && g.isDesvioAberto())){
				this.ultimoTopoDaPilha = p.getTopo();
				String no = this.g.getUltimoNo(this.ultimoTopoDaPilha); // Verifico qual o nó anterior
				this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
				this.g.addAresta(no, listaIndiceLinhas.toString()); // Crio uma aresta ligando os dois nós criados
				this.g.setDesvioFechado(true);
				listaIndiceLinhas.clear(); // Limpa a lista
			}else{
				String no = this.g.getUltimoNo(this.ultimoTopoDaPilha); // Verifico qual o nó anterior
				this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
				this.g.addAresta(no, listaIndiceLinhas.toString()); // Crio uma aresta ligando os dois nós criados

				// Verifica qual o tipo de bloco que está sendo fechado
				String topoPilha = p.popPilha();
				this.ultimoTopoDaPilha = topoPilha;
				String noFinal = g.getUltimaOcorrenciaNo(topoPilha); // Pega a referência do nó que deve ser fechado o ciclo
				this.g.addAresta(listaIndiceLinhas.toString(), noFinal); // Crio uma aresta ligando os dois nós criados
				listaIndiceLinhas.clear(); // Limpa a lista
			}
		}
		if(g.isDesvioFechado()){
			fecharFluxoDecisao(this.arquivo.size(), "");
			if(!p.estaVazia()){
				String no = this.g.getUltimoNo(this.ultimoTopoDaPilha); // Verifico qual o nó anterior
				this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
				this.g.addAresta(no, listaIndiceLinhas.toString()); // Crio uma aresta ligando os dois nós criados

				// Verifica qual o tipo de bloco que está sendo fechado
				String topoPilha = p.popPilha();
				this.ultimoTopoDaPilha = topoPilha;
				String noFinal = g.getUltimaOcorrenciaNo(topoPilha); // Pega a referência do nó que deve ser fechado o ciclo
				this.g.addAresta(listaIndiceLinhas.toString(), noFinal); // Crio uma aresta ligando os dois nós criados
				listaIndiceLinhas.clear(); // Limpa a lista
			}
		}
		else if(!listaIndiceLinhas.isEmpty()) {
			this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(this.arquivo.size()+1);
			this.g.addNo(l.toString(), p.getTopo()); // Crio um nó no grafo com a linha atual que representa o desvio, e sinaliza qual o desvio
			this.g.addAresta(listaIndiceLinhas.toString(), l.toString()); // Crio uma aresta ligando os dois nós criados
			listaIndiceLinhas.clear(); // Limpo a lista de linhas anteriotes
			System.out.println(g.isDesvioAberto());
		}
		// Quando a lista está vazia, apenas crio um novo nó com a linha atual e em seguida crio uma aresta com o nó anterior caso haja
		else {
			ArrayList<Integer> l = new ArrayList<Integer>();
			l.add(this.arquivo.size()+1);
			this.g.addNo(l.toString(), p.getTopo()); // Crio um nó no grafo com a linha atual que representa o desvio
			String no = this.g.getUltimoNo(this.ultimoTopoDaPilha);
			//Caso haja algum nó anterior faço essa conexao com a aresta
			if(no != null) {
				this.g.addAresta(no, l.toString()); // Crio uma aresta ligando os dois nós criados
			}
			System.out.println(no + "  " + l.toString() + "  " +this.ultimoTopoDaPilha);
			System.out.println(g.isDesvioAberto());
		}
		System.out.println("\n\n");
		System.out.println(this.g.toString());
		return this.g;
	}
	
	//Método que identifica se contém algum elemento que caracteriza algum tipo de desvio, e empilha essas estruturas
	boolean contemDesvioInicial(String linha) {
		if((linha.contains("if ") || linha.contains("if(")) && !linha.contains("else")) {
			this.p.addPilha("if");
			return true;
		}else if(linha.contains("else if " ) || linha.contains("else if(")){
			this.p.addPilha("else if");
			return true;
		}else if(linha.contains("else ") || linha.contains("else{")) {
			this.p.addPilha("else");
			return true;
		}else if(linha.contains("for ") || linha.contains("for(")) {
			this.p.addPilha("for");
			return true;
		}else if(linha.contains("while ") || linha.contains("while(")) {
			this.p.addPilha("while");
			return true;
		}/*else if(linha.contains("{")) {  // Fiquei na dúvida se esse entra ou não, acredito que não
			return true;
		}*/
		return false;
	}
	public void fecharFluxoDecisao(int novo, String tipo){
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(novo+1);
		if(tipo != ""){
			this.g.addNo(l.toString(), tipo);
		}
		else{
			this.g.addNo(l.toString());
		}
		this.ultimoTopoDaPilha = p.getTopo();
		while(this.ultimoTopoDaPilha != "if"){
			String no = this.g.getUltimoNo(this.ultimoTopoDaPilha); // Verifico qual o nó anterior
			this.g.addAresta(no, l.toString()); // Crio uma aresta ligando os dois nós criados
			No dentro = g.getNo(no);
			if(dentro.temFilho()){
				for(int j = 0; j < dentro.getArestasSaida().size(); j++){
					String noAux = dentro.getArestasSaida().get(j).getFim().getDado();
					if(!noAux.contains(l.toString())){
						this.g.addAresta(noAux, l.toString());
					}
				}
			}
			dentro.setDesvioNoFechado(true);
			p.popPilha();
			this.ultimoTopoDaPilha = p.getTopo();
		}
		//Fechando o IF
		String no = this.g.getUltimoNo(this.ultimoTopoDaPilha);
		this.g.addAresta(no, l.toString()); // Crio uma aresta ligando os dois nós criados
		No dentro = g.getNo(no);
		if(dentro.temFilho()){
			for(int j = 0; j < dentro.getArestasSaida().size(); j++){
				String noAux = dentro.getArestasSaida().get(j).getFim().getDado();
				if(!noAux.contains(l.toString())){
					this.g.addAresta(noAux, l.toString());
				}
			}
		}
		dentro.setDesvioNoFechado(true);
		g.setDesvioAberto(false);
		g.setDesvioFechado(false);
		p.popPilha();
	}
	//Método para ler o arquivo do diretório passado como caminho
	public void lerArquivo(String caminho){
        ArrayList<String> arquivo = new ArrayList<String>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
				String linha = br.readLine();
				while (linha != null) {
				    arquivo.add(linha);
				    linha = br.readLine();
				}
			}

        } catch (IOException e) {
            arquivo = null;
        }
        this.arquivo = arquivo;
    }
}
