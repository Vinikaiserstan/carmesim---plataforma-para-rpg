package br.edu.ifpi.carmesim.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Classe de domínio do diagrama de classes. Casos de uso: UC16, UC17. */
public class Denuncia {
    private int id;
    private String motivo;
    private String detalhe;
    private StatusDenuncia status = StatusDenuncia.ABERTA;
    private Usuario denunciante;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDetalhe() { return detalhe; }
    public void setDetalhe(String detalhe) { this.detalhe = detalhe; }
    public StatusDenuncia getStatus() { return status; }
    public void setStatus(StatusDenuncia status) { this.status = status; }
    public Usuario getDenunciante() { return denunciante; }
    public void setDenunciante(Usuario denunciante) { this.denunciante = denunciante; }

    public void marcarProcedente() {
        if (status != StatusDenuncia.ABERTA) throw new IllegalStateException("Denúncia já analisada");
        status = StatusDenuncia.PROCEDENTE;
    }

    public void marcarImprocedente() {
        if (status != StatusDenuncia.ABERTA) throw new IllegalStateException("Denúncia já analisada");
        status = StatusDenuncia.IMPROCEDENTE;
    }
}
