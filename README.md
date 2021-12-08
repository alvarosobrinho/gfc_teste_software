<h1 align="center">GFC Teste de Software</h1>
<p align="center">Projeto Realizado pelos alunos da UFAPE referente da disciplina Teste de Software - 2020.2</p>

### Overview
Devido a um problema encontrado, dois códigos foram feitos, Main.java e Main2.java(Ambos se encontram dentro de scr/com/company/).<br/>

No Main.java está a implementação de um GFC incompleto, funcionando para o caso if, else if e else. Os casos de loop não foram implementados devido ao tempo.

No Main2.java está a implementação dos algoritmos Todos-Nós e Todas-Arestas, através do uso de um grafo com arestas e nós adicionados manualmente. 

### Pré-Requisitos

Para utilizar o GFC incompleto, é necessário um arquivo txt com as linhas de um programa Java, e para fazer o caso if, else if e else funcionar o formato deve ser:

```bash
if(condicao){
  codigo
}else if(condicao){//Criado na mesma linha que a chave do if vai ser fechada
  codigo
}else{//Mesmo esquema do else if
  codigo
}
```
E com esse arquivo, criar uma nova instância da GFC2 e utilizar o método gerar grafo. 

```bash
GFC2 grafo = new GFC2("src/com/company/iftesting.txt");
grafo.gerarGrafo();
```

Para utilizar o Todos-Nós e Todas-Arestas, deve criar um grafo manualmente adicionando seus nós e arestas e após isso deve executar o algoritmo desejado:

```bash
Grafo g = new Grafo();
g.addNo("0");
g.addNo("1");
g.addAresta("0", "1");
ArrayList<ArrayList<No>> caminhos = g.todosNos(); ou ArrayList<ArrayList<No>> caminhos2 = g.todasArestas();
```
