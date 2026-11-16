package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC15. */
public class Comentario {
    private int id;
    private String texto;
    private LocalDateTime dataHora;
    private Usuario autor;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) {
        if (texto == null || texto.isBlank() || texto.length() > 1000) throw new IllegalArgumentException("Texto deve ter de 1 a 1000 caracteres");
        this.texto = texto;
    }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
}
