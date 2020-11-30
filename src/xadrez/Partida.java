package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleiro.Pe�a;
import tabuleiro.Posi��o;
import tabuleiro.Tabuleiro;
import xadrez.pe�as.Rei;
import xadrez.pe�as.Torre;

public class Partida {
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<Pe�a> pe�asNoTabuleiro = new ArrayList<>();
	private List<Pe�a> capturadas = new ArrayList<>();
	
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
	
	public Pe�aXadrez[][] getPe�as(){
		Pe�aXadrez[][] matriz = new Pe�aXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas();i++) {
			for(int j=0; j<tabuleiro.getColunas();j++) {
				matriz[i][j] = (Pe�aXadrez) tabuleiro.pe�a(i,j);
			}
		}
		return matriz;
	}
	
	public boolean[][] possiveisMovimentos(Posi��oXadrez posi��oOrigem){
		Posi��o origem = posi��oOrigem.toPosi��o();
		validarPosi��oOrigem(origem);
		return tabuleiro.pe�a(origem).possiveisMovimentos();
	}
	
	public Pe�aXadrez executarMovimentoXadrez(Posi��oXadrez posi��oOrigem, Posi��oXadrez posi��oDestino) {
		Posi��o origem = posi��oOrigem.toPosi��o();
		Posi��o destino = posi��oDestino.toPosi��o();
		validarPosi��oDestino(origem, destino);
		Pe�a pe�aCapturada = realizeMovimento(origem, destino);
		proximoTurno();
		return (Pe�aXadrez) pe�aCapturada;
	}
	
	private Pe�a realizeMovimento(Posi��o origem, Posi��o destino) {
		Pe�a p = tabuleiro.removerPe�a(origem);
		Pe�a pe�aCapturada = tabuleiro.removerPe�a(destino);
		tabuleiro.lugarPe�a(p, destino);
		if(pe�aCapturada != null) {
			pe�asNoTabuleiro.remove(pe�aCapturada);
			capturadas.add(pe�aCapturada);
		}
		return pe�aCapturada;
	}
	
	private void validarPosi��oOrigem(Posi��o posi��o) {
		if(!tabuleiro.existePe�a(posi��o)) {
			throw new XadrezException("Nao ha peca nesta posicao");
		}
		if(jogadorAtual != ((Pe�aXadrez) tabuleiro.pe�a(posi��o)).getCor()) {
			throw new XadrezException("Escolha uma peca sua");
		}
		if(!tabuleiro.pe�a(posi��o).haMovimentoPossivel() ) {
			throw new XadrezException("Nao ha movimentos possiveis para peca escolhida");
		}
	}
	
	private void validarPosi��oDestino(Posi��o origem, Posi��o destino)  {
		if(!tabuleiro.pe�a(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover para posicao de destino");
		}
	}
	
	private void lugarNovaPe�a(char coluna, int linha, Pe�aXadrez pe�a) {
		tabuleiro.lugarPe�a(pe�a, new Posi��oXadrez(coluna, linha).toPosi��o());
		pe�asNoTabuleiro.add(pe�a);
	}
	
	private void setupInicial() {
		lugarNovaPe�a('a',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('h',1, new Torre(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('e',1, new Rei(tabuleiro, Cor.Brancas));
		lugarNovaPe�a('a',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('h',8, new Torre(tabuleiro, Cor.Pretas));
		lugarNovaPe�a('e',8, new Rei(tabuleiro, Cor.Pretas));
	}
}
