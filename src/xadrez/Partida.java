package xadrez;

import tabuleiro.Peça;
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
	
	public PeçaXadrez executarMovimentoXadrez(PosiçãoXadrez posiçãoOrigem, PosiçãoXadrez posiçãoDestino) {
		Posição origem = posiçãoOrigem.toPosição();
		Posição destino = posiçãoDestino.toPosição();
		validarPosiçãoOrigem(origem);
		Peça peçaCapturada = realizeMovimento(origem, destino);
		return (PeçaXadrez) peçaCapturada;
	}
	
	private Peça realizeMovimento(Posição origem, Posição destino) {
		Peça p = tabuleiro.removerPeça(origem);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.lugarPeça(p, destino);
		return peçaCapturada;
	}
	
	private void validarPosiçãoOrigem(Posição posição) {
		if(!tabuleiro.existePeça(posição)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if(!tabuleiro.peça(posição).haMovimentoPossivel() ) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
	}
	
	private void lugarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarPeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
	}
	
	private void setupInicial() {
		lugarNovaPeça('a',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeça('h',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeça('e',1, new Rei(tabuleiro, Cor.Brancas));
		lugarNovaPeça('a',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeça('h',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeça('e',8, new Rei(tabuleiro, Cor.Pretas));
	}
}
