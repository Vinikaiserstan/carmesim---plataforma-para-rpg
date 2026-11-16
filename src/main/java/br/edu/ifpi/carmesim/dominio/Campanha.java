package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC06, UC07. */
public class Campanha {
    private int id;
    private String nome;
    private String descricao;
    private int limiteVagas;
    private int minimoPresencas;
    private boolean arquivada = false;
    private Usuario mestre;
    private List<Participacao> participacoes = new ArrayList<>();
    private List<Sessao> sessoes = new ArrayList<>();
    private List<Missao> missoes = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getLimiteVagas() { return limiteVagas; }
    public void setLimiteVagas(int limiteVagas) { this.limiteVagas = limiteVagas; }
    public int getMinimoPresencas() { return minimoPresencas; }
    public void setMinimoPresencas(int minimoPresencas) { this.minimoPresencas = minimoPresencas; }
    public boolean isArquivada() { return arquivada; }
    public void setArquivada(boolean arquivada) { this.arquivada = arquivada; }
    public Usuario getMestre() { return mestre; }
    public void setMestre(Usuario mestre) { this.mestre = mestre; }
    public List<Participacao> getParticipacoes() { return participacoes; }
    public List<Sessao> getSessoes() { return sessoes; }
    public List<Missao> getMissoes() { return missoes; }

    public int vagasOcupadas() {
        int total = 0;
        for (Participacao p : participacoes) {
            if (p.getStatus() == StatusConvite.ACEITO) total++;
        }
        return total;
    }

    public boolean temVaga() {
        return vagasOcupadas() < limiteVagas; // RN01
    }

    public void arquivar() {
        arquivada = true;
    }
}
