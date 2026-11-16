package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC12, UC13, UC14. */
public class Missao {
    private int id;
    private String titulo;
    private String descricao;
    private int dificuldade;
    private int recompensaOuro;
    private int maxPersonagens;
    private StatusMissao status = StatusMissao.PUBLICADA;
    private List<ParticipacaoMissao> participantes = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getDificuldade() { return dificuldade; }
    public void setDificuldade(int dificuldade) { this.dificuldade = dificuldade; }
    public int getRecompensaOuro() { return recompensaOuro; }
    public void setRecompensaOuro(int recompensaOuro) { this.recompensaOuro = recompensaOuro; }
    public int getMaxPersonagens() { return maxPersonagens; }
    public void setMaxPersonagens(int maxPersonagens) { this.maxPersonagens = maxPersonagens; }
    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }
    public List<ParticipacaoMissao> getParticipantes() { return participantes; }

    public void aceitar(Personagem personagem) {
        if (status != StatusMissao.PUBLICADA && status != StatusMissao.ACEITA) throw new IllegalStateException("Missão não aceita novos personagens");
        if (!personagem.estaAprovado()) throw new IllegalArgumentException("Personagem não aprovado");
        if (participantes.size() >= maxPersonagens) throw new IllegalStateException("Missão lotada");
        for (ParticipacaoMissao existente : participantes) {
            if (existente.getPersonagem() == personagem) throw new IllegalStateException("Personagem já participa da missão");
        }
        ParticipacaoMissao pm = new ParticipacaoMissao();
        pm.setPersonagem(personagem);
        pm.setOrdemAceite(participantes.size() + 1);
        participantes.add(pm);
        status = StatusMissao.ACEITA;
    }

    public void iniciar() {
        if (status != StatusMissao.ACEITA) throw new IllegalStateException("A missão precisa ter sido aceita");
        status = StatusMissao.EM_ANDAMENTO;
    }

    public void dividirOuro() {
        int n = participantes.size();
        if (n == 0) throw new IllegalStateException("Missão sem personagens participantes");
        int base = recompensaOuro / n;
        int resto = recompensaOuro % n;
        for (ParticipacaoMissao pm : participantes) {
            int parte = base + (pm.getOrdemAceite() == 1 ? resto : 0); // RN05
            pm.setOuroRecebido(parte);
            pm.getPersonagem().creditarOuro(parte);
        }
    }

    public void concluir(boolean sucesso) {
        if (status != StatusMissao.EM_ANDAMENTO) throw new IllegalStateException("Só missões em andamento podem ser concluídas");
        if (sucesso) {
            dividirOuro();
            status = StatusMissao.CONCLUIDA;
        } else {
            status = StatusMissao.FALHADA;
        }
    }
}
