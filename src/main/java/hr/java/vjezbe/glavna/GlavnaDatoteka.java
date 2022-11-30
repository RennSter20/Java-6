package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.PostojiViseNajmadjihStudenataException;
import hr.java.vjezbe.sortiranje.StudentSorter;
import hr.java.vjezbe.sortiranje.SveucilisteSorter;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GlavnaDatoteka  {

    private static final String PROFESORI_SERIALIZATION_FILE_NAME = "dat\\profesori.txt";
    private static final String PREDMETI_SERIALIZATION_FILE_NAME = "dat\\predmeti.txt";
    private static final String STUDENTI_SERIALIZATION_FILE_NAME = "dat\\studenti.txt";
    private static final String ISPITI_SERIALIZATION_FILE_NAME = "dat\\ispiti.txt";
    private static final String OCJENE_SERIALIZATION_FILE_NAME = "dat\\ocjene.txt";
    private static final String USTANOVE_SERIALIZATION_FILE_NAME = "dat\\ustanova.txt";

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    private static final DateTimeFormatter ISPIT_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
    static List<Profesor> dohvatiProfesore(String filename){
        List<Profesor> profesori = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){
                Long tempID = scanner.nextLong();
                scanner.nextLine();
                String tempSifra = scanner.nextLine();
                String tempIme = scanner.nextLine();
                String tempPrezime = scanner.nextLine();
                String tempTitula = scanner.nextLine();

                profesori.add(new Profesor(tempID, tempSifra, tempIme, tempPrezime, tempTitula));

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

                Long ids = scanner.nextLong();
                scanner.nextLine();
                String tempIme = scanner.nextLine();
                String tempPrezime = scanner.nextLine();
                LocalDate tempDate = LocalDate.parse(scanner.nextLine(), DATE_TIME_FORMAT);
                String tempJMBAG = scanner.nextLine();

                studenti.add(new Student(ids, tempIme, tempPrezime, tempJMBAG, tempDate));
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

                Long tempID = scanner.nextLong();
                scanner.nextLine();
                String tempSifra = scanner.nextLine();
                String tempNaziv = scanner.nextLine();
                Integer tempECTS = scanner.nextInt();
                scanner.nextLine();

                String studentiOneLine = scanner.nextLine();
                List<Long> studentiIDS = Arrays.stream(studentiOneLine.split(" ")).map(Long::parseLong).collect(Collectors.toList());

                Long profesorID = scanner.nextLong();
                if(scanner.hasNextLine()){
                    scanner.nextLine();
                }
                Profesor prof = null;
                for(Profesor profesor : profesori){
                    if(profesor.getId().equals(profesorID)){
                        prof = profesor;
                        break;
                    }
                }

                Set<Student> studentiZaDodati = new HashSet<>();
                for(Student student : studenti){
                    if(studentiIDS.contains(student.getId())){
                        studentiZaDodati.add(student);
                    }
                }

                predmeti.add(new Predmet(tempID, tempSifra, tempNaziv, tempECTS, prof, studentiZaDodati));

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

                Long tempID = scanner.nextLong();
                scanner.nextLine();
                Long IDPredmeta = scanner.nextLong();
                scanner.nextLine();
                String tempDvorana = scanner.nextLine();
                String tempZgrada = scanner.nextLine();
                String software = scanner.nextLine();
                Long IDStudenta = scanner.nextLong();
                scanner.nextLine();
                Integer tempOcjena = scanner.nextInt();
                scanner.nextLine();
                LocalDate tempDate = LocalDate.parse(scanner.nextLine(), ISPIT_TIME_FORMAT);

                Predmet nadenPredmet = predmeti.stream().filter(predmet -> predmet.getId().equals(IDPredmeta)).findFirst().get();
                Student nadenStudent = studenti.stream().filter(student -> student.getId().equals(IDStudenta)).findFirst().get();

                tempIspiti.add(new Ispit(tempID, nadenPredmet, nadenStudent, tempOcjena, tempDate, new Dvorana(tempDvorana, tempZgrada)));

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return tempIspiti;

    }
    static Map<Student, List<Integer>> dohvatiOcjene(String filename, List<Student> studenti){

        Map<Student, List<Integer>> tempOcjene = new HashMap<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){

            Long idStudenta = scanner.nextLong();
            scanner.nextLine();
            Integer ocjenaPrva = scanner.nextInt();
            scanner.nextLine();
            Integer ocjenaDruga = scanner.nextInt();
            if(scanner.hasNextLine()){
                scanner.nextLine();
            }

            studenti.stream().forEach(student -> {
                if(student.getId().equals(idStudenta)){
                    tempOcjene.put(student, new ArrayList<>(Arrays.asList(ocjenaPrva, ocjenaDruga)));
                }
            });

        }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
            return tempOcjene;
    }
    static List<ObrazovnaUstanova> dohvatiObrazovneUstanove(String filename){

        List<ObrazovnaUstanova> ustanove = new ArrayList<>();
        Integer brojUstanova = 0;

        List<Profesor> sviProfesori = dohvatiProfesore(PROFESORI_SERIALIZATION_FILE_NAME);
        List<Student> sviStudenti = dohvatiStudente(STUDENTI_SERIALIZATION_FILE_NAME);
        List<Predmet> sviPredmeti = dohvatiPredmete(PREDMETI_SERIALIZATION_FILE_NAME, sviStudenti, sviProfesori);
        List<Ispit> sviIspiti = dohvatiIspite(ISPITI_SERIALIZATION_FILE_NAME, sviPredmeti, sviStudenti);

        //podatci o ustanovama
        List<Long> idUstanova = new ArrayList<>();
        List<Integer> vrsteUstanova = new ArrayList<>();
        List<String> naziviUstanova = new ArrayList<>();
        List<List<Profesor>> profesoriUstanova = new ArrayList<>();
        List<List<Predmet>> predmetiUstanova = new ArrayList<>();
        List<List<Student>> studentiUstanova = new ArrayList<>();
        List<List<Ispit>> ispitiUstanova = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){

                Long id = scanner.nextLong();
                idUstanova.add(id);
                scanner.nextLine();

                Integer vrsta = scanner.nextInt();
                vrsteUstanova.add(vrsta);
                scanner.nextLine();

                String naziv = scanner.nextLine();
                naziviUstanova.add(naziv);

                String profesori = scanner.nextLine();
                List<Long> listaProfesora = Arrays.stream(profesori.split(" ")).map(Long::parseLong).collect(Collectors.toList());
                List<Profesor> profesoriOveUstanove = new ArrayList<>();
                for(Profesor profesor : sviProfesori){
                    for(Long idProf : listaProfesora){
                        if(idProf.equals(profesor.getId())){
                            profesoriOveUstanove.add(profesor);
                        }
                    }
                }
                profesoriUstanova.add(profesoriOveUstanove);

                String predmeti = scanner.nextLine();
                List<Long> listaPredmeta = Arrays.stream(predmeti.split(" ")).map(Long::parseLong).collect(Collectors.toList());
                List<Predmet> predmetiOveUstanove = new ArrayList<>();
                for(Predmet predmet :sviPredmeti){
                    for(Long idPred : listaPredmeta){
                        if(idPred.equals(predmet.getId())){
                            predmetiOveUstanove.add(predmet);
                        }
                    }
                }
                predmetiUstanova.add(predmetiOveUstanove);

                String studenti = scanner.nextLine();
                List<Long> listaStudenata = Arrays.stream(studenti.split(" ")).map(Long::parseLong).collect(Collectors.toList());
                List<Student> studentiOveUstanove = new ArrayList<>();
                for(Student student : sviStudenti){
                    for(Long idStud : listaStudenata){
                        if(idStud.equals(student.getId())){
                            studentiOveUstanove.add(student);
                        }
                    }
                }
                studentiUstanova.add(studentiOveUstanove);

                String ispiti = scanner.nextLine();
                List<Long> listaIspita = Arrays.stream(ispiti.split(" ")).map(Long::parseLong).collect(Collectors.toList());
                List<Ispit> ispitiOveUstanove = new ArrayList<>();
                for(Ispit ispit : sviIspiti){
                    for(Long idIspita : listaIspita){
                        if(idIspita.equals(ispit.getId())){
                            ispitiOveUstanove.add(ispit);
                        }
                    }
                }
                ispitiUstanova.add(ispitiOveUstanove);

                brojUstanova++;

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0;i<brojUstanova;i++){
            switch (vrsteUstanova.get(i)){
                case 1:
                    ustanove.add(new VeleucilisteJave(idUstanova.get(i), naziviUstanova.get(i), predmetiUstanova.get(i), profesoriUstanova.get(i), studentiUstanova.get(i), ispitiUstanova.get(i)));
                    break;
                case 2:
                    ustanove.add(new FakultetRacunarstva(idUstanova.get(i), naziviUstanova.get(i), predmetiUstanova.get(i), profesoriUstanova.get(i), studentiUstanova.get(i), ispitiUstanova.get(i)));
            }
        }

        return ustanove;
    }

    static void ispisProfesoraKojiPredaju(List<Predmet> predmeti, List<Student> studenti, List<Profesor> profesori, Map<Profesor, List<Predmet>> mapa){

        for(Profesor profesor : profesori){
            mapa.put(profesor, new ArrayList<>());
        }
        for(Predmet predmet : predmeti){

            List<Predmet> tempPredmeti = mapa.get(predmet.getNositelj());
            tempPredmeti.add(predmet);

            mapa.put(predmet.getNositelj(), tempPredmeti);
        }

        for(Profesor profesor : mapa.keySet()){
            if(profesori.contains(profesor)){
                System.out.println("Profesor " + profesor.getIme() + " " + profesor.getPrezime() + " predaje sljedeće predmete: ");
                Integer it = 1;
                for(Predmet predmet : mapa.get(profesor)){
                    System.out.println((it) + ") " + predmet.getNaziv());
                    it++;
                }
            }
        }

    }
    static void ispisIzvrsnihStudenata(List<Ispit> ispiti){
        ispiti.stream().forEach(ispit -> {
            if(ispit.getOcjena() == Ocjena.IZVRSTAN.getInteger()){
                System.out.println("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispit.getPredmet().getNaziv() + "'");
            }});
    }
    static void ispisKonacnihOcjena(ObrazovnaUstanova ustanova){

        Map<Student, List<Integer>> ocjene = dohvatiOcjene(OCJENE_SERIALIZATION_FILE_NAME, ustanova.getStudenti());

        if(ustanova instanceof VeleucilisteJave){
            for(Student student : ustanova.getStudenti()){
                BigDecimal ocjena = ((VeleucilisteJave) ustanova).izracunajKonacnuOcjenuStudijaZaStudenta(ustanova.getIspiti(), ocjene.get(student).get(0), ocjene.get(student).get(1), student);
                System.out.println("Konacna ocjena studenta " + student.getIme() + " " + student.getPrezime() + " je " + ocjena);
            }
        }else{
            for(Student student : ustanova.getStudenti()){
                BigDecimal ocjena = ((FakultetRacunarstva) ustanova).izracunajKonacnuOcjenuStudijaZaStudenta(ustanova.getIspiti(), ocjene.get(student).get(0), ocjene.get(student).get(1), student);
                System.out.println("Konacna ocjena studenta " + student.getIme() + " " + student.getPrezime() + " je " + ocjena);
            }
        }
    }
    static void ispisNajboljegStudenta(ObrazovnaUstanova ustanova){

        Student najuspjesniji = ustanova.odrediNajuspjesnijegStudentaNaGodini(2022);

        System.out.println("Najbolji student 2022. godine je " + najuspjesniji.getIme() + " " + najuspjesniji.getPrezime() + " JMBAG: " + najuspjesniji.getJmbag());

    }
    static void ispisZaRektora(ObrazovnaUstanova ustanova){
        if(ustanova instanceof FakultetRacunarstva){
            try{
                System.out.println("Student koji je osvojio rektorovu nagradu je: " + ((FakultetRacunarstva) ustanova).odrediStudentaZaRektorovuNagradu().getIme() + " " + ((FakultetRacunarstva) ustanova).odrediStudentaZaRektorovuNagradu().getPrezime());
            }catch (PostojiViseNajmadjihStudenataException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static void ispisStudenataPoKolegijima(List<Predmet> predmeti){


        predmeti.stream().forEach(predmet -> {
                    if(predmet.getStudenti().size() == 0){
                        System.out.println("Nema studenata upisanih na predmet '" + predmet.getNaziv() + "'.");
                    }else{
                        System.out.printf("Studenti upisani na predmet %s (%s):%n", predmet.getNaziv(), predmet.getSifra());
                        predmet.getStudenti().stream().sorted(new StudentSorter()).toList().forEach(s -> {
                            System.out.printf("%s %s %s%n", s.getJmbag(), s.getPrezime(), s.getIme());
                        });
                    }
                }
        );

    }
    public static void main(String[] args) {

        System.out.println("Ucitavanje profesora...");
        System.out.println("Ucitavanje studenata...");
        System.out.println("Ucitavanje predmeta...");
        System.out.println("Ucitavanje ispita i ocjena...");
        System.out.println("Ucitavanje obrazovnih ustanova...");

        Map<Profesor, List<Predmet>> mapa = new HashMap<>();

        List<ObrazovnaUstanova> sveUstanove = dohvatiObrazovneUstanove(USTANOVE_SERIALIZATION_FILE_NAME);
        for(int i = 0;i<sveUstanove.size();i++){
            ispisProfesoraKojiPredaju(sveUstanove.get(i).getPredmeti(), sveUstanove.get(i).getStudenti(), sveUstanove.get(i).getProfesori(), mapa);
            ispisStudenataPoKolegijima(sveUstanove.get(i).getPredmeti());
            ispisIzvrsnihStudenata(sveUstanove.get(i).getIspiti());

            ispisKonacnihOcjena(sveUstanove.get(i));
            ispisNajboljegStudenta(sveUstanove.get(i));
            ispisZaRektora(sveUstanove.get(i));

            for(int j = 0;j<3;j++){
                System.out.println("");
            }
        }

        List<ObrazovnaUstanova> sortirani = sveUstanove.stream().sorted(((o1, o2) -> {
            if(o1.getStudenti().size() > o2.getStudenti().size()){
                return 1;
            }else if(o1.getStudenti().size() < o2.getStudenti().size()){
                return 0;
            }else{
                return o1.getNaziv().compareTo(o2.getNaziv());
            }
        })).toList();
        System.out.println("Sortirane obrazovne ustanove prema broju studenata:");
        for(int i = 0;i<sortirani.size();i++){
            System.out.println(sortirani.get(i).getNaziv() + " " + sortirani.get(i).getStudenti().size() + " studenata");
        }

    }

}
