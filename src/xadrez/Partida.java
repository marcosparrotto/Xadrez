package xadrez;

import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class Partida {
	private Tabuleiro tabuleiro;
	
	public Partida() {
		tabuleiro = new Tabuleiro(8,8);
		setupInicial();
	}
	
	public PeçaXadrez[][] getPeças(){
		PeçaXadrez[][] matriz = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas();i++) {
			for(int j=0; j<tabuleiro.getColunas();j++) {
				matriz[i][j] = (PeçaXadrez) tabuleiro.peça(i,j);
			}
		}
		return matriz;
	}
	
	private void setupInicial() {
		tabuleiro.lugarPeça(new Torre(tabuleiro, Cor.Brancas), new Posição(1,1));
		tabuleiro.lugarPeça(new Rei(tabuleiro, Cor.Pretas), new Posição(1,1));
	}
}
