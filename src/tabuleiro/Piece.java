package tabuleiro;

public abstract class Piece {
	protected Position position;
	private Tabuleiro tabuleiro;
	
	public Piece(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] possiveisMovimentos();
	
	public boolean possivelMovimento(Position position) {
		return possiveisMovimentos()[position.getLinha()][position.getColuna()];
	}

	public boolean haMovimentoPossivel() {
		boolean[][] mat = possiveisMovimentos();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
