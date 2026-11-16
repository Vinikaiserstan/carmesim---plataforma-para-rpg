package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC15, UC16, UC17. */
public class Publicacao {
    private int id;
    private String texto;
    private LocalDateTime dataHora;
    private boolean visivel = true;
    private boolean removida = false;
    private Usuario autor;
    private List<Comentario> comentarios = new ArrayList<>();
    private List<Denuncia> denuncias = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) {
        if (texto == null || texto.isBlank() || texto.length() > 1000) throw new IllegalArgumentException("Texto deve ter de 1 a 1000 caracteres");
        this.texto = texto;
    }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public boolean isVisivel() { return visivel; }
    public void setVisivel(boolean visivel) { this.visivel = visivel; }
    public boolean isRemovida() { return removida; }
    public void setRemovida(boolean removida) { this.removida = removida; }
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    public List<Comentario> getComentarios() { return comentarios; }
    public List<Denuncia> getDenuncias() { return denuncias; }

    public void comentar(Comentario comentario) {
        if (!visivel || removida) throw new IllegalStateException("Publicação não aceita comentários");
        comentarios.add(comentario);
    }

    public void denunciar(Denuncia denuncia) {
        if (denuncia.getDenunciante().getId() == autor.getId()) throw new IllegalArgumentException("Não é possível denunciar a própria publicação");
        int abertas = 0;
        for (Denuncia d : denuncias) {
            if (d.getDenunciante().getId() == denuncia.getDenunciante().getId()) throw new IllegalStateException("Denúncia já registrada por este usuário");
            if (d.getStatus() == StatusDenuncia.ABERTA) abertas++;
        }
        denuncias.add(denuncia);
        abertas++;
        if (abertas >= 3) ocultar(); // RN06
    }

    public void ocultar() {
        visivel = false;
    }

    public void reexibir() {
        if (!removida) visivel = true;
    }

    public void remover() {
        removida = true;
        visivel = false;
    }
}
