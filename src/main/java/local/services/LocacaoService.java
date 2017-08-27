package local.services;

import static local.utils.DataUtils.adicionarDias;
import java.util.Date;
import local.entities.Filme;
import local.entities.Locacao;
import local.entities.Usuario;
import local.exceptions.FilmeSemEstoqueException;
import local.exceptions.LocadoraException;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {
        if (usuario == null) {
            throw new LocadoraException("Impossivel locar sem um usuário");
        }

        if (filme == null) {
            throw new LocadoraException("Nenhum filme foi selecionado");
        }

        if (filme.getEstoque() == 0) {
            throw new FilmeSemEstoqueException("Filme sem estoque");
        }

        Locacao locacao = new Locacao();
        locacao.setFilme(filme);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filme.getPrecoLocacao());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...	
        //TODO adicionar método para salvar
        return locacao;
    }
}
