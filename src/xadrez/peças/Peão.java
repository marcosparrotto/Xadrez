package xadrez.pe�as;

import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Pe�aXadrez;

public class Pe�o extends Pe�aXadrez {

	public Pe�o(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posi��o p = new Posi��o(0, 0);

		// Brancas
		if (getCor() == Cor.Brancas) {
			
			// Andar uma casa para cima
			p.setValores(posi��o.getLinha() - 1, posi��o.getColuna());
			if (getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para cima
			p.setValores(posi��o.getLinha() - 2, posi��o.getColuna());
			Posi��o p2 = new Posi��o(posi��o.getLinha() - 1, posi��o.getColuna());
			if ((getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().posi��oExiste(p2) && !getTabuleiro().existePe�a(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar pe�a diagonal cima + esquerda
			p.setValores(posi��o.getLinha() - 1, posi��o.getColuna() - 1);
			if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar pe�a diagonal cima + direita
			p.setValores(posi��o.getLinha() - 1, posi��o.getColuna() + 1);
			if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} /* Cor Preta */ else {
			
			// Andar uma casa para baixo
			p.setValores(posi��o.getLinha() + 1, posi��o.getColuna());
			if (getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para baixo
			p.setValores(posi��o.getLinha() + 2, posi��o.getColuna());
			Posi��o p2 = new Posi��o(posi��o.getLinha() + 2, posi��o.getColuna());
			if ((getTabuleiro().posi��oExiste(p) && !getTabuleiro().existePe�a(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().posi��oExiste(p2) && !getTabuleiro().existePe�a(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar pe�a diagonal baixo + esquerda
			p.setValores(posi��o.getLinha() + 1, posi��o.getColuna() - 1);
			if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar pe�a diagonal baixo + direita
			p.setValores(posi��o.getLinha() + 1, posi��o.getColuna() + 1);
			if (getTabuleiro().posi��oExiste(p) && haPe�aOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}

		return mat;
	}

}
