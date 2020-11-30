package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleiro.Peça;
import tabuleiro.Posição;
import tabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> capturadas = new ArrayList<>();
	
	public Partida() {
		tabuleiro = new Tabuleiro(8,8);
		turno =1;
		jogadorAtual = Cor.Brancas;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = jogadorAtual == Cor.Brancas ? Cor.Pretas : Cor.Brancas;
	}
	
	public PeçaXadrez[][] getPeças(){
		PeçaXadrez[][] matriz = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas();i++) {
			for(int j=0; j<tabuleiro.getColunas();j++) {
				matriz[i][j] = (PeçaXadrez) tabuleiro.peça(i,j);
			}
		}
		return matriz;
	}
	
	public boolean[][] possiveisMovimentos(PosiçãoXadrez posiçãoOrigem){
		Posição origem = posiçãoOrigem.toPosição();
		validarPosiçãoOrigem(origem);
		return tabuleiro.peça(origem).possiveisMovimentos();
	}
	
	public PeçaXadrez executarMovimentoXadrez(PosiçãoXadrez posiçãoOrigem, PosiçãoXadrez posiçãoDestino) {
		Posição origem = posiçãoOrigem.toPosição();
		Posição destino = posiçãoDestino.toPosição();
		validarPosiçãoDestino(origem, destino);
		Peça peçaCapturada = realizeMovimento(origem, destino);
		proximoTurno();
		return (PeçaXadrez) peçaCapturada;
	}
	
	private Peça realizeMovimento(Posição origem, Posição destino) {
		Peça p = tabuleiro.removerPeça(origem);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.lugarPeça(p, destino);
		if(peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			capturadas.add(peçaCapturada);
		}
		return peçaCapturada;
	}
	
	private void validarPosiçãoOrigem(Posição posição) {
		if(!tabuleiro.existePeça(posição)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if(jogadorAtual != ((PeçaXadrez) tabuleiro.peça(posição)).getCor()) {
			throw new XadrezException("Escolha uma peca sua");
		}
		if(!tabuleiro.peça(posição).haMovimentoPossivel() ) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
	}
	
	private void validarPosiçãoDestino(Posição origem, Posição destino)  {
		if(!tabuleiro.peça(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover para posicao de destino");
		}
	}
	
	private void lugarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.lugarPeça(peça, new PosiçãoXadrez(coluna, linha).toPosição());
		peçasNoTabuleiro.add(peça);
	}
	
	private void setupInicial() {
		lugarNovaPeça('a',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeça('h',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPeça('e',1, new Rei(tabuleiro, Cor.Brancas));
		lugarNovaPeça('a',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeça('h',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPeça('e',8, new Rei(tabuleiro, Cor.Pretas));
	}
}
