package tabuleiro;

public abstract class Pe�a {
	protected Posi��o posi��o;
	private Tabuleiro tabuleiro;
	
	public Pe�a(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] possiveisMovimentos();
	
	public boolean possivelMovimento(Posi��o posi��o) {
		return possiveisMovimentos()[posi��o.getLinha()][posi��o.getColuna()];
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
