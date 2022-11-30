package hr.java.vjezbe.entitet;

/**
 * Apstraktna klasa sa varijablama ime i prezime. Nasljeđuju ju je klase Student i Profesor.
 */
public abstract class Osoba extends Entitet {

    private String ime;
    private String prezime;

    public Osoba(Long id, String ime, String prezime) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
