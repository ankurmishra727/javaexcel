import javax.swing.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> Klasse zur Fehlerausgabe
 */
public class Fehler{
  /**
   * Konstruktor Gibt den Fehler auf STDERR und als Dialog aus
   */
  public Fehler(String fehler){
    System.err.println(fehler);
    JOptionPane.showMessageDialog(null,fehler,"An error occured",
    JOptionPane.ERROR_MESSAGE);
  }
}