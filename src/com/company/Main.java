package com.company;

import Plataforma.Console.GFC;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        GFC grafo = new GFC();
        ArrayList arquivo = grafo.lerArquivo("src/com/company/teste.txt");
//        System.out.println(arquivo);
//        System.out.println(grafo.encontrarMatches(arquivo));
        grafo.gerarNosComuns(arquivo);

    }
}
