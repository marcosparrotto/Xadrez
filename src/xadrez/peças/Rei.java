package xadrez.pe�as;

import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Pe�aXadrez;

public class Rei extends Pe�aXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posi��o posi��o) {
		Pe�aXadrez p = (Pe�aXadrez) getTabuleiro().pe�a(posi��o);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posi��o p = new Posi��o(0, 0);

		// Acima
		p.setValores(posi��o.getLinha() - 1, posi��o.getColuna());
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo
		p.setValores(posi��o.getLinha() + 1, posi��o.getColuna());
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Esquerda
		p.setValores(posi��o.getLinha(), posi��o.getColuna() - 1);
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Direita
		p.setValores(posi��o.getLinha(), posi��o.getColuna() + 1);
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Esquerda
		p.setValores(posi��o.getLinha() -1, posi��o.getColuna() - 1);
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Acima + Direita
		p.setValores(posi��o.getLinha() -1, posi��o.getColuna() + 1);
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Esquerda
		p.setValores(posi��o.getLinha() +1, posi��o.getColuna() - 1);
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Baixo + Direita
		p.setValores(posi��o.getLinha() +1, posi��o.getColuna() + 1);
		if (getTabuleiro().posi��oExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}