package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC10, UC11. */
public class Presenca {
    private int id;
    private boolean confirmada = false;
    private boolean compareceu = false;
    private Participacao participacao;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public boolean isConfirmada() { return confirmada; }
    public void setConfirmada(boolean confirmada) { this.confirmada = confirmada; }
    public boolean isCompareceu() { return compareceu; }
    public void setCompareceu(boolean compareceu) { this.compareceu = compareceu; }
    public Participacao getParticipacao() { return participacao; }
    public void setParticipacao(Participacao participacao) { this.participacao = participacao; }

    public void confirmar(boolean vai) {
        confirmada = vai;
    }

    public void registrarComparecimento(boolean compareceu) {
        this.compareceu = compareceu;
    }
}
