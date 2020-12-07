package xadrez.pieces;

import tabuleiro.Position;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PieceXadrez;

public class Peao extends PieceXadrez {

	private Partida partida;

	public Peao(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Position p = new Position(0, 0);

		// Brancas
		if (getCor() == Cor.Brancas) {

			// Andar uma casa para cima
			p.setValores(position.getLinha() - 1, position.getColuna());
			if (getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para cima
			p.setValores(position.getLinha() - 2, position.getColuna());
			Position p2 = new Position(position.getLinha() - 1, position.getColuna());
			if ((getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().positionExiste(p2) && !getTabuleiro().existePiece(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar piece diagonal cima + esquerda
			p.setValores(position.getLinha() - 1, position.getColuna() - 1);
			if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar piece diagonal cima + direita
			p.setValores(position.getLinha() - 1, position.getColuna() + 1);
			if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// enPassant Brancas
			if (position.getLinha() == 3) {
				Position esquerda = new Position(position.getLinha(), position.getColuna() - 1);
				if (getTabuleiro().positionExiste(esquerda) && hapieceOponente(esquerda)
						&& getTabuleiro().piece(esquerda) == partida.getEnPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Position direita = new Position(position.getLinha(), position.getColuna() + 1);
				if (getTabuleiro().positionExiste(direita) && hapieceOponente(direita)
						&& getTabuleiro().piece(direita) == partida.getEnPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}

		} /* Cor Preta */ else {

			// Andar uma casa para baixo
			p.setValores(position.getLinha() + 1, position.getColuna());
			if (getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Andar duas casas para baixo
			p.setValores(position.getLinha() + 2, position.getColuna());
			Position p2 = new Position(position.getLinha() + 2, position.getColuna());
			if ((getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p) && getcontagemMovimentos() == 0)
					&& (getTabuleiro().positionExiste(p2) && !getTabuleiro().existePiece(p2))) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar piece diagonal baixo + esquerda
			p.setValores(position.getLinha() + 1, position.getColuna() - 1);
			if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Capturar piece diagonal baixo + direita
			p.setValores(position.getLinha() + 1, position.getColuna() + 1);
			if (getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// enPassant Pretas
			if (position.getLinha() == 4) {
				Position esquerda = new Position(position.getLinha(), position.getColuna() - 1);
				if (getTabuleiro().positionExiste(esquerda) && hapieceOponente(esquerda)
						&& getTabuleiro().piece(esquerda) == partida.getEnPassant()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Position direita = new Position(position.getLinha(), position.getColuna() + 1);
				if (getTabuleiro().positionExiste(direita) && hapieceOponente(direita)
						&& getTabuleiro().piece(direita) == partida.getEnPassant()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}

		return mat;
	}

}
