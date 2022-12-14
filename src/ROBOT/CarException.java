package ROBOT;

/**
 * Exception Klasse für das Projekt in Wissensverarbeitung im WS17/18.
 * Sie können Spezialisierungen für verschiedene Fehlertypen realisieren
 * oder die das Problem einfach direkt in dieser Exception kodieren. Sie dürfen die
 * Klasse natürlich beleibig anpassen.
 */

public class CarException extends Exception {

    /**
     * generated by Intellij
     **/
    private static final long serialVersionUID = 1581353508531667561L;

    CarException() {
        super();
    }

    public CarException(String s) {
        super(s);
    }

    CarException(Exception e) {
        super(e);
    }
}