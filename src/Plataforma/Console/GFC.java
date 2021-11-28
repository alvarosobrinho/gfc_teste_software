package Plataforma.Console;

import org.jgrapht.Graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class GFC {
    public void gerarNosComuns(ArrayList<String> arquivo){
        //lista responsável por criar as linhas de cada nó
        ArrayList<Integer> listaTemp = new ArrayList<Integer>();
        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        //pilha e o marcador do anterior
        Stack pilha = new Stack();
        int marcadorDaPilha = -1;
        int marcadorDaPilhaAntigo = -1;
        boolean pilhaM = false;


        //varrendo linhas do arquivo
        for (int i = 0; i < arquivo.size(); i++) {

            if(!(arquivo.get(i).contains("{")) && !(arquivo.get(i).contains("}"))){
                listaTemp.add((i+1));
            }
            if(encontrarEstruturaDeDecissao(arquivo.get(i))){

                //crio o nó com o array listaTemp.clone()
                if (!listaTemp.isEmpty()) {
                    System.out.println("No" + listaTemp.clone());
                    g.addVertex(listaTemp.toString());
                }
                listaTemp.clear();
                //adiciono a linha de decisão
                listaTemp.add((i+1));
                //crio o nó com o array listaTemp.clone()
                //criar aresta do nó anterior para esse
//                System.out.println("No" + listaTemp.clone());
                listaTemp.clear();
                //adicionar linha na Pilha
                if(marcadorDaPilha == -1){
                    marcadorDaPilha = i+1;
                }
                pilha.push(i+1);
            }
            if((arquivo.get(i).contains("}"))){


                //remover linha da pilha
                if(!pilha.isEmpty()){
                    pilha.pop();
                }
                String tempVertex = listaTemp.toString();
                g.addVertex(tempVertex);
                listaTemp.clear();

                if(pilha.isEmpty() && !(i == arquivo.size()-1)){
                    //esse no se liga ao nó que tem como  início a linha da variável marcadorDaPilha
                    System.out.println("Pilha vazia, nó de referência começa com a linha: " + marcadorDaPilha);
                    ArrayList<Integer> aux = new ArrayList<Integer>();
                    ArrayList<Integer> aux2 = new ArrayList<Integer>();
                    aux.add(marcadorDaPilha);
                    g.addVertex(aux.toString());
                    g.addEdge(aux.toString(), tempVertex);
                    if(marcadorDaPilhaAntigo != -1){
                        aux2.add(marcadorDaPilhaAntigo);

//                        if(arquivo.get(marcadorDaPilhaAntigo - 1).contains("while ") || arquivo.get(marcadorDaPilhaAntigo -1).contains("while(")) {
//                            System.out.println("No" + listaTemp.clone());
//                            g.addEdge(tempVertex, aux2.toString());
//                            System.out.println(tempVertex);
//                            System.out.println(aux2.toString());
//                        }
                        g.addEdge(aux2.toString(), aux.toString());
                    }

                    marcadorDaPilhaAntigo = marcadorDaPilha;
                    marcadorDaPilha = -1;
                    pilhaM = true;

                }
                //crio o nó com o array listaTemp.clone()
                System.out.println("No" + listaTemp.clone());

            }
//            if(pilhaM && )

        }
        System.out.println(g.toString());


    }
    public boolean encontrarEstruturaDeDecissao(String linha){
        boolean contem = false;
        if (linha.contains("if ") || linha.contains("if(") || linha.contains("else {") || linha.contains("else{") ||
                linha.contains("while ") || linha.contains("while(") || linha.contains("for ") || linha.contains("for(")){
            contem = true;
        }
        return contem;
    }
    public ArrayList<String> lerArquivo(String caminho){
        ArrayList<String> arquivo = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(caminho));

            String linha = br.readLine();

            while (linha != null) {
                arquivo.add(linha);
                linha = br.readLine();
            }

        } catch (IOException e) {
            arquivo = null;
        }
        return arquivo;
    }
}
