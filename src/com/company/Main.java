package com.company;

import Plataforma.Console.GFC;
import Plataforma.Console.GFC2;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

    	GFC2 grafo = new GFC2("src/com/company/iftesting.txt");
    	
    	grafo.gerarGrafo();
    	
    	
    	/*
    	GFC grafo = new GFC();
        ArrayList arquivo = grafo.lerArquivo("src/com/company/testing1.txt");
        System.out.println(arquivo);
        System.out.println(grafo.encontrarMatches(arquivo));
        grafo.gerarNosComuns(arquivo);
        */

    }
}
