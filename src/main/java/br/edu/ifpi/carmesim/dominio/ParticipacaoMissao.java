package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC13, UC14. */
public class ParticipacaoMissao {
    private int id;
    private int ordemAceite;
    private int ouroRecebido = 0;
    private Personagem personagem;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrdemAceite() { return ordemAceite; }
    public void setOrdemAceite(int ordemAceite) { this.ordemAceite = ordemAceite; }
    public int getOuroRecebido() { return ouroRecebido; }
    public void setOuroRecebido(int ouroRecebido) { this.ouroRecebido = ouroRecebido; }
    public Personagem getPersonagem() { return personagem; }
    public void setPersonagem(Personagem personagem) { this.personagem = personagem; }
}
