package app;

import java.util.Scanner;

import xadrez.Partida;
import xadrez.Pe�aXadrez;
import xadrez.Posi��oXadrez;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		
		while(true) {
			UI.printTabuleiro(partida.getPe�as());
			System.out.println();
			System.out.print("Origem: ");
			Posi��oXadrez origem = UI.lendoPosi��oXadrez(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			Posi��oXadrez destino = UI.lendoPosi��oXadrez(sc);
			
			Pe�aXadrez pe�aCapturada = partida.executarMovimentoXadrez(origem, destino);
			
		}
	}

}
