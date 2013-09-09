package com.bisware.spietati.bean;

public class ElencoRecensioniItem {
    private String mese;
    private String titolo;
    private String idFilm;

    public ElencoRecensioniItem(String mese, String titolo, String idFilm) {
        this.mese = mese;
        this.titolo = titolo;
        this.idFilm = idFilm;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
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
