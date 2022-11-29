package hr.java.vjezbe.entitet;

import java.util.List;

public class Sveuciliste<T extends ObrazovnaUstanova> {

    private List<T> zbirka;

    public Sveuciliste(List<T> zbirka) {
        this.zbirka = zbirka;
    }

    public void dodajObrazovnuUstanovu(T objekt){
        zbirka.add(objekt);
    }

    public T dohvatiObrazovnuUstanovu(int index){
        return zbirka.get(index);
    }

    public List<T> dohvatiSveObrazovneUstanove(){
        return zbirka;
    }
}
