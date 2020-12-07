package xadrez.pieces;

import tabuleiro.Position;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PieceXadrez;

public class Torre extends PieceXadrez{

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
		
		Position p = new Position(0,0);
		
		//Acima
		p.setValores(position.getLinha() -1, position.getColuna());
		while(getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		if(getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Esquerda
		p.setValores(position.getLinha(), position.getColuna()-1);
		while(getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()-1);
		}
		if(getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Direita
		p.setValores(position.getLinha(), position.getColuna()+1);
		while(getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()+1);
		}
		if(getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		//Baixo
		p.setValores(position.getLinha() +1, position.getColuna());
		while(getTabuleiro().positionExiste(p) && !getTabuleiro().existePiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()+1);
		}
		if(getTabuleiro().positionExiste(p) && hapieceOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
			
		
		return mat;
	}
}
