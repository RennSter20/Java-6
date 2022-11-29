package hr.java.vjezbe.entitet;

public enum Semestar {

    ZIMSKI("1", "Zimski semestar"),
    LJETNI("2", "Ljetni semestar");


    String redniBrojSemestra;
    String nazivSemestra;

    public static Integer zimski = 0;
    public static Integer ljetni = 0;

    Semestar(String redniBrojSemestra, String nazivSemestra) {
        this.redniBrojSemestra = redniBrojSemestra;
        this.nazivSemestra = nazivSemestra;
    }

    public String getRedniBrojSemestra() {
        return redniBrojSemestra;
    }

    public void setRedniBrojSemestra(String redniBrojSemestra) {
        this.redniBrojSemestra = redniBrojSemestra;
    }

    public String getNazivSemestra() {
        return nazivSemestra;
    }

    public void setNazivSemestra(String nazivSemestra) {
        this.nazivSemestra = nazivSemestra;
    }

    public static Integer getZimski() {
        return zimski;
    }


    public static Integer getLjetni() {
        return ljetni;
    }

}
