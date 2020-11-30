package xadrez;

import tabuleiro.Pe�a;
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
	
	public Pe�aXadrez executarMovimentoXadrez(Posi��oXadrez posi��oOrigem, Posi��oXadrez posi��oDestino) {
		Posi��o origem = posi��oOrigem.toPosi��o();
		Posi��o destino = posi��oDestino.toPosi��o();
		validarPosi��oOrigem(origem);
		Pe�a pe�aCapturada = realizeMovimento(origem, destino);
		return (Pe�aXadrez) pe�aCapturada;
	}
	
	private Pe�a realizeMovimento(Posi��o origem, Posi��o destino) {
		Pe�a p = tabuleiro.removerPe�a(origem);
		Pe�a pe�aCapturada = tabuleiro.removerPe�a(destino);
		tabuleiro.lugarPe�a(p, destino);
		return pe�aCapturada;
	}
	
	private void validarPosi��oOrigem(Posi��o posi��o) {
		if(!tabuleiro.existePe�a(posi��o)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if(!tabuleiro.pe�a(posi��o).haMovimentoPossivel() ) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
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
