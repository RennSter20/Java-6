package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Klasa koja sadrži podatke o ispitu, studenta koji je pisao ispit, predmet ispita, ocjenu, datum i dvoranu u kojoj se ispit pisao.
 * Također sadrži implementiranu metodu unosSoftwarea u kojoj se unosi software koji se koristio na ispitu.
 */
public final class Ispit extends Entitet implements Online  {

    private Predmet predmet;
    private Student student;
    private Integer ocjena;
    private LocalDate datumIVrijeme;

    private Dvorana dvorana;

    public Ispit(Long id, Predmet predmet, Student student, Integer ocjena, LocalDate datumIVrijeme, Dvorana dvorana) {
        super(id);
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana = dvorana;
    }

    @Override
    public void unosSoftwarea(Scanner unos) {
        System.out.println("Unesite software koji ce se koristiti na ispitu: ");
        unos.nextLine();
    }

    public Dvorana getDvorana() {
        return dvorana;
    }

    public void setDvorana(Dvorana dvorana) {
        this.dvorana = dvorana;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDate getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDate datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }
}