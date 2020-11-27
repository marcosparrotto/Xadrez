package xadrez;

import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.pe�as.Rei;
import xadrez.pe�as.Torre;

public class Partida {
	private Tabuleiro tabuleiro;
	
	public Partida() {
		tabuleiro = new Tabuleiro(8,8);
		setupInicial();
	}
	
	public Pe�aXadrez[][] getPe�as(){
		Pe�aXadrez[][] matriz = new Pe�aXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas();i++) {
			for(int j=0; j<tabuleiro.getColunas();j++) {
				matriz[i][j] = (Pe�aXadrez) tabuleiro.pe�a(i,j);
			}
		}
		return matriz;
	}
	
	private void lugarNovaPe�a(char coluna, int linha, Pe�aXadrez pe�a) {
		tabuleiro.lugarPe�a(pe�a, new Posi��oXadrez(coluna, linha).toPosi��o());
	}
	
	private void setupInicial() {
		lugarNovaPe�a('a',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('h',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('e',1, new Rei(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('a',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('h',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('e',8, new Rei(tabuleiro, Cor.Pretas));
	}
}
