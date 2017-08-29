package local.services;

import java.util.ArrayList;
import java.util.Arrays;
import static local.utils.DataUtils.isMesmaData;
import static local.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import static org.junit.Assert.*;
//import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import local.entities.Filme;
import local.entities.Locacao;
import local.entities.Usuario;
import local.exceptions.FilmeSemEstoqueException;
import local.exceptions.LocadoraException;
import org.junit.Before;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    LocacaoService locacaoService;

    @Before
    public void setup() {
        locacaoService = new LocacaoService();
    }

    @Test
    public void testeDataDeRetorno() throws Exception {
        //cenario
        locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 1, 5.0);
        List<Filme> filmes = new ArrayList();
        filmes.add(filme);
        //acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        //verificacao
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    // Metodo 1: Trabalhar com exceptions
    @Test(expected = FilmeSemEstoqueException.class)
    public void testLocacaoFilmeSemEstoque() throws Exception {
        //cenario
        locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 2", 0, 4.0);
        List<Filme> filmes = new ArrayList();
        filmes.add(filme);
        //acao
        locacaoService.alugarFilme(usuario, filmes);
    }
    // Metodo 2: Trabalhar com exceptions

    @Test
    public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException {
        //cenario
        locacaoService = new LocacaoService();
        Filme filme = new Filme("Filme 2", 1, 4.0);
        List<Filme> filmes = new ArrayList();
        filmes.add(filme);
        //acao
        try {
            locacaoService.alugarFilme(null, filmes);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Impossivel locar sem um usuário"));
        }
    }

// Metodo 3: Trabalhar com exceptions
    @Test
    public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Nenhum filme foi selecionado");

        //acao
        locacaoService.alugarFilme(usuario, null);
    }

    @Test
    public void testLocacaoMultiplosFilmes() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        locacaoService = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("GoT", 5, 2.00));
        filmes.add(new Filme("Lost", 2, 2.00));
        filmes.add(new Filme("Supernatural", 2, 2.00));
        //acao
        Locacao x = locacaoService.alugarFilme(usuario, filmes);
        //validação
        assertThat(x.getFilmes().size(), is(3));
    }

    @Test
    public void devePagar75PorcentoNoFilme3() throws FilmeSemEstoqueException,
            LocadoraException {
        //Cenário
        Usuario usuario = new Usuario("Angelo");
        List<Filme> filmes = Arrays.asList(
                new Filme("Piratas do Vale do Silício", 4, 4.0),
                new Filme("Jobs", 3, 4.0),
                new Filme("Duro de Matar 4", 11, 4.0));
        //Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        //Validação
        assertEquals(11,locacao.getValor(), 0.01);
    }

    @Test
    public void devePagar50PorcentoNoFilme4() throws FilmeSemEstoqueException,
            LocadoraException {
        //Cenário
        Usuario usuario = new Usuario("Angelo");
        List<Filme> filmes = Arrays.asList(
                new Filme("Piratas do Vale do Silício", 4, 4.0),
                new Filme("Jobs", 3, 4.0),
                new Filme("Duro de Matar 4", 11, 4.0),
                new Filme("Lagoa Azul", 2, 4.0));
        //Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        //Validação
        assertEquals(13, locacao.getValor(), 0.01);
        //assertThat(locacao.getValor(), is(13));
    }

    @Test
    public void naoDevePagarPeloFilme5() throws FilmeSemEstoqueException,
            LocadoraException {
        //Cenário
        Usuario usuario = new Usuario("Angelo");
        List<Filme> filmes = Arrays.asList(
                new Filme("Piratas do Vale do Silício", 4, 4.0),
                new Filme("Jobs", 3, 4.0),
                new Filme("Duro de Matar 4", 11, 4.0),
                new Filme("Lagoa Azul", 2, 4.0),
                new Filme("A Trança dos Carecas", 2, 4.0));

        //Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        //Validação

        //assertEquals(13, locacao.getValor(), 0.01);
        assertThat(locacao.getValor(), is(13.0));
    }
}
