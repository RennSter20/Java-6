package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Prilikom unosa, unosi se podatak o zgradi gdje se održava ispit.
 * @param naziv Naziv dvorane
 * @param zgrada Odabrana zgrada
 */
public record Dvorana(String naziv, String zgrada) implements Serializable {
}
