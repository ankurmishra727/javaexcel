import java.util.*;
/**
 * <b>Programm:</b> JSheet - Spreadsheet<br>
 * <b>Copyright:</b> 2001 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <b>Version:</b> 1.0<br>
 * <b>Date:</b> 22.06.2001<br>
 * <br>
 * <b>Info:</b> Diese Klasse stellt einen 2-dimensionalen Vektor zur verfügung
 */
public class Vector2D {
  private Vector data=null;
  /////////////////////////////////////////////////////////////////////////////
  /*
   *Legt einen 2D-Vector mit der gegebenen Grösse an und initialisiert
   *ihn mit null
   */
  public Vector2D(int rows,int cols){
    int i=0;
    int j=0;
    Vector temp=null;

    data=new Vector();
    for (i=0;i<rows;i++){
      temp = new Vector();
      for (j=0;j<cols;j++){
	temp.add(null);
      }
      data.add(temp);
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /*
   *Liefert das Object an der bezeichneten Stelle zurück
   */
  public Object get(int row, int col){
    try {
      return (((Vector)(data.get(row))).get(col));
    }
    catch (Exception e) {
      return null;
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /*
   *Traegt ein Object an der bezeichneten Stelle ein
   */
  public void set(int row, int col, Object value){
    ((Vector)(data.get(row))).set(col,value);
  }
  
  /////////////////////////////////////////////////////////////////////////////
  /*
   *Liefert die Spaltenanzahl zurueck
   */
  public int colCount(){
    return ((Vector)(data.get(0))).size();
  }

  /////////////////////////////////////////////////////////////////////////////
  /*
   *Liefert die Zeileanzahl zurueck
   */
  public int rowCount(){
    return data.size();
  }

  /////////////////////////////////////////////////////////////////////////////
  /*
   *Fuegt eine neue Spalte ein
   */
  public void insertCol(int index){
    for (int i=0; i< rowCount(); i++){
      ((Vector) data.get(i)).add(index,null);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /*
   *Fuegt eine neue Zeile ein
   */
  public void insertRow(int index){
    Vector temp= new Vector();
    for (int i=0; i<colCount(); i++){
      temp.add(null);
    }
    data.add(index,temp);
  }

  /////////////////////////////////////////////////////////////////////////////
  /* 
   * Loescht eine Zeile
   */
  public void deleteRow(int index){
    data.remove(index);
  }

  /////////////////////////////////////////////////////////////////////////////
  /*
   * Loescht eine Spalte
   */
  public void deleteCol(int index){
    for (int i=0; i<rowCount(); i++){
      ((Vector)data.get(i)).remove(index);
    }
  }
}
