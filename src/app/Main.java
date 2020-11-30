package app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.Partida;
import xadrez.Pe�aXadrez;
import xadrez.Posi��oXadrez;
import xadrez.XadrezException;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		List<Pe�aXadrez> capturadas = new ArrayList<>();
		
		while(true) {
			try {
				UI.limparTela();
				UI.printPartida(partida, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				Posi��oXadrez origem = UI.lendoPosi��oXadrez(sc);
				
				boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(partida.getPe�as(), possiveisMovimentos);		
				System.out.println();
				System.out.print("Destino: ");
				Posi��oXadrez destino = UI.lendoPosi��oXadrez(sc);
				
				Pe�aXadrez pe�aCapturada = partida.executarMovimentoXadrez(origem, destino);
				if(pe�aCapturada != null) {
					capturadas.add(pe�aCapturada);
				}
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
