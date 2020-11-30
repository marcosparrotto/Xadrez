package xadrez.peças;

import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posição posição) {
		PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		// Acima
		p.setValores(posição.getLinha() - 1, posição.getColuna());
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo
		p.setValores(posição.getLinha() + 1, posição.getColuna());
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Esquerda
		p.setValores(posição.getLinha(), posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Direita
		p.setValores(posição.getLinha(), posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Esquerda
		p.setValores(posição.getLinha() -1, posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Direita
		p.setValores(posição.getLinha() -1, posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Esquerda
		p.setValores(posição.getLinha() +1, posição.getColuna() - 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Direita
		p.setValores(posição.getLinha() +1, posição.getColuna() + 1);
		if (getTabuleiro().posiçãoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}