package app;

import java.util.Scanner;

import xadrez.Partida;
import xadrez.PeçaXadrez;
import xadrez.PosiçãoXadrez;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		
		while(true) {
			UI.printTabuleiro(partida.getPeças());
			System.out.println();
			System.out.print("Origem: ");
			PosiçãoXadrez origem = UI.lendoPosiçãoXadrez(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			PosiçãoXadrez destino = UI.lendoPosiçãoXadrez(sc);
			
			PeçaXadrez peçaCapturada = partida.executarMovimentoXadrez(origem, destino);
			
		}
	}

}
