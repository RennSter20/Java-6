package hr.java.vjezbe.entitet;

/**
 * Klasa profesor predstavlja profesora u obrazovnoj ustanovi sa svojom sifrom, imenom, prezimenom i stečenom titulom.
 */
public class Profesor extends Osoba {

    private String sifra, ime, prezime, titula;

    public Profesor(Long id, String sifra, String ime, String prezime, String titula) {
        super(id, ime, prezime);
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.titula = titula;
    }

    public static class Builder {

        private String sifra, ime, prezime, titula;
        private Long id;
        public Profesor build() {
            return new Profesor(id, sifra, ime, prezime, titula);
        }
        public void ProfesorBuilder(long id) {
            this.id = id;
        }

        public Builder withSifra(String sifra){
            this.sifra = sifra;
            return this;
        }

        public Builder withIme(String ime){
            this.ime = ime;
            return this;
        }

        public Builder withPrezime(String prezime){
            this.prezime = prezime;
            return this;
        }

        public Builder withTitula(String titula){
            this.titula = titula;
            return this;
        }
    }


    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
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

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "sifra='" + sifra + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", titula='" + titula + '\'' +
                '}';
    }
}