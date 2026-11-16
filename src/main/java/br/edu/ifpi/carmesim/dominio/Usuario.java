package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC01, UC02, UC03, UC18. */
public class Usuario {
    private int id;
    private String nome;
    private String login;
    private String email;
    private String senhaHash;
    private boolean ativo = true;
    private Set<Perfil> perfis = new HashSet<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public Set<Perfil> getPerfis() { return perfis; }
    public void setPerfis(Set<Perfil> perfis) { this.perfis = perfis; }

    public boolean possuiPerfil(Perfil perfil) {
        return perfis.contains(perfil);
    }

    public boolean autenticar(String senhaHash) {
        return ativo && this.senhaHash != null && this.senhaHash.equals(senhaHash);
    }

    public double calcularReputacao(List<Avaliacao> avaliacoes) {
        double soma = 0;
        int total = 0;
        for (Avaliacao a : avaliacoes) {
            if (a.getAvaliado() != null && a.getAvaliado().getId() == id) {
                soma += a.getNota();
                total++;
            }
        }
        return total == 0 ? 0 : soma / total; // RN07
    }
}
