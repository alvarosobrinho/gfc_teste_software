package Plataforma.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Plataforma.Console.Grafo.Grafo;
import Plataforma.Console.Pilha.Pilha;

public class GFC3 {
	
	ArrayList<String> arquivo;
	Grafo g;
	Pilha p;
	
	//Construtor da classe
	public GFC3(String caminho) {
		lerArquivo(caminho);
		this.g = new Grafo();
		this.p = new Pilha();
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
	
	//Fun��o que retorna uma lista com todos os comandos relevantes da linha passada como parametro
	ArrayList<String> comandos(String linha){
		ArrayList<String> out = new ArrayList<String>();
		if(linha.contains("}")) {
			out.add("}");
		} if(linha.contains("if ") || linha.contains("if(")) {
			out.add("if");
		}else if(linha.contains("else ") || linha.contains("else{")) {
			out.add("else");
		}if(linha.contains("for ") || linha.contains("for(") || linha.contains("while ") || linha.contains("while(")) {
			out.add("loop");
		}		
		return out;
	}
	
	//Fun��o raiz para a cria��o do grafo
	public Grafo gerarGrafo(){
		ArrayList<Integer> listaIndiceLinhas = new ArrayList<Integer>(); //Lista que armazena todas as linhas de c�digo de cada n�
		
		//Percorrendo todas as linhas do c�digo
		//for(int i = 0; i < this.arquivo.size(); i++) { ////////////////////////////////////////////////////////////
		for(int i = 0; i < 9; i++) {
			
			//Lendo a linha do index atual
			String linha = this.arquivo.get(i);
			
			//Todos os comandos relevantes dessa linha
			ArrayList<String> comandos = comandos(linha);
			
			//Se n�o houver comandos importantes
			if(comandos.isEmpty()) {
				listaIndiceLinhas.add(i+1);
				continue;
			}
			
			//Daqui para baixo s� ser� executado quando houver comandos relevantes( } ou if ou else ou loop)
			
			//Verifica se na linha atual h� o comando }
			if(comandos.contains("}")) {
				
			}
			
		}
		
		return this.g;
	}

}
