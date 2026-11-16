package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC04, UC05, UC13. */
public class Personagem {
    private int id;
    private String nome;
    private String classe;
    private int nivel = 1;
    private int xp = 0;
    private int ouro = 0;
    private String atributos;
    private String historia;
    private String observacaoRevisao;
    private StatusPersonagem status = StatusPersonagem.RASCUNHO;
    private Usuario jogador;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }
    public int getOuro() { return ouro; }
    public void setOuro(int ouro) { this.ouro = ouro; }
    public String getAtributos() { return atributos; }
    public void setAtributos(String atributos) { this.atributos = atributos; }
    public String getHistoria() { return historia; }
    public void setHistoria(String historia) { this.historia = historia; }
    public String getObservacaoRevisao() { return observacaoRevisao; }
    public void setObservacaoRevisao(String observacaoRevisao) { this.observacaoRevisao = observacaoRevisao; }
    public StatusPersonagem getStatus() { return status; }
    public void setStatus(StatusPersonagem status) { this.status = status; }
    public Usuario getJogador() { return jogador; }
    public void setJogador(Usuario jogador) { this.jogador = jogador; }

    public void enviarParaRevisao() {
        if (status != StatusPersonagem.RASCUNHO) throw new IllegalStateException("Só rascunhos podem ser enviados para revisão");
        status = StatusPersonagem.EM_REVISAO;
    }

    public void aprovar() {
        if (status != StatusPersonagem.EM_REVISAO) throw new IllegalStateException("Personagem não está em revisão");
        status = StatusPersonagem.APROVADO;
    }

    public void devolver(String motivo) {
        if (status != StatusPersonagem.EM_REVISAO) throw new IllegalStateException("Personagem não está em revisão");
        observacaoRevisao = motivo;
        status = StatusPersonagem.RASCUNHO;
    }

    public boolean estaAprovado() {
        return status == StatusPersonagem.APROVADO;
    }

    public void ganharXp(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("XP deve ser maior que zero");
        int niveisAntes = xp / 100;
        xp += quantidade;
        nivel += (xp / 100) - niveisAntes; // RN04: 1 nível a cada 100 XP
    }

    public void creditarOuro(int quantidade) {
        if (quantidade < 0) throw new IllegalArgumentException("Ouro não pode ser negativo");
        ouro += quantidade;
    }
}
