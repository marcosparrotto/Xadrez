package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Piece;
import tabuleiro.Position;
import tabuleiro.Tabuleiro;
import xadrez.pieces.Bispo;
import xadrez.pieces.Cavalo;
import xadrez.pieces.Dama;
import xadrez.pieces.Peao;
import xadrez.pieces.Rei;
import xadrez.pieces.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PieceXadrez enPassant;
	private PieceXadrez promovida;

	private List<Piece> piecesNoTabuleiro = new ArrayList<>();
	private List<Piece> capturadas = new ArrayList<>();

	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.Brancas;
		setupInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PieceXadrez getEnPassant() {
		return enPassant;
	}
	
	public PieceXadrez getpromovida() {
		return promovida;
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = jogadorAtual == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	public PieceXadrez[][] getpieces() {
		PieceXadrez[][] matriz = new PieceXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PieceXadrez) tabuleiro.piece(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] possiveisMovimentos(PositionXadrez positionOrigem) {
		Position origem = positionOrigem.toposition();
		validarPositionOrigem(origem);
		return tabuleiro.piece(origem).possiveisMovimentos();
	}

	public PieceXadrez executarMovimentoXadrez(PositionXadrez positionOrigem, PositionXadrez positionDestino) {
		Position origem = positionOrigem.toposition();
		Position destino = positionDestino.toposition();
		validarpositionDestino(origem, destino);
		Piece pieceCapturada = realizeMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pieceCapturada);
			throw new XadrezException("Nao pode se colocar/continuar em Check!");
		}

		PieceXadrez pieceMovida = (PieceXadrez) tabuleiro.piece(destino);
		
		//Promover o peão
		promovida = null;
		if(pieceMovida instanceof Peao) {
			if((pieceMovida.getCor()==Cor.Brancas && destino.getLinha()==0) ||
					(pieceMovida.getCor()==Cor.Pretas && destino.getLinha()==7)) {
				promovida = (PieceXadrez) tabuleiro.piece(destino);
				promovida = alterarpiecePromovida("D");
			} 
		}
		
		check = testarCheck(corOponente(jogadorAtual));

		if (testarCheckMate(corOponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// enPassant
		if (pieceMovida instanceof Peao && (destino.getLinha() == origem.getLinha() + 2
				|| destino.getLinha() == origem.getLinha() - 2)) {
			enPassant = pieceMovida;
		} else {
			enPassant = null;
		}

		return (PieceXadrez) pieceCapturada;
	}
	
	public PieceXadrez alterarpiecePromovida(String type) {
		if(promovida == null) {
			throw new IllegalStateException("Nao ha peca promovida");
		}
		if(!type.equals("T") && !type.equals("C") && !type.equals("B") && !type.equals("D")) {
			return promovida;
		}
		
		Position pos = promovida.getpositionXadrez().toposition();
		Piece p = tabuleiro.removerPiece(pos);
		piecesNoTabuleiro.remove(p);
		
		PieceXadrez novapiece = novapiece(type, promovida.getCor());
		tabuleiro.lugarPiece(novapiece, pos);
		piecesNoTabuleiro.add(novapiece);
		
		return novapiece;
	}
	
	private PieceXadrez novapiece(String type, Cor cor) {
		if(type.equals("D")) return new Dama(tabuleiro, cor);
		if(type.equals("T")) return new Torre(tabuleiro, cor);
		if(type.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Bispo(tabuleiro, cor);
		
	}

	private Piece realizeMovimento(Position origem, Position destino) {
		PieceXadrez p = (PieceXadrez) tabuleiro.removerPiece(origem);
		p.incrementarcontagemMovimentos();
		Piece pieceCapturada = tabuleiro.removerPiece(destino);
		tabuleiro.lugarPiece(p, destino);
		if (pieceCapturada != null) {
			piecesNoTabuleiro.remove(pieceCapturada);
			capturadas.add(pieceCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Position origemTorreRei = new Position(origem.getLinha(), origem.getColuna() + 3);
			Position destinoTorreRei = new Position(destino.getLinha(), origem.getColuna() + 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(origemTorreRei);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPiece(torre, destinoTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Position origemTorreDama = new Position(origem.getLinha(), origem.getColuna() - 4);
			Position destinoTorreDama = new Position(destino.getLinha(), origem.getColuna() - 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(origemTorreDama);
			torre.incrementarcontagemMovimentos();
			tabuleiro.lugarPiece(torre, destinoTorreDama);
		}
		
		//enPassant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pieceCapturada ==null) {
				Position peaoposition = enPassant.getpositionXadrez().toposition();
				pieceCapturada = tabuleiro.removerPiece(peaoposition);
				piecesNoTabuleiro.remove(pieceCapturada);
				capturadas.add(pieceCapturada);
			}
		}

		return pieceCapturada;
	}

	private void desfazerMovimento(Position origem, Position destino, Piece pieceCapturada) {
		PieceXadrez p = (PieceXadrez) tabuleiro.removerPiece(destino);
		p.decrementarcontagemMovimentos();
		tabuleiro.lugarPiece(p, origem);
		if (pieceCapturada != null) {
			tabuleiro.lugarPiece(pieceCapturada, destino);
			capturadas.remove(pieceCapturada);
			piecesNoTabuleiro.add(pieceCapturada);
		}

		// Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Position origemTorreRei = new Position(origem.getLinha(), origem.getColuna() + 3);
			Position destinoTorreRei = new Position(destino.getLinha(), origem.getColuna() + 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(destinoTorreRei);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPiece(torre, origemTorreRei);
		}

		// Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Position origemTorreDama = new Position(origem.getLinha(), origem.getColuna() - 4);
			Position destinoTorreDama = new Position(destino.getLinha(), origem.getColuna() - 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(destinoTorreDama);
			torre.decrementarcontagemMovimentos();
			tabuleiro.lugarPiece(torre, origemTorreDama);
		}
		
		//enPassant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pieceCapturada == enPassant) {
				Position peaoposition = ((PieceXadrez) pieceCapturada).getpositionXadrez().toposition();
				if(p.getCor() == Cor.Brancas) {
					peaoposition = new Position(3, peaoposition.getColuna());
				} else {
					peaoposition = new Position(4, peaoposition.getColuna());
				}
				pieceCapturada = tabuleiro.removerPiece(destino);
				tabuleiro.lugarPiece(pieceCapturada, peaoposition);
			}
		}

	}

	private void validarPositionOrigem(Position position) {
		if (!tabuleiro.existePiece(position)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if (jogadorAtual != ((PieceXadrez) tabuleiro.piece(position)).getCor()) {
			throw new XadrezException("Escolha uma peca sua");
		}
		if (!tabuleiro.piece(position).haMovimentoPossivel()) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
	}

	private void validarpositionDestino(Position origem, Position destino) {
		if (!tabuleiro.piece(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover para posicao de destino");
		}
	}

	private void lugarNovapiece(char coluna, int linha, PieceXadrez piece) {
		tabuleiro.lugarPiece(piece, new PositionXadrez(coluna, linha).toposition());
		piecesNoTabuleiro.add(piece);
	}

	private Cor corOponente(Cor cor) {
		return cor == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}

	private PieceXadrez Rei(Cor cor) {
		List<Piece> list = piecesNoTabuleiro.stream().filter(x -> ((PieceXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof Rei) {
				return (PieceXadrez) p;
			}
		}
		throw new IllegalAccessError("Nao ha Rei da cor" + cor);
	}

	private boolean testarCheck(Cor cor) {
		Position reiposition = Rei(cor).getpositionXadrez().toposition();
		List<Piece> piecesOponentes = piecesNoTabuleiro.stream().filter(x -> ((PieceXadrez) x).getCor() == corOponente(cor))
				.collect(Collectors.toList());
		for (Piece p : piecesOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiposition.getLinha()][reiposition.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Piece> list = piecesNoTabuleiro.stream().filter(x -> ((PieceXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Position origem = ((PieceXadrez) p).getpositionXadrez().toposition();
						Position destino = new Position(i, j);
						Piece pieceCapturada = realizeMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pieceCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void setupInicial() {

		lugarNovapiece('a', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('b', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('c', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('d', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('e', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('f', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('g', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('h', 2, new Peao(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('a', 1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovapiece('b', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovapiece('c', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovapiece('d', 1, new Dama(tabuleiro, Cor.Brancas));
		lugarNovapiece('e', 1, new Rei(tabuleiro, Cor.Brancas, this));
		lugarNovapiece('f', 1, new Bispo(tabuleiro, Cor.Brancas));
		lugarNovapiece('g', 1, new Cavalo(tabuleiro, Cor.Brancas));
		lugarNovapiece('h', 1, new Torre(tabuleiro, Cor.Brancas));

		lugarNovapiece('a', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('b', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('c', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('d', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('e', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('f', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('g', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('h', 7, new Peao(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('a', 8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovapiece('b', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovapiece('c', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovapiece('d', 8, new Dama(tabuleiro, Cor.Pretas));
		lugarNovapiece('e', 8, new Rei(tabuleiro, Cor.Pretas, this));
		lugarNovapiece('f', 8, new Bispo(tabuleiro, Cor.Pretas));
		lugarNovapiece('g', 8, new Cavalo(tabuleiro, Cor.Pretas));
		lugarNovapiece('h', 8, new Torre(tabuleiro, Cor.Pretas));
	}
}
