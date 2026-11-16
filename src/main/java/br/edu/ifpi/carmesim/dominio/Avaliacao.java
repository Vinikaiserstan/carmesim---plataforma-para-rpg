package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC18. */
public class Avaliacao {
    private int id;
    private int nota;
    private String comentario;
    private Sessao sessao;
    private Usuario avaliador;
    private Usuario avaliado;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Sessao getSessao() { return sessao; }
    public void setSessao(Sessao sessao) { this.sessao = sessao; }
    public Usuario getAvaliador() { return avaliador; }
    public void setAvaliador(Usuario avaliador) { this.avaliador = avaliador; }
    public Usuario getAvaliado() { return avaliado; }
    public void setAvaliado(Usuario avaliado) { this.avaliado = avaliado; }

    public boolean notaValida() {
        return nota >= 1 && nota <= 5;
    }

    public void validar() {
        if (!notaValida()) throw new IllegalArgumentException("A nota deve ir de 1 a 5");
        if (sessao == null || !sessao.avaliacoesLiberadas()) throw new IllegalStateException("A sessão ainda não foi encerrada");
        if (avaliador.getId() == avaliado.getId()) throw new IllegalArgumentException("Não é possível avaliar a si mesmo"); // RN07
    }
}
