package local.services;



import java.util.ArrayList;
import static local.utils.DataUtils.isMesmaData;
import static local.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import local.entities.Filme;
import local.entities.Locacao;
import local.entities.Usuario;
import local.exceptions.FilmeSemEstoqueException;
import local.exceptions.LocadoraException;



public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeDataDeRetorno() throws Exception {
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 1, 5.0);
		 List<Filme> filmes = new ArrayList();
		filmes.add(filme);
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verificacao
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
        // Metodo 1: Trabalhar com exceptions
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacaoFilmeSemEstoque() throws Exception{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		 List<Filme> filmes = new ArrayList();
		filmes.add(filme);
		//acao
		service.alugarFilme(usuario, filmes);
	}
	// Metodo 2: Trabalhar com exceptions
	@Test
	public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException{
		//cenario
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);
		 List<Filme> filmes = new ArrayList();
		filmes.add(filme);
		//acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Impossivel locar sem um usuário"));
		}
	}
	
// Metodo 3: Trabalhar com exceptions
	@Test
	public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Nenhum filme foi selecionado");
		
		//acao
		service.alugarFilme(usuario, null);
	}
        
        @Test
        public void testLocacaoMultiplosFilmes() throws FilmeSemEstoqueException, LocadoraException{
            	//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
                List<Filme> filmes = new ArrayList();
		filmes.add(new Filme("GoT", 5, 2.00));
		filmes.add(new Filme("Lost", 2, 2.00));
		filmes.add(new Filme("Supernatural", 2, 2.00));
		//acao
		Locacao x = service.alugarFilme(usuario,filmes);
		
		
                assertThat(x.getFilmes().size(), is(3));
        }
}
