package xadrez.peças;

import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Peão extends PeçaXadrez {

	public Peão(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posição p = new Posição(0, 0);

		// Brancas
		if (getCor() == Cor.Brancas) {
			
			// Andar uma casa para cima
			p.setValores(posição.getLinha() - 1, posição.getColuna());
			if (getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para cima
			p.setValores(posição.getLinha() - 2, posição.getColuna());
			Posição p2 = new Posição(posição.getLinha() - 1, posição.getColuna());
			if ((getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().posiçãoExiste(p2) && !getTabuleiro().existePeça(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peça diagonal cima + esquerda
			p.setValores(posição.getLinha() - 1, posição.getColuna() - 1);
			if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peça diagonal cima + direita
			p.setValores(posição.getLinha() - 1, posição.getColuna() + 1);
			if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} /* Cor Preta */ else {
			
			// Andar uma casa para baixo
			p.setValores(posição.getLinha() + 1, posição.getColuna());
			if (getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para baixo
			p.setValores(posição.getLinha() + 2, posição.getColuna());
			Posição p2 = new Posição(posição.getLinha() + 2, posição.getColuna());
			if ((getTabuleiro().posiçãoExiste(p) && !getTabuleiro().existePeça(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().posiçãoExiste(p2) && !getTabuleiro().existePeça(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peça diagonal baixo + esquerda
			p.setValores(posição.getLinha() + 1, posição.getColuna() - 1);
			if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar peça diagonal baixo + direita
			p.setValores(posição.getLinha() + 1, posição.getColuna() + 1);
			if (getTabuleiro().posiçãoExiste(p) && haPeçaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}

		return mat;
	}

}
