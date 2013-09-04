package com.bisware.spietati.bean;

public class Recensione {
    private String trama;
    private String corpo;

    private String autore;
    private String voto;
    private String data;

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {

        return "{" +
                "Corpo:" + this.corpo +
                ",Autore: " + this.autore +
                ",Voto: " + this.voto +
                ",Data: " + this.data +
                "}";
    }
}
