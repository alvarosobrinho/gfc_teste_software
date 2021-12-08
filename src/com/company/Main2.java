package com.company;

import java.util.ArrayList;

import Plataforma.Console.Grafo.Grafo;
import Plataforma.Console.Grafo.No;

public class Main2 {
	
	public static void main(String[] args) {
	       
		Grafo g = new Grafo();
		
		g.addNo("0");
		g.addNo("1");
		g.addNo("2");
		g.addNo("3");
		g.addNo("4");
		g.addNo("5");
		g.addNo("6");
		g.addNo("7");
		g.addNo("8");
		g.addNo("9");
		g.addNo("10");
		
		g.addAresta("0", "1");
		g.addAresta("1", "2");
		g.addAresta("2", "1");
		g.addAresta("1", "3");
		g.addAresta("3", "4");
		g.addAresta("3", "5");
		g.addAresta("4", "6");
		g.addAresta("5", "6");
		g.addAresta("6", "7");
		g.addAresta("7", "6");
		g.addAresta("6", "8");
		g.addAresta("8", "9");
		g.addAresta("8", "10");
		g.addAresta("9", "10");
		
		ArrayList<ArrayList<No>> caminhos = g.todosNos();
		
		System.out.println("Todos-Nós:");
		for(int i=0; i<caminhos.size(); i++) {
			String print = "Caminho "+ String.valueOf(i) + ": ";
			for(int j=0; j<caminhos.get(i).size();j++) {
				print = print +" " + String.valueOf(caminhos.get(i).get(j).getDado());
			}
			System.out.println(print);
		}
		
		
		ArrayList<ArrayList<No>> caminhos2 = g.todasArestas();
		
		System.out.println("\n\nTodas-Arestas:");
		for(int i=0; i<caminhos2.size();i++) {
			String print = "Caminho "+ String.valueOf(i) + ": ";
			for(int j=0; j<caminhos2.get(i).size();j++) {
				print = print +" " + String.valueOf(caminhos2.get(i).get(j).getDado());
			}
			System.out.println(print);
		}

    }

}
