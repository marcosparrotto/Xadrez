package xadrez.peças;

import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Torre extends PeçaXadrez{

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}
}
