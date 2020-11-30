package app;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.Partida;
import xadrez.Pe�aXadrez;
import xadrez.Posi��oXadrez;
import xadrez.XadrezException;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		
		while(true) {
			try {
				UI.limparTela();
				UI.printTabuleiro(partida.getPe�as());
				System.out.println();
				System.out.print("Origem: ");
				Posi��oXadrez origem = UI.lendoPosi��oXadrez(sc);
				
				System.out.println();
				System.out.print("Destino: ");
				Posi��oXadrez destino = UI.lendoPosi��oXadrez(sc);
				
				Pe�aXadrez pe�aCapturada = partida.executarMovimentoXadrez(origem, destino);
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
	}

}
