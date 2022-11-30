package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.sortiranje.SveucilisteSorter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GlavnaDatoteka extends Glavna {

    private static final String PROFESORI_SERIALIZATION_FILE_NAME = "dat\\profesori.txt";
    private static final String PREDMETI_SERIALIZATION_FILE_NAME = "dat\\predmeti.txt";
    private static final String STUDENTI_SERIALIZATION_FILE_NAME = "dat\\studenti.txt";
    private static final String ISPITI_SERIALIZATION_FILE_NAME = "dat\\ispiti.txt";

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    private static final DateTimeFormatter ISPIT_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
    static List<Profesor> dohvatiProfesore(String filename){
        List<Profesor> profesori = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){
                String tempSifra = scanner.nextLine();
                String tempIme = scanner.nextLine();
                String tempPrezime = scanner.nextLine();
                String tempTitula = scanner.nextLine();

                profesori.add(new Profesor(tempSifra, tempIme, tempPrezime, tempTitula));

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return profesori;
    }

    static List<Student> dohvatiStudente(String filename){
        List<Student> studenti = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){

                String tempIme = scanner.nextLine();
                String tempPrezime = scanner.nextLine();
                LocalDate tempDate = LocalDate.parse(scanner.nextLine(), DATE_TIME_FORMAT);
                String tempJMBAG = scanner.nextLine();

                studenti.add(new Student(tempIme, tempPrezime, tempJMBAG, tempDate));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return studenti;
    }
    static List<Predmet> dohvatiPredmete(String filename, List<Student> studenti, List<Profesor> profesori){
        List<Predmet> predmeti = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){
                String tempSifra = scanner.nextLine();
                String tempNaziv = scanner.nextLine();
                Integer tempECTS = scanner.nextInt();
                scanner.nextLine();
                Integer tempProf = scanner.nextInt();
                scanner.nextLine();
                String ids = scanner.nextLine();
                List<Integer> lista = Arrays.stream(ids.split(" ")).map(Integer::parseInt).collect(Collectors.toList());

                Set<Student> studentiZaDodati = new HashSet<>();
                for(int i = 0;i<lista.size();i++){
                    studentiZaDodati.add(studenti.get(lista.get(i) - 1));
                }

                predmeti.add(new Predmet(tempSifra, tempNaziv, tempECTS, profesori.get(tempProf - 1), studentiZaDodati));

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return predmeti;
    }
    static List<Ispit> dohvatiIspite(String filename, List<Predmet> predmeti, List<Student> studenti){

        List<Ispit> tempIspiti = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){

                Integer tempPredmetOdabir = scanner.nextInt();
                scanner.nextLine();
                String tempDvorana = scanner.nextLine();
                String tempZgrada = scanner.nextLine();
                String software = scanner.nextLine();
                Integer tempStudentOdabir = scanner.nextInt();
                scanner.nextLine();
                Integer tempOcjena = scanner.nextInt();
                scanner.nextLine();
                LocalDate tempDate = LocalDate.parse(scanner.nextLine(), ISPIT_TIME_FORMAT);

                tempIspiti.add(new Ispit(predmeti.get(tempPredmetOdabir - 1), studenti.get(tempStudentOdabir - 1), tempOcjena, tempDate, new Dvorana(tempDvorana, tempZgrada)));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return tempIspiti;

    }

    public static void main(String[] args) {

        List<Profesor> profesori = dohvatiProfesore(PROFESORI_SERIALIZATION_FILE_NAME);
        List<Student> studenti = dohvatiStudente(STUDENTI_SERIALIZATION_FILE_NAME);
        List<Predmet> predmeti = dohvatiPredmete(PREDMETI_SERIALIZATION_FILE_NAME, studenti, profesori);
        List<Ispit> ispiti = dohvatiIspite(ISPITI_SERIALIZATION_FILE_NAME, predmeti, studenti);





    }

}
