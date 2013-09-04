package com.bisware.spietati.bean;

public class SchedaFilm {

    private String locandina;
    private String titolo;
    private String regista;
    private Recensione recensione;

    public String getLocandina() {
        return locandina;
    }

    public void setLocandina(String locandina) {
        this.locandina = locandina;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }

    @Override
    public String toString(){

        return "{" +
                "Titolo:" + this.titolo +
                ",Regista:" + this.regista +
                ",Recensione:" + this.recensione +
                "}";
    }
}
