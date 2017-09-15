package local.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import local.entities.Filme;
import local.entities.Locacao;
import local.entities.Usuario;
import local.exceptions.FilmeSemEstoqueException;
import local.exceptions.LocadoraException;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.*;
import org.junit.runners.Parameterized.*;

/**
 * Esta classe apresenta os testes de desconto da LocacaoServiceTest refatorados
 * utilizando parametrização para reduzir a redundância de métodos similares
 *
 * @author Angelo
 */
@RunWith(Parameterized.class)
public class DevolucaoFilmeParametrizadoTest {

    private final LocacaoService locacaoService;

    public DevolucaoFilmeParametrizadoTest() {
        locacaoService = new LocacaoService();
    }

    /**
     * Método com a declaração dos parâmetros de execução Este deve ser estático
     * pelo fato de precisar ser acessado pelo JUnit antes da execução do teste
     *
     * @return
     */
    @Parameters
    public static List<Object[]> parametrosDeExecucao() {

        Filme filme1 = new Filme("Piratas do Vale do Silício", 4, 4.0);
        Filme filme2 = new Filme("Jobs", 3, 4.0);
        Filme filme3 = new Filme("Duro de Matar 4", 11, 4.0);
        Filme filme4 = new Filme("Lagoa Azul", 2, 4.0);
        Filme filme5 = new Filme("A Trança dos Carecas", 2, 4.0);
        Filme filme6 = new Filme("Diomelo e seus dois maridos", 2, 4.0);
        // Cada array representa um caso de teste
        return Arrays.asList(new Object[][]{
            {Arrays.asList(filme1, filme2), 8.0},
            {Arrays.asList(filme1, filme2, filme3), 11.0},
            {Arrays.asList(filme1, filme2, filme3, filme4), 13.0},
            {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 13.0},
            {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 17.0}
        });
    }

    // Definido atributo como parâmetro e o ID do mesmo
    @Parameter(0)
    public List<Filme> filmes;// Parameter deve ser público para acesso pelo JUnit
    @Parameter(1)
    public Double valorLocacao;// Parameter deve ser público para acesso pelo JUnit

    @Test
    public void deveVerificarValorDeDevolucao() throws FilmeSemEstoqueException,
            LocadoraException {
        //Cenário
        Usuario usuario = new Usuario("Angelo");
        //Ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
        //Validação
        assertThat(locacao.getValor(), is(valorLocacao));
    }
}
