package Plataforma.Console.Pilha;

import java.util.ArrayList;

public class Pilha {
	
	private ArrayList<String> pilha;
	
	public Pilha() {
		this.pilha = new ArrayList<String>();
	}
	
	public void addPilha(String dado) {
		this.pilha.add(dado);
	}
	
	public String popPilha() {
		if(this.pilha.isEmpty()) {
			return null;
		}
		String out = this.pilha.get(this.pilha.size()-1);
		this.pilha.remove(this.pilha.size()-1);
		return out;
	}
	
	public boolean estaVazia() {
		return this.pilha.isEmpty();
	}
	
	public String getTopo() {
		if(this.pilha.isEmpty()) {
			return null;
		}
		return this.pilha.get(this.pilha.size()-1);
	}

	@Override
	public String toString() {
		return  pilha.toString();
	}

}
