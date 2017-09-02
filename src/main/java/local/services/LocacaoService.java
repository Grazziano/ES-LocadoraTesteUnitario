package local.services;

import java.util.Calendar;
import static local.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import local.entities.Filme;
import local.entities.Locacao;
import local.entities.Usuario;
import local.exceptions.FilmeSemEstoqueException;
import local.exceptions.LocadoraException;
import local.utils.DataUtils;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
        if (usuario == null) {
            throw new LocadoraException("Impossivel locar sem um usuário");
        }
        if (filmes == null) {
            throw new LocadoraException("Nenhum filme foi selecionado");
        }
        Locacao locacao = new Locacao();
        Double valorTotal = 0d;
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            if (filme.getEstoque() == 0) {
                throw new FilmeSemEstoqueException("Filme sem estoque");
            }
            double valorFilme = filme.getPrecoLocacao();
            switch (i) {
                case 2:
                    valorFilme = valorFilme * 0.75;
                    break;
                case 3:
                    valorFilme = valorFilme * 0.50;
                    break;
                case 4:
                    valorFilme = 0;
                    break;
                default:
            }
            locacao.addFilme(filme);
            valorTotal += valorFilme;
            //Entrega no dia seguinte
            Date dataEntrega = new Date();
            dataEntrega = adicionarDias(dataEntrega, 1);
            if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
                dataEntrega = adicionarDias(dataEntrega, 1);
            }
            locacao.setDataRetorno(dataEntrega);
            //.....
        }
        locacao.setValor(valorTotal);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        return locacao;
    }
}
