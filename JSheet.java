import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> Dies ist die Haupt-Klasse
 */
public class JSheet extends JFrame 
                     implements ActionListener {
    private JPanel _jp = null;
    private SpecialTable _sp = null;
    
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Konstruktor - Erzeugt das Mainwindow
   */  
  public JSheet(){
    super();
    setSize(600, 400);
    setTitle("JSheet");

    addWindowListener(new WindowCallback()); //Beenden beim Fensterschliessen

    // MenuBar hinzufuegen
    setJMenuBar( new MainMenu(this) );
    
    _jp = new JPanel();
    _jp.setLayout(new GridLayout(1,1));
    getContentPane().add(_jp,BorderLayout.CENTER);
    
    //Delays fuer Tooltips aendern:
    ToolTipManager ttm = ToolTipManager.sharedInstance();
    ttm.setInitialDelay(0);
    ttm.setDismissDelay(8000);
        

    //JLabel ergebnis = new JLabel("Formelwert");
    //getContentPane().add(ergebnis, BorderLayout.NORTH);

  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Main-Routine erzeigt ein neues JSheet Objekt und setzt es auf visible
   */
  public static void main(String args[]){
    JSheet x = new JSheet();
    x.setVisible(true);
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Eventhandler - faengt die Menu-Ereignisse
   */
  public void actionPerformed( ActionEvent e ) {
    String cmd = e.getActionCommand();
    if(cmd=="open") {
      dateiOpen();
    }
    if(cmd=="save") {
      dateiSave();
    }
    if(cmd=="new") {
      dateiNeu();
    }
    if(cmd=="about") {
      about();
    }
    if(cmd=="exit") {
      System.exit(0);
    }
    if(cmd=="coffee") {
      new Fehler("Coffee is ready :-)");
    }
    if(cmd=="insert_column") {
      insertColumn();
    }
    if(cmd=="insert_row") {
      insertRow();
    }
    if(cmd=="delete_column") {
      deleteColumn();
    }
    if(cmd=="delete_row") {
      deleteRow();
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Laedt ein Sheet aus einer Datei
   */
  private void dateiOpen() {
    JFileChooser chooser = new JFileChooser("."); //im aktuellen dir starten
    int returnVal = chooser.showOpenDialog(this);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      String datei = chooser.getSelectedFile().getPath(); //Pfad lesen
      try{
        _jp.setVisible(false);
        _jp.removeAll(); //alte Table removen
	_sp = new SpecialTable(datei);
        _jp.add(_sp ,BorderLayout.CENTER);
	_jp.setVisible(true);
      }
      catch(Exception e) {
        new Fehler("Opening "+datei+" failed.");
        return;
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /*
   * Save Funktion
   */
  private void dateiSave() {
    if(_sp == null ) {
      new Fehler("No Data loaded");
      return;
    }
    JFileChooser chooser = new JFileChooser("."); //im aktuellen dir starten
    int returnVal = chooser.showSaveDialog(this);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      String datei = chooser.getSelectedFile().getPath(); //Pfad lesen
      try {
        _sp.getModel().save(datei);
      }
      catch (Exception e) {
        new Fehler("Saving failed");
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Ruft DimDialog auf und erzeugt dann eine neue SpecialTable mit der
   * gewuenschten Groesse
   */
  private void dateiNeu() {
    DimDialog xy = new DimDialog(this);
    if (xy.wasOkay()) {
      _jp.setVisible(false);
      _jp.removeAll(); //alte Table removen
      _sp = new SpecialTable(xy.row(),xy.col());
      _jp.add(_sp ,BorderLayout.CENTER);
      _jp.setVisible(true);
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /*
   * Fuegt eine Zeile ein
   */
  private void insertRow() {
    if (_sp == null) {return;}
    if (_sp.getTable().getSelectedRow() != -1) {
      _sp.getModel().insertRow(_sp.getTable().getSelectedRow());
      _sp.initTable();
      _sp.initScrollPane();
    }else{
      new Fehler("Please select a cell first");
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /*
   * Fuegt eine Spalte ein
   */
  private void insertColumn() {
    if (_sp == null) {return;}
    if (_sp.getTable().getSelectedColumn() != -1) {
      _sp.getModel().insertCol(_sp.getTable().getSelectedColumn());
      _sp.initTable();
      _sp.initScrollPane();
    }else{
      new Fehler("Please select a cell first");
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /*
   * Loescht eine Zeile
   */
  private void deleteRow() {
    if (_sp == null) {return;}
    if (_sp.getTable().getSelectedRow() != -1) {
      _sp.getModel().deleteRow(_sp.getTable().getSelectedRow());
      _sp.initTable();
      _sp.initScrollPane();
    }else{
      new Fehler("Please select a cell first");
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /*
   * Loescht eine Spalte
   */
  private void deleteColumn() {
    if (_sp == null) {return;}
    if (_sp.getTable().getSelectedColumn() != -1) {
      _sp.getModel().deleteCol(_sp.getTable().getSelectedColumn());
      _sp.initTable();
      _sp.initScrollPane();
    }else{
      new Fehler("Please select a cell first");
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  /**
   * About-Window
   */
  private void about() {
    JOptionPane.showMessageDialog(this,
    "2001 (c) Frank Schubert, Andreas Gohr\n\n"+
    "This Software is licensed under the terms of\n"+
    "the GNU General Public License Version 2 or higher",
    "JSheet",
     JOptionPane.INFORMATION_MESSAGE);
   }
}

///////////////////////////////////////////////////////////////////////////////
/**
 * Sorgt für das beenden des Programms beim schliessen des Fensters
 */
class WindowCallback extends WindowAdapter {
  public void windowClosing(WindowEvent e){
      System.exit(0);
  }
}


/* Begruendung der gewaehlten Programmstruktur:
   JSheet besteht im groben aus den Klassen JSheet, Specialtable und
   Datenmodell sowie diversen Hilfsklassen fuer spezielle Aufgaben.
   
   Alle Daten und Funktionen die darauf wirken werden in der Klasse Datenmodell
   gespeichert.
   In Specialtable ist die grafische Ausgabe der Tabellenstruktur angelegt.
   JSheet selber stellt dann das Framework fuer das gesamte Programm bereit,
   mit Hauptmenu, Dialogboxen u.a.
   
   Durch die Trennung des Datenmodells vom grafischen Teil waere es moeglich
   die Komplette Oberfläche aszutauschen ohne die darunterliegenden Algorythmen
   auszutauschen.
   
*/
