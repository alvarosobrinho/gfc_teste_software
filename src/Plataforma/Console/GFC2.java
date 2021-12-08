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
	
	//Fun��o raiz para a cria��o do grafo
	public Grafo gerarGrafo(){
		ArrayList<Integer> listaIndiceLinhas = new ArrayList<Integer>(); //Lista que armazena todas as linhas de c�digo de cada n�
		
		//Percorrendo todas as linhas do c�digo
		//for(int i = 0; i < this.arquivo.size(); i++) { ////////////////////////////////////////////////////////////
		for(int i = 0; i < 11; i++) {
			
			//Armazenando linha atual
			String linha = this.arquivo.get(i);
			
			//Quando � uma linha de comandos simples
			if(!contemDesvioInicial(linha) && !linha.contains("}")) {
				listaIndiceLinhas.add(i+1);
				continue;
			}
			
			//Quando � uma linha que fecha um bloco de c�digo
			else if(linha.contains("}")) {
				//Verifico se h� dados na lista de linhas, se tiver, crio um n� para esses comandos, em seguida crio uma aresta para o n� anterior
				if(!listaIndiceLinhas.isEmpty()) {
					String no = this.g.getUltimoNoLiteral(); // Verifico qual o n� anterior ////////////////////////////////////////////////////////
					this.g.addNo(listaIndiceLinhas.toString()); // Crio um n� no grafo com as linhas anteriores
					this.g.addAresta(no, listaIndiceLinhas.toString()); // Crio uma aresta ligando os dois n�s criados
					
					// Verifica qual o tipo de bloco que est� sendo fechado
					String topoPilha = p.getTopo();
					this.ultimoTopoDaPilha = topoPilha;
					
					// Quando for loop
					if(topoPilha == "while" || topoPilha == "for") {
						String noFinal = g.getUltimaOcorrenciaNo(topoPilha); // Pega a refer�ncia do n� que deve ser fechado o ciclo
						this.g.addAresta(listaIndiceLinhas.toString(), noFinal); // Crio uma aresta ligando os dois n�s criados
						p.popPilha(); // Remove a instru��o do topo da pilha
					}
					
					// Quando for desvio condicional
					else if(topoPilha == "if" || topoPilha == "else" || topoPilha == "else if") {
						g.setDesvioAberto(true);
						p.popPilha(); /////////////////////////////////////////////
					}
					
					listaIndiceLinhas.clear(); // Limpa a lista
				}
			}
			
			//Quando � uma linha que cont�m algum n� com desvio
			else {
				//Verifico se h� dados na lista de linhas, se tiver, crio um n� para esses comandos, em seguida crio um n� para a linha atual, junto com a aresta ligando ambos
				if(!listaIndiceLinhas.isEmpty()) {
					this.g.addNo(listaIndiceLinhas.toString()); // Crio um n� no grafo com as linhas anteriores
					ArrayList<Integer> l = new ArrayList<Integer>();
					l.add(i+1);
					this.g.addNo(l.toString(), p.getTopo(), true); // Crio um n� no grafo com a linha atual que representa o desvio, e sinaliza qual o desvio
					this.g.addAresta(listaIndiceLinhas.toString(), l.toString()); // Crio uma aresta ligando os dois n�s criados
					listaIndiceLinhas.clear(); // Limpo a lista de linhas anteriotes
				}
				// Quando a lista est� vazia, apenas crio um novo n� com a linha atual e em seguida crio uma aresta com o n� anterior caso haja
				else {
					String no;
					if(g.isDesvioAberto()) {
						no = this.g.getUltimoNoCondicional();  // Armazena o �ltimo n� condicional que estiver em aberto
						g.setDesvioAberto(false); ////////////////////////////////////////////
					}else {
						no = this.g.getUltimoNoLiteral(); // Armazena o �ltimo n� antes de criar o novo
					}
					ArrayList<Integer> l = new ArrayList<Integer>();
					l.add(i+1);
					this.g.addNo(l.toString(), p.getTopo(), true); // Crio um n� no grafo com a linha atual que representa o desvio
					//Caso haja algum n� anterior fa�o essa conexao com a aresta
					if(no != null) {
						this.g.addAresta(no, l.toString()); // Crio uma aresta ligando os dois n�s criados
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
	
	//M�todo que identifica se cont�m algum elemento que caracteriza algum tipo de desvio, e empilha essas estruturas
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
		}/*else if(linha.contains("{")) {  // Fiquei na d�vida se esse entra ou n�o, acredito que n�o
			return true;
		}*/
		return false;
	}
	
	boolean ehCondicional(String c) {
		return c.equals("if") || c.equals("else") || c.equals("else if");
	}
	
	//M�todo para ler o arquivo do diret�rio passado como caminho
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
