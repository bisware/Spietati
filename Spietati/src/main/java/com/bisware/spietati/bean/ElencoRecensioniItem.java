package com.bisware.spietati.bean;

public class ElencoRecensioniItem {
    private String titolo;
    private String idFilm;

    public ElencoRecensioniItem(String titolo, String idFilm) {
        this.titolo = titolo;
        this.idFilm = idFilm;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }
}
