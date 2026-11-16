package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC09, UC10, UC11, UC18. */
public class Sessao {
    private int id;
    private String titulo;
    private LocalDateTime inicio;
    private int duracaoMin;
    private StatusSessao status = StatusSessao.AGENDADA;
    private int xpConcedido = 0;
    private String resumo;
    private List<Presenca> presencas = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public int getDuracaoMin() { return duracaoMin; }
    public void setDuracaoMin(int duracaoMin) { this.duracaoMin = duracaoMin; }
    public StatusSessao getStatus() { return status; }
    public void setStatus(StatusSessao status) { this.status = status; }
    public int getXpConcedido() { return xpConcedido; }
    public void setXpConcedido(int xpConcedido) { this.xpConcedido = xpConcedido; }
    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }
    public List<Presenca> getPresencas() { return presencas; }

    public void confirmarPresenca(Participacao participacao, boolean vai) {
        if (status != StatusSessao.AGENDADA && status != StatusSessao.CONFIRMADA) throw new IllegalStateException("Sessão não aceita confirmações");
        Presenca alvo = null;
        for (Presenca p : presencas) {
            if (p.getParticipacao() == participacao) { alvo = p; break; }
        }
        if (alvo == null) {
            alvo = new Presenca();
            alvo.setParticipacao(participacao);
            presencas.add(alvo);
        }
        alvo.confirmar(vai);
    }

    public boolean atingiuQuorum(int minimo) {
        int confirmadas = 0;
        for (Presenca p : presencas) {
            if (p.isConfirmada()) confirmadas++;
        }
        return confirmadas >= minimo;
    }

    public void atualizarQuorum(int minimo) {
        if (status == StatusSessao.AGENDADA && atingiuQuorum(minimo)) {
            status = StatusSessao.CONFIRMADA; // RN03
        }
    }

    public void iniciar() {
        if (status != StatusSessao.CONFIRMADA) throw new IllegalStateException("Só sessões confirmadas podem ser iniciadas");
        status = StatusSessao.EM_ANDAMENTO;
    }

    public void encerrar(int xp, String resumo) {
        if (status != StatusSessao.EM_ANDAMENTO) throw new IllegalStateException("Só é possível encerrar sessão em andamento");
        List<Presenca> presentes = new ArrayList<>();
        for (Presenca p : presencas) {
            if (p.isCompareceu()) presentes.add(p);
        }
        if (presentes.isEmpty()) {
            this.xpConcedido = 0;
        } else {
            if (xp <= 0) throw new IllegalArgumentException("XP deve ser maior que zero");
            for (Presenca p : presentes) {
                Personagem personagem = p.getParticipacao().getPersonagem();
                if (personagem != null) personagem.ganharXp(xp); // RN04
            }
            this.xpConcedido = xp;
        }
        this.resumo = resumo;
        this.status = StatusSessao.ENCERRADA;
    }

    public void cancelar() {
        if (status != StatusSessao.AGENDADA && status != StatusSessao.CONFIRMADA) throw new IllegalStateException("Sessão não pode ser cancelada neste status");
        status = StatusSessao.CANCELADA;
    }

    public boolean avaliacoesLiberadas() {
        return status == StatusSessao.ENCERRADA;
    }
}
