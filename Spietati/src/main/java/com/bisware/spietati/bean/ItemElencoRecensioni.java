package com.bisware.spietati.bean;

public class ItemElencoRecensioni {
    private String mese;
    private ItemFilm film;

    public ItemElencoRecensioni(String mese, ItemFilm film) {
        this.mese = mese;
        this.film = film;

    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public ItemFilm getFilm() {
        return film;
    }

    public void setFilm(ItemFilm film) {
        this.film = film;
    }

}
