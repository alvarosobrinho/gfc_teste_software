package Plataforma.Console;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Plataforma.Console.Grafo.Grafo;
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
		for(int i = 0; i < 11; i++) {
			
			//Armazenando linha atual
			String linha = this.arquivo.get(i);
			
			//Quando é uma linha de comandos simples
			if(!contemDesvioInicial(linha) && !linha.contains("}")) {
				listaIndiceLinhas.add(i+1);
				continue;
			}
			
			//Quando é uma linha que fecha um bloco de código
			else if(linha.contains("}")) {
				//Verifico se há dados na lista de linhas, se tiver, crio um nó para esses comandos, em seguida crio uma aresta para o nó anterior
				if(!listaIndiceLinhas.isEmpty()) {
					String no = this.g.getUltimoNoLiteral(); // Verifico qual o nó anterior ////////////////////////////////////////////////////////
					this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
					this.g.addAresta(no, listaIndiceLinhas.toString()); // Crio uma aresta ligando os dois nós criados
					
					// Verifica qual o tipo de bloco que está sendo fechado
					String topoPilha = p.getTopo();
					this.ultimoTopoDaPilha = topoPilha;
					
					// Quando for loop
					if(topoPilha == "while" || topoPilha == "for") {
						String noFinal = g.getUltimaOcorrenciaNo(topoPilha); // Pega a referência do nó que deve ser fechado o ciclo
						this.g.addAresta(listaIndiceLinhas.toString(), noFinal); // Crio uma aresta ligando os dois nós criados
						p.popPilha(); // Remove a instrução do topo da pilha
					}
					
					// Quando for desvio condicional
					else if(topoPilha == "if" || topoPilha == "else" || topoPilha == "else if") {
						g.setDesvioAberto(true);
						p.popPilha(); /////////////////////////////////////////////
					}
					
					listaIndiceLinhas.clear(); // Limpa a lista
				}
			}
			
			//Quando é uma linha que contém algum nó com desvio
			else {
				//Verifico se há dados na lista de linhas, se tiver, crio um nó para esses comandos, em seguida crio um nó para a linha atual, junto com a aresta ligando ambos
				if(!listaIndiceLinhas.isEmpty()) {
					this.g.addNo(listaIndiceLinhas.toString()); // Crio um nó no grafo com as linhas anteriores
					ArrayList<Integer> l = new ArrayList<Integer>();
					l.add(i+1);
					this.g.addNo(l.toString(), p.getTopo(), true); // Crio um nó no grafo com a linha atual que representa o desvio, e sinaliza qual o desvio
					this.g.addAresta(listaIndiceLinhas.toString(), l.toString()); // Crio uma aresta ligando os dois nós criados
					listaIndiceLinhas.clear(); // Limpo a lista de linhas anteriotes
				}
				// Quando a lista está vazia, apenas crio um novo nó com a linha atual e em seguida crio uma aresta com o nó anterior caso haja
				else {
					String no;
					if(g.isDesvioAberto()) {
						no = this.g.getUltimoNoCondicional();  // Armazena o último nó condicional que estiver em aberto
						g.setDesvioAberto(false); ////////////////////////////////////////////
					}else {
						no = this.g.getUltimoNoLiteral(); // Armazena o último nó antes de criar o novo
					}
					ArrayList<Integer> l = new ArrayList<Integer>();
					l.add(i+1);
					this.g.addNo(l.toString(), p.getTopo(), true); // Crio um nó no grafo com a linha atual que representa o desvio
					//Caso haja algum nó anterior faço essa conexao com a aresta
					if(no != null) {
						this.g.addAresta(no, l.toString()); // Crio uma aresta ligando os dois nós criados
					}
				}
			}
			//System.out.println(this.arquivo.get(i));
		}
		System.out.println("\n\n");
		System.out.println(this.g.toString());
		System.out.println(this.p.toString());
		return this.g;
	}
	
	//Método que identifica se contém algum elemento que caracteriza algum tipo de desvio, e empilha essas estruturas
	boolean contemDesvioInicial(String linha) {
		if(linha.contains("else if ") || linha.contains("else if(")) {
			this.p.addPilha("else if");
			return true;
		}else if(linha.contains("if ") || linha.contains("if(")) {
			this.p.addPilha("if");
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
	
	boolean ehCondicional(String c) {
		return c.equals("if") || c.equals("else") || c.equals("else if");
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
