package hr.java.vjezbe.sortiranje;

import hr.java.vjezbe.entitet.ObrazovnaUstanova;

import java.util.Comparator;

public class SveucilisteSorter implements Comparator<ObrazovnaUstanova> {

    @Override
    public int compare(ObrazovnaUstanova o1, ObrazovnaUstanova o2) {
        if(o1.getStudenti().size() < o2.getStudenti().size()){
            return 0;
        }else if(o1.getStudenti().size() == o2.getStudenti().size()){
            if(o1.getNaziv().compareToIgnoreCase(o2.getNaziv()) < 0){
                return 1;
            }
        }

        return 1;
    }
}
