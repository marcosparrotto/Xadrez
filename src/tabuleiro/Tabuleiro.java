package tabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Piece[][] pieces;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas <1 || colunas <1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro, necessario pelo menos 1 linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pieces = new Piece[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Piece piece(int linha, int coluna) {
		if(!positionExiste(linha, coluna)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return pieces[linha][coluna];
	}
	
	public Piece piece(Position position) {
		return piece(position.getLinha(),position.getColuna());
	}
	
	public void lugarPiece(Piece piece, Position position) {
		if(existePiece(position)) {
			throw new TabuleiroException("Já tem uma peca nessa posicao");
		}
		pieces[position.getLinha()][position.getColuna()] = piece;
		piece.position = position;
	}
	
	public Piece removerPiece(Position position) {
		if(!positionExiste(position)) {
			throw new TabuleiroException("Posicao invalida");
		}
		if(piece(position)==null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getLinha()][position.getColuna()] = null;
		return aux;
	}
	
	public boolean positionExiste(int linha, int coluna) {
		return linha>=0 && linha<linhas && coluna >=0 && coluna<colunas;
	}
	
	public boolean positionExiste(Position position) {
		return positionExiste(position.getLinha(), position.getColuna());
	}
	
	public boolean existePiece(Position position) {
		if(!positionExiste(position)) {
			throw new TabuleiroException("Posicao invalida");
		}
		return piece(position) != null;
	}
}
