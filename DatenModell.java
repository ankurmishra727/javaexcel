import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.StringTokenizer;
import java.io.*;
import java.util.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> Diese Klasse stellt das Tablemodel des Spreadsheets zur
 *              Verfuegung
 */
public class DatenModell extends AbstractTableModel {
  Vector2D data=null;
  
  /////////////////////////////////////////////////////////////////////////////
  /*
   * Konstruktor - legt die Datenvektoren mit der uebergebenen
   * Dimension an
   */
  public DatenModell(int rows, int cols) {
    data = new Vector2D(rows,cols);
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Save Funktion -Speichert alle Daten in <I>filename</I>
   */
  public boolean save(String filename) {
    BufferedWriter fileBuffer = null;
    int Rows = getRowCount();     // Zeilen
    int Columns = getColumnCount();// Spalten
    int i = 0; // for variablen
    int j = 0;
    //Buffer erstellen:
    try {
     fileBuffer = new BufferedWriter(new FileWriter(filename));
    }
    catch (IOException e) {
      System.err.println("EA-Fehler bei oeffnen von "+filename+".");
      return false;
    }
    //Schreiben:
    try {
      fileBuffer.write(Rows +"|"+ Columns +"|"); // Dimensionen in erste Zeile
      fileBuffer.newLine();
      for (i=0; i<Rows; i++) {
        for (j=0; j<Columns; j++) {
          if ( getValueAt(i,j) == null) {  
            fileBuffer.write("|");
	  }
	  else {
	    fileBuffer.write(getValueAt(i,j) + "|");
	  }
	}
        fileBuffer.newLine();
      }
      fileBuffer.close();
    }
    catch (IOException e) {
      System.err.println("Fehler beim schreiben");
    }
    return true;
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Läd Daten aus einer Datei und legt das entsprechende Datenmodell
   * an. Wirft Exceptions bei Problemen
   */
  public DatenModell(String filename) throws Exception {
    BufferedReader fileBuffer = null;
    String zeile = null;
    StringTokenizer ST = null;
    String lastToken = null;
    String actualToken = null;
    int aktuelle_spalte = 0; //die zu schreibende Spalte
    int zeilenlaenge = 0;    //entspricht countToken der akt. zeile
    int rows = 0;
    int cols = 0;
    int i=0;
    int j=0;
    try {
      fileBuffer = new BufferedReader(new FileReader(filename));
    }
    catch (IOException e) {
      System.err.println("Could not open " + filename);
      System.err.println(e.toString());
      throw e;  // Fehler an Aufrufer weiterwerfen
    }
 
   //Erste Zeile einlesen:
    try {
      zeile = fileBuffer.readLine();
      ST = new StringTokenizer(zeile,"|");
      rows=Integer.valueOf(ST.nextToken()).intValue(); //Dimension lesen
      cols=Integer.valueOf(ST.nextToken()).intValue();
   
      //DatenVektor anlegen:
      data = new Vector2D(rows,cols);
     
      for (i=0; i<rows; i++){
        zeile = fileBuffer.readLine();
        ST = new StringTokenizer(zeile,"|",true);
	zeilenlaenge = ST.countTokens();
	aktuelle_spalte = 0;
	
        for (j=0; j<zeilenlaenge; j++){
	  actualToken = ST.nextToken();
	  if(i == 0) {
	    if( actualToken.compareTo("|") == 0 ) {
	      setValueAt("",i,aktuelle_spalte++);
	    }
	    if( actualToken.compareTo("|") != 0 ) {
	      setValueAt(actualToken,i,aktuelle_spalte++);
	    }
	  }
	  else {
	    if( ( lastToken.compareTo(actualToken) == 0 ) && 
	        ( lastToken.compareTo("|") == 0 )) {
	      setValueAt("",i,aktuelle_spalte++);
	    }
	    if( ( lastToken.compareTo("|") == 0  ) && 
	        ( actualToken.compareTo("|") != 0) ) {
	      setValueAt(actualToken,i,aktuelle_spalte++);
	    }
	  }
	  lastToken = actualToken.toString();
        }
      }
    }
    catch (Exception e){
      System.err.println("Error on reading " + filename);
      System.err.println(e.toString());
      throw e;
    }

  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert Anzahl der Zeilen zurueck (ueberschriebene Methode
   * von AbstractTableModel)
   */
  public int getRowCount() {
    return data.rowCount();
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert Anzahl der Spalten zurueck (ueberschriebene Methode
   * von AbstractTableModel)
   */
  public int getColumnCount() {
    return data.colCount();
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert den berechneten Wert zurueck
   */
  public Object getCalcValueAt(int r, int c) {
    try{
      return parser((String)data.get(r,c),0);
    }
    catch (LoopException e){
      new Fehler("Recursion Loop detected at ["+r+","+c+"]\n"+
                 "Cell contents deleted");
      setValueAt(null,r,c);
      return getValueAt(r,c);
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Liefert den eingegebenen Wert zurueck
   */
  public Object getValueAt(int r, int c) {
    return data.get(r,c);
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Setzt alle Zellen auf editierbar (ueberschriebene Methode)
   */
  public boolean isCellEditable(int row, int col) {
    return true ;
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Rekursive Funktion um einen String nach Zellverweisen zu durchsuchen
   * und diese durch ihren Wert zu ersetzen
   * Sollte die maximale Recursionstiefe von 64 ueberschritten werden wird
   * eine LoopException geworfen
   */
  private String parser (String wert, int count) throws LoopException{
    int i=0;
    int links=0;
    int rechts=0;
    int col=0;
    int row=0;
    boolean link=false;
    boolean conti=true;
    StringTokenizer st=null;
    Object rWert=null;
    String temp;

    if (count >64){
      throw (new LoopException());
    }

    if (wert==null) {
      return("");
    }

     //Nach echtem Verweis suchen:
     while (conti){
       for (i=rechts; i<wert.length(); i++){
         if (wert.charAt(i)=='[') {
           links=i;
   	   break;
         }
       }

       if (i == wert.length()) {
         break;
       }
     
       for(i=rechts+1; i<wert.length(); i++){
         if (wert.charAt(i)==']') {
           rechts=i;
  	   break;
         }
       }
     
       if ( (i == wert.length()) && (wert.charAt(i)!=']') ){
         break;
       }

       if (links >= rechts){
         continue;
       }

       st = new StringTokenizer(wert.substring(links+1,rechts),",");
       if (st.countTokens() != 2){
         continue; //Es muessen 2 Tokens sein!!
       }

       try{
         row= Integer.valueOf(st.nextToken()).intValue();
         col= Integer.valueOf(st.nextToken()).intValue();
       }catch (NumberFormatException e){
         continue;
       }
     
       link=true;
       conti=false;
     }

     if (link) {
       rWert = getValueAt(row-1,col-1);

       if (rWert == null) {
         temp = "";
       }else{
         try {
           temp = parser((String)rWert, ++count);
         }
         catch (LoopException e){
           throw e;
         }
       }

       wert = wert.substring(0,links) +
              temp +
              wert.substring(rechts+1,wert.length());
       try {
         wert = parser(wert, ++count);
       }
       catch (LoopException e){
         throw e;
       }
     }
     try {
       ExprCalc rechner = new ExprCalc(wert);
       return Double.toString(rechner.getResult());
     }
     catch (SyntaxErrorException e) {
       return wert;
     }
   }
					       

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Ueberschriebene Methode - Setzt uebergebenen Wert in rawData
   * ein
   */
  public void setValueAt(Object o, int row, int col) {
    data.set(row,col,o) ;
    fireTableDataChanged();
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Fuegt Spalte nach angegebenem Index ein
   */
  public void insertCol(int col){
    data.insertCol(col);
    fireTableStructureChanged();
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Fuegt Zeile nach angegebenem Index ein
   */
  public void insertRow(int row){
    data.insertRow(row);
    fireTableStructureChanged();
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /**
   * Loescht Spalte an angegebenem Index
   */
  public void deleteCol(int col){
    data.deleteCol(col);
    fireTableStructureChanged();
  }

  /////////////////////////////////////////////////////////////////////////////
  /**
   * Loescht Zeile an angegebenem Index
   */
  public void deleteRow(int row){
    data.deleteRow(row);
    fireTableStructureChanged();
  }
}

/**
 *Stell nur eine Exception mit neuem Namen zur Verfuegung
 */
class LoopException extends Exception{
}
