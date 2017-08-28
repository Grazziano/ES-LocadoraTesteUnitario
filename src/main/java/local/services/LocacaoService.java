package local.services;

import static local.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import local.entities.Filme;
import local.entities.Locacao;
import local.entities.Usuario;
import local.exceptions.FilmeSemEstoqueException;
import local.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		if(usuario == null) {
			throw new LocadoraException("Impossivel locar sem um usuário");
		}
		
		if(filmes == null) {
			throw new LocadoraException("Nenhum filme foi selecionado");
		}
                Locacao locacao = new Locacao();
                Double valorTotal = 0d;
		for(Filme filme: filmes){
		if(filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException("Filme sem estoque");
		}
		locacao.addFilme(filme);		
		valorTotal += filme.getPrecoLocacao();
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
                //.....
                }
                locacao.setValor(valorTotal);
                locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		return locacao;
	}
}