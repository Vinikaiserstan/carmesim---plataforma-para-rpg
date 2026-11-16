package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC07, UC08. */
public class Participacao {
    private int id;
    private StatusConvite status = StatusConvite.PENDENTE;
    private Usuario jogador;
    private Personagem personagem;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public StatusConvite getStatus() { return status; }
    public void setStatus(StatusConvite status) { this.status = status; }
    public Usuario getJogador() { return jogador; }
    public void setJogador(Usuario jogador) { this.jogador = jogador; }
    public Personagem getPersonagem() { return personagem; }
    public void setPersonagem(Personagem personagem) { this.personagem = personagem; }

    public void aceitar(Personagem personagem) {
        if (status != StatusConvite.PENDENTE) throw new IllegalStateException("Convite não está pendente");
        this.personagem = personagem;
        status = StatusConvite.ACEITO;
    }

    public void recusar() {
        if (status != StatusConvite.PENDENTE) throw new IllegalStateException("Convite não está pendente");
        status = StatusConvite.RECUSADO;
    }

    public void expirar() {
        if (status != StatusConvite.PENDENTE) throw new IllegalStateException("Convite não está pendente");
        status = StatusConvite.EXPIRADO;
    }
}
